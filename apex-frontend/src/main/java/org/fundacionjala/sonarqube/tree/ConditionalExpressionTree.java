package org.fundacionjala.sonarqube.tree;

public interface ConditionalExpressionTree extends ExpressionTree{

    ExpressionTree condition();

    SyntaxToken questionToken();

    ExpressionTree trueExpression();

    SyntaxToken colonToken();

    ExpressionTree falseExpression();
}
