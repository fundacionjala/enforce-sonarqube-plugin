package org.fundacionjala.sonarqube.tree;

import org.fundacionjala.sonarqube.semantic.Symbol;

import javax.annotation.Nullable;

public interface IdentifierTree extends ExpressionTree, TypeTree{
    SyntaxToken identifierToken();
    String simpleName();
    @Nullable
    Symbol symbol();

}
