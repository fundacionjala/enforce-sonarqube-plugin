package org.fundacionjala.sonarqube.apex;

import com.google.common.base.Preconditions;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.typed.ActionParser;
import org.fundacionjala.sonarqube.MockChecks.SpecialCheck;
import org.fundacionjala.sonarqube.semantic.Symbol;
import org.fundacionjala.sonarqube.semantic.SymbolModel;
import org.fundacionjala.sonarqube.semantic.SymbolModelImpl;
import org.fundacionjala.sonarqube.semantic.Usage;
import org.fundacionjala.sonarqube.tree.*;
import org.fundacionjala.sonarqube.treeimplementation.IdentifierTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.MemberSelectExpressionTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.MethodInvocationTreeImpl;
import org.fundacionjala.sonarqube.visitors.*;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.symbol.NewSymbolTable;
import org.sonar.api.ce.measure.Settings;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ApexAstScanner {

    private static final String APEX_EXTENSION = ".cls";
    private static final Logger LOG = Loggers.get(ApexSquid.class);
    private ActionParser<Tree> parser;
    private SensorContext context;
    private List<TreeVisitor> visitors;
    private Settings settings;
    private Map<String, InputFile> inputFiles;
    private Map<String, SymbolModel> unBindedSymbols;
    private ApexVisitorContext apexVisitorContext;
    private Map<String, TreeVisitorContext> contexts;

    public ApexAstScanner(ActionParser<Tree> parser, Settings settings) {
        this.parser = parser;
        this.settings = settings;
        inputFiles = new Hashtable<>();
        unBindedSymbols = new Hashtable<>();
        contexts = new Hashtable<>();
    }

    public void scan(List<InputFile> inputFiles, SensorContext context, List<TreeVisitor> visitors) {
        this.context = context;
        this.visitors = visitors;
        if (this.inputFiles.isEmpty()) {
            for (InputFile inputFile : inputFiles) {
                this.inputFiles.put(inputFile.file().getName(), inputFile);
            }
        }
        for (InputFile currentFile : inputFiles) {
            simpleScan(currentFile);
        }

        if (!visitors.isEmpty()) {
            for (InputFile currentFile : inputFiles) {
                for (TreeVisitor visitor : visitors) {
                    if (visitor instanceof SpecialCheck) {
                        scanSpecialChecks((SpecialCheck)visitor, currentFile.file().getName());
                    } else {
                        visitor.scanTree(contexts.get(currentFile.file().getName()));
                    }
                }
            }
        }
    }

    public ApexVisitorContext getContext() {
        return apexVisitorContext;
    }

    private void scanSpecialChecks(SpecialCheck visitor, String fileName) {
        visitor.setUnbindedSymbols(unBindedSymbols);
        visitor.scanTree(contexts.get(fileName));
    }

    private void simpleScan(InputFile file) {
        try {
            CompilationUnitTree tree = (CompilationUnitTree) parser.parse(new File(file.absolutePath()));
            scanFile(file.file().getName(), visitors, tree);
            highlightSymbols(context.newSymbolTable().onFile(file), apexVisitorContext);
        } catch (RecognitionException exception) {
            LOG.error("Unable to parse source file : " + file.file().getAbsolutePath());
            LOG.error(exception.getMessage());
        }
    }

    private void scanFile(String inputFile, List<TreeVisitor> visitors, CompilationUnitTree tree) {
        apexVisitorContext = new ApexVisitorContext(tree, inputFile, settings, unBindedSymbols);
        contexts.put(inputFile, apexVisitorContext);
        bindSymbols(inputFile);
    }

    private void bindSymbols(String inputfile) {
        SymbolModel symbolModel = apexVisitorContext.getSymbolModel();

        //Method Invocations
        List<Symbol> methodInvocations = symbolModel.getSymbols(Symbol.Kind.METHOD_INVOCATION);
        for (Symbol symbol : methodInvocations) {
            MethodInvocationTreeImpl methodInvocationTree = ((MethodInvocationTreeImpl) symbol.scope().tree());
            if (methodInvocationTree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
                IdentifierTree variableInvocationName = ((IdentifierTree) ((MemberSelectExpressionTreeImpl) methodInvocationTree.methodSelect()).expression());
                IdentifierTree methodInvocationName = ((MemberSelectExpressionTreeImpl) methodInvocationTree.methodSelect()).identifier();
                String variableType = variableType(variableInvocationName);
                if (!variableType.isEmpty()) {
                    addUsageFor(variableType, methodInvocationName, inputfile);
                }
            }
        }

        //Variable Declarations

        //Field Declarations
    }

    private String variableType(IdentifierTree methodInvocationName) {
        String variableType;
        Scope possibleMethodsScope = ((IdentifierTreeImpl) methodInvocationName).getScope().outer();
        if (possibleMethodsScope.tree().is(Tree.Kind.METHOD)) {
            MethodTree methodTree = ((MethodTree) possibleMethodsScope.tree());
            variableType = findMethodParameterType(methodTree, methodInvocationName.simpleName(), "");
        } else {
            MethodTree method = (MethodTree) possibleMethodsScope.outer().tree();
            variableType = findMethodParameterType(method, methodInvocationName.simpleName(), "");
        }

        return variableType;
    }

    public static String findMethodParameterType(MethodTree methodTree, String methodInvocationName, String variableType) {
        for (VariableTree variable : methodTree.parameters()) {
            if (methodInvocationName.equals(variable.simpleName().simpleName())) {
                variableType = variable.type().name();
                break;
            }
        }
        return variableType;
    }

    private void addUsageFor(String variableTypeName, IdentifierTree methodInvocationName, String inputFile) {
        SymbolModel symbolModel = unBindedSymbols.get(variableTypeName + APEX_EXTENSION);
        if (symbolModel != null) {
            List<Symbol> simbolModelSymbols = symbolModel.getSymbols();
            if (simbolModelSymbols.contains(methodInvocationName.simpleName())) {
                simbolModelSymbols.get(simbolModelSymbols.indexOf(methodInvocationName.simpleName())).addUsage(Usage.create(methodInvocationName, Usage.Kind.METHOD_INVOCATION, inputFiles.get(inputFile)));
            } else if (!simbolModelSymbols.contains(methodInvocationName.simpleName())) {
                ((SymbolModelImpl) symbolModel).declareSymbol(methodInvocationName.simpleName(), Symbol.Kind.METHOD, null)
                        .addUsage(Usage.create(methodInvocationName, Usage.Kind.EXTERNAL_INVOCATION, inputFiles.get(inputFile)));
            }
        } else {
            SymbolModelImpl virtualLink = new SymbolModelImpl();
            virtualLink.declareSymbol(methodInvocationName.simpleName(), Symbol.Kind.METHOD, null)
                    .addUsage(Usage.create(methodInvocationName, Usage.Kind.EXTERNAL_INVOCATION, inputFiles.get(inputFile)));
            unBindedSymbols.put(variableTypeName + APEX_EXTENSION, virtualLink);
        }
    }

    private static void highlightSymbols(NewSymbolTable newSymbolTable, TreeVisitorContext context) {
        HighlightSymbolTableBuilder.build(newSymbolTable, context);
    }
}
