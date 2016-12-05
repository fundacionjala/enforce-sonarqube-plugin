package org.fundacionjala.sonarqube.visitors;

import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.semantic.Symbol;
import org.fundacionjala.sonarqube.semantic.SymbolModelBuilder;
import org.fundacionjala.sonarqube.semantic.SymbolModelImpl;
import org.fundacionjala.sonarqube.semantic.Usage;
import org.fundacionjala.sonarqube.tree.*;
import org.fundacionjala.sonarqube.treeimplementation.BlockTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.ExpressionStatementTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.MemberSelectExpressionTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.VariableTreeImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SymbolsGeneratorVisitor extends BaseTreeVisitor {
    private static final String RETURN = "(?i)return";
    private static final String BOOLEAN_REGEX = "(?i)(true|false)";
    private SymbolModelBuilder symbolModel;
    private Scope currentScope;
    private Map<Tree, Scope> treeScopeMap;

    public SymbolsGeneratorVisitor(Map<Tree, Scope> treeScopeMap) {
        this.treeScopeMap = treeScopeMap;
    }

    @Override
    public void visitCompilationUnit(CompilationUnitTree tree) {
        this.symbolModel = (SymbolModelBuilder) getContext().getSymbolModel();
        this.currentScope = null;

        enterScope(tree);
        super.visitCompilationUnit(tree);
        leaveScope();
    }

    // this method may be important for saving method's return value. Until this point it was a good solution to test
    // relations between classes by saving method's return value for then retrieve it and use it for assertions.
    @Override
    public void visitBlock(BlockTree tree) {
        if (isScopeAlreadyEntered(tree)) {
            super.visitBlock(tree);

        } else {
            enterScope(tree);
            List<StatementTree> bodyBlock = tree.body();
            if (!bodyBlock.isEmpty()) {
                int index = 0;
                boolean hasReturnKeywork = false;
                while (!hasReturnKeywork && index < bodyBlock.size()) {
                    Tree currentTree = bodyBlock.get(index);
                    if (currentTree.is(Tree.Kind.VARIABLE) && ((VariableTreeImpl) currentTree).simpleName().simpleName().matches(RETURN)) {
                        hasReturnKeywork = true;
                    }
                    index++;
                }
                Tree returnValue = bodyBlock.get(index++);
                if (returnValue.is(Tree.Kind.EXPRESSION_STATEMENT) &&
                        ((ExpressionStatementTreeImpl) returnValue).expression().is(Tree.Kind.IDENTIFIER)
                        && ((IdentifierTree) ((ExpressionStatementTreeImpl) returnValue).expression()).simpleName().matches(BOOLEAN_REGEX)) {
                    String returnValueName = ((IdentifierTree) ((ExpressionStatementTreeImpl) returnValue).expression()).simpleName();
                    //TODO: retrieve parent from block tree and use it to get to method tree and add this return value as variable or something.
                    List<Symbol> methods = ((SymbolModelImpl) symbolModel).getSymbols(Symbol.Kind.METHOD);
                }
            }
            super.visitBlock(tree);
            leaveScope();
        }
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        enterScope(tree);
        if (tree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            declareMethodInvocationName(((MemberSelectExpressionTreeImpl) tree.methodSelect()).identifier());
        } else {
            declareMethodInvocationName((IdentifierTree) tree.methodSelect());
        }
        super.visitMethodInvocation(tree);
        leaveScope();
    }

    @Override
    public void visitMethod(MethodTree tree) {
        enterScope(tree);
        List<Symbol> preCreatedSymbolModelForMethod = ((SymbolModelImpl) symbolModel).getSymbols(tree.simpleName().simpleName());
        if (preCreatedSymbolModelForMethod.isEmpty()) {
            IdentifierTree methodName = tree.simpleName();
            declareSymbolModelMethod(tree, methodName);
        } else if (preCreatedSymbolModelForMethod.contains(tree.simpleName())) {
            preCreatedSymbolModelForMethod.get(0).setScope(currentScope);
            declareMethodReturnType(tree.returnType());
            declareParameters(tree.parameters());
        } else {
            declareSymbolModelMethod(tree, tree.simpleName());
        }
        super.visitMethod(tree);

        leaveScope();
    }


    @Override
    public void visitClass(ClassTree tree) {
        IdentifierTree classNameIdentifier = tree.simpleName();

        if (classNameIdentifier != null) {
            if (tree.is(Tree.Kind.CLASS_DECLARATION)) {
                declareClassSymbol(classNameIdentifier, getNonBlockScope());
                enterScope(tree);

            } else {
                enterScope(tree);
                declareClassSymbol(classNameIdentifier, currentScope);
            }

        } else {
            enterScope(tree);
        }

        super.visitClass(tree);
        leaveScope();
    }

    @Override
    public void visitIdentifier(IdentifierTree tree) {
        if (tree.is(Tree.Kind.IDENTIFIER_REFERENCE, Tree.Kind.THIS)) {
            addUsageFor(tree, Usage.Kind.READ);
        }
    }

    private void declareSymbolModelMethod(MethodTree tree, IdentifierTree methodName) {
        declareMethodReturnType(tree.returnType());
        declareMethodName(methodName, currentScope);
        declareParameters(tree.parameters());
    }

    private void declareMethodReturnType(TypeTree typeTree) {
        symbolModel.declareSymbol(typeTree.toString(), Symbol.Kind.METHOD, currentScope);
    }

    private void declareMethodInvocationName(IdentifierTree tree) {
        symbolModel.declareSymbol((tree).simpleName(), Symbol.Kind.METHOD_INVOCATION, currentScope).addUsage(Usage.create(tree, Usage.Kind.METHOD_INVOCATION));
    }

    private void declareParameters(List<VariableTree> identifiers) {
        for (VariableTree identifier : identifiers) {
            symbolModel.declareSymbol(identifier.simpleName().simpleName(), Symbol.Kind.PARAMETER, currentScope).addUsage(Usage.create(identifier.simpleName(), Usage.Kind.LEXICAL_DECLARATION));
        }
    }

    private void leaveScope() {
        if (currentScope != null) {
            currentScope = currentScope.outer();
        }
    }

    private void enterScope(Tree tree) {
        currentScope = treeScopeMap.get(tree);
        if (currentScope == null) {
            throw new IllegalStateException("No scope found for the tree");
        }
    }

    /**
     * @return true if symbol found and usage recorded, false otherwise.
     */
    private boolean addUsageFor(IdentifierTree identifier, Usage.Kind kind) {
        Symbol symbol = currentScope.lookupSymbol(identifier.simpleName());
        if (symbol != null) {
            symbol.addUsage(Usage.create(identifier, kind));
            return true;
        }
        return false;
    }

    private boolean isScopeAlreadyEntered(BlockTree tree) {
        return !treeScopeMap.containsKey(tree);
    }

    private Scope getNonBlockScope() {
        Scope scope = currentScope;
        while (scope.isBlock()) {
            scope = scope.outer();
        }
        return scope;
    }

    private void declareMethodName(IdentifierTree methodName, Scope scope) {
        List<Symbol> symbolModelImpl = ((SymbolModelImpl) symbolModel).getSymbols(methodName.simpleName());
        if (!symbolModelImpl.isEmpty()) {
            Symbol virtualSymbol = symbolModelImpl.get(0);
            virtualSymbol.setScope(currentScope);
            virtualSymbol.addUsageAtFirst(Usage.create(methodName, Usage.Kind.DECLARATION));
        } else {
            symbolModel.declareSymbol(methodName.simpleName(), Symbol.Kind.METHOD, scope).addUsage(Usage.create(methodName, Usage.Kind.DECLARATION));
        }
    }

    private void declareClassSymbol(IdentifierTree classNameIdentifier, Scope scope) {
        symbolModel.declareSymbol(classNameIdentifier.simpleName(), Symbol.Kind.CLASS, scope).addUsage(Usage.create(classNameIdentifier, Usage.Kind.DECLARATION));
    }
}
