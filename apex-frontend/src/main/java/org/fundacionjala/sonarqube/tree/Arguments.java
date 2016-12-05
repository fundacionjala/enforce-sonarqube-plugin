package org.fundacionjala.sonarqube.tree;

import javax.annotation.Nullable;

public interface Arguments extends ListTree<ExpressionTree> {

    @Nullable
    SyntaxToken openParenToken();

    @Nullable
    SyntaxToken closeParenToken();
}
