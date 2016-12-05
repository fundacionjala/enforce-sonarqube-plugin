package org.fundacionjala.sonarqube.semantic;

import org.fundacionjala.sonarqube.visitors.Scope;

import java.util.Set;

public interface SymbolModelBuilder {

    Scope globalScope();

    void addScope(Scope scope);

    Set<Scope> getScopes();

    Symbol declareSymbol(String name, Symbol.Kind kind, Scope scope);

}
