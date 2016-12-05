package org.fundacionjala.sonarqube.visitors;

import org.fundacionjala.sonarqube.semantic.SymbolModel;
import org.fundacionjala.sonarqube.tree.CompilationUnitTree;

public interface TreeVisitorContext {
    /**
     * @return the top tree node of the current file AST representation.
     */
    CompilationUnitTree getTopTree();


    /**
     * @return the symbol model that allows to access the symbols declared in the current file
     */
    SymbolModel getSymbolModel();
}
