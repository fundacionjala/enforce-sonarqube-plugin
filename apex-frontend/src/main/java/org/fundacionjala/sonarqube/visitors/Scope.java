package org.fundacionjala.sonarqube.visitors;

import com.google.common.collect.Maps;
import org.fundacionjala.sonarqube.semantic.Symbol;
import org.fundacionjala.sonarqube.tree.Tree;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Scope {

    private Scope parentScope;
    private final Tree tree;
    protected Map<String, Symbol> symbols = Maps.newHashMap();
    private final boolean isBlock;

    /**
     *
     * @param parentScope parent scope
     * @param tree syntax tree holding this scope (e.g. CompilationUnitTree or BlockTree)
     * @param isBlock pass true for block scopes (loops, if, etc.), false for non-block scopes
     */
    public Scope(Scope parentScope, Tree tree, boolean isBlock) {
        this.parentScope = parentScope;
        this.tree = tree;
        this.isBlock = isBlock;
    }

    public Tree tree() {
        return tree;
    }

    public Scope outer() {
        return parentScope;
    }

    /**
     * @param name of the symbol to look for
     * @return the symbol corresponding to the given name
     */
    public Symbol lookupSymbol(String name) {
        Scope scope = this;
        while (scope != null && !scope.symbols.containsKey(name)) {
            scope = scope.parentScope;
        }
        return scope == null ? null : scope.symbols.get(name);
    }

    /**
     * @param kind of the symbols to look for
     * @return the symbols corresponding to the given kind
     */
    public List<Symbol> getSymbols(Symbol.Kind kind) {
        List<Symbol> result = new LinkedList<>();
        for (Symbol symbol : symbols.values()) {
            if (symbol.is(kind)) {
                result.add(symbol);
            }
        }
        return result;
    }

    public boolean isGlobal() {
            return tree.is(Tree.Kind.COMPILATION_UNIT);
    }

    public void addSymbol(Symbol symbol) {
        symbols.put(symbol.name(), symbol);
    }

    @Nullable
    public Symbol getSymbol(String name) {
        return symbols.get(name);
    }

    /**
     * Returns true for block scopes (loops, if, etc.), false for function scopes (script, functions, getter, etc.)
     */
    public boolean isBlock() {
        return isBlock;
    }

}
