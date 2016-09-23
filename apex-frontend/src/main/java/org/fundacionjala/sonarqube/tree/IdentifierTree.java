package org.fundacionjala.sonarqube.tree;

public interface IdentifierTree extends ExpressionTree, TypeTree{
    SyntaxToken identifierToken();
    String name();
}
