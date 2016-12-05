package org.fundacionjala.sonarqube.visitors;

import org.fundacionjala.sonarqube.semantic.SymbolModel;
import org.fundacionjala.sonarqube.semantic.SymbolModelImpl;
import org.fundacionjala.sonarqube.tree.CompilationUnitTree;
import org.fundacionjala.sonarqube.tree.Tree;
import org.sonar.api.ce.measure.Settings;

import java.util.Map;

public class ApexVisitorContext implements TreeVisitorContext{

    private final CompilationUnitTree tree;
    private final SymbolModel symbolModel;
    private boolean isVirtual;
    private Map<String, SymbolModel> unbindedSymbols;

    public ApexVisitorContext(CompilationUnitTree tree, String file, Settings settings,
                              Map<String, SymbolModel> unBindedSymbols) {
        this.unbindedSymbols = unBindedSymbols;
        this.tree = tree;
        this.symbolModel = retriveSymbolModelFor(unBindedSymbols, file);
        SymbolModelImpl.build(this, settings);
        if ((tree.is(Tree.Kind.COMPILATION_UNIT) || tree.is(Tree.Kind.METHOD) || tree.is(Tree.Kind.MEMBER_SELECT)) && !isVirtual) {
            this.unbindedSymbols.put(file, this.symbolModel);
        }
    }

    @Override
    public CompilationUnitTree getTopTree() {
        return tree;
    }

    @Override
    public SymbolModel getSymbolModel() {
        return symbolModel;
    }

    public Map<String,SymbolModel> getUnbindedSymbols() {
        return this.unbindedSymbols;
    }

    private SymbolModel retriveSymbolModelFor(Map<String, SymbolModel> unBindedSymbols, String file) {
        SymbolModel retrivedSymbolModel = unBindedSymbols.get(file);
        if (retrivedSymbolModel == null) {
            return new SymbolModelImpl();

        } else {
            isVirtual = true;
            return retrivedSymbolModel;
        }
    }
}
