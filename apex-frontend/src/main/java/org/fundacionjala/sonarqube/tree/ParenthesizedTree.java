package org.fundacionjala.sonarqube.tree;

public interface ParenthesizedTree extends ExpressionTree{
    SyntaxToken openParen();

    ExpressionTree expression();

    SyntaxToken closePare();
}
