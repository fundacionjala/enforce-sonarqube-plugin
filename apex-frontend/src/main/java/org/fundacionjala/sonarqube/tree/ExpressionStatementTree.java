package org.fundacionjala.sonarqube.tree;

public interface ExpressionStatementTree extends StatementTree{

    ExpressionTree expression();

    SyntaxToken semicolonToken();

}
