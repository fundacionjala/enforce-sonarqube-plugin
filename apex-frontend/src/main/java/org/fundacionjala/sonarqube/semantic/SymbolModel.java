package org.fundacionjala.sonarqube.semantic;

import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.visitors.Scope;

import javax.annotation.Nullable;
import java.util.List;

public interface SymbolModel {
    /**
     * Returns all symbols
     */
    List<Symbol> getSymbols();

    /**
     * @param kind kind of symbols to look for
     * @return list of symbols with the given kind
     */
    List<Symbol> getSymbols(Symbol.Kind kind);

    /**
     * @param name name of symbols to look for
     * @return list of symbols with the given name
     */
    List<Symbol> getSymbols(String name);

    /**
     * @param tree
     * @return scope corresponding to this tree. Returns Null if no scope found
     */
    @Nullable
    Scope getScope(Tree tree);

}
