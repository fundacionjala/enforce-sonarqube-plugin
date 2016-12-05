package org.fundacionjala.sonarqube.tree;

public interface BinaryExpressionTree extends ExpressionTree{

    ExpressionTree leftOperand();

    SyntaxToken operatorToken();

    ExpressionTree rightOperand();

}
