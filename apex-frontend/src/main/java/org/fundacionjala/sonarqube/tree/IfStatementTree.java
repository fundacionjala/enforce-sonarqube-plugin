package org.fundacionjala.sonarqube.tree;

import javax.annotation.Nullable;

public interface IfStatementTree extends StatementTree{

    SyntaxToken ifKeyword();

    SyntaxToken openParenToken();

    ExpressionTree condition();

    SyntaxToken closeParenToken();

    StatementTree thenStatement();

    @Nullable
    SyntaxToken elseKeyword();

    @Nullable
    StatementTree elseStatement();
}
