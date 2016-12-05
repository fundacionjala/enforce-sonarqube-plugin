package org.fundacionjala.sonarqube.semantic;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.visitors.Scope;
import org.fundacionjala.sonarqube.visitors.ScopeVisitor;
import org.fundacionjala.sonarqube.visitors.SymbolsGeneratorVisitor;
import org.fundacionjala.sonarqube.visitors.TreeVisitorContext;
import org.sonar.api.ce.measure.Settings;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SymbolModelImpl implements SymbolModel, SymbolModelBuilder{
    private List<Symbol> symbols = Lists.newLinkedList();
    private Set<Scope> scopes = Sets.newHashSet();
    private Scope globalScope;

    public static void build(TreeVisitorContext context, @Nullable Settings settings) {
        Map<Tree, Scope> treeScopeMap = getScopes(context);

        new SymbolsGeneratorVisitor(treeScopeMap).scanTree(context);
    }

    private static Map<Tree, Scope> getScopes(TreeVisitorContext context) {
        ScopeVisitor scopeVisitor = new ScopeVisitor();
        scopeVisitor.scanTree(context);
        return scopeVisitor.getTreeScopeMap();
    }

    @Override
    public Scope globalScope() {
        return globalScope;
    }

    @Override
    public void addScope(Scope scope) {
        if (scopes.isEmpty()) {
            globalScope = scope;
        }
        scopes.add(scope);
    }

    @Override
    public Set<Scope> getScopes() {
        return scopes;
    }

    @Override
    public Symbol declareSymbol(String name, Symbol.Kind kind, @Nullable Scope scope) {
        Symbol symbol;
        if(scope != null) {
            symbol = scope.getSymbol(name);
            if (symbol == null) {
                symbol = new Symbol(name, kind, scope);
                scope.addSymbol(symbol);
                symbols.add(symbol);
            }
        } else {
            symbol = new Symbol(name, kind, null);
            symbols.add(symbol);
        }
        return symbol;
    }

    @Override
    public List<Symbol> getSymbols() {
        return Collections.unmodifiableList(symbols);
    }

    /**
     * @param kind kind of symbols to look for
     * @return list of symbols with the given kind
     */
    @Override
    public List<Symbol> getSymbols(Symbol.Kind kind) {
        List<Symbol> result = Lists.newArrayList();
        for (Symbol symbol : symbols) {
            if (kind.equals(symbol.kind())) {
                result.add(symbol);
            }
        }
        return result;
    }

    /**
     * @param name name of symbols to look for
     * @return list of symbols with the given name
     */
    @Override
    public List<Symbol> getSymbols(String name) {
        List<Symbol> result = Lists.newArrayList();
        for (Symbol symbol : symbols) {
            if (name.equals(symbol.name())) {
                result.add(symbol);
            }
        }
        return result;
    }

    @Nullable
    @Override
    public Scope getScope(Tree tree) {
        for (Scope scope : getScopes()) {
            if (scope.tree().equals(tree)) {
                return scope;
            }
        }
        return null;
    }
}
