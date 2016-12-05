package org.fundacionjala.sonarqube.tree;

public interface MemberSelectExpressionTree extends ExpressionTree, TypeTree{

    ExpressionTree expression();

    SyntaxToken operatorToken();

    IdentifierTree identifier();

}
