package org.fundacionjala.sonarqube.tree;

import org.fundacionjala.sonarqube.InternalSyntaxToken;

import javax.annotation.Nullable;

public interface VariableTree extends StatementTree{

    ModifiersTree modifiers();

    TypeTree type();

    InternalSyntaxToken simpleName();

    @Nullable
    ExpressionTree initializer();

    @Nullable
    SyntaxToken endToken();

}
