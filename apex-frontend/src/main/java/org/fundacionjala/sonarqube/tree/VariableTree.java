package org.fundacionjala.sonarqube.tree;

import javax.annotation.Nullable;

public interface VariableTree extends StatementTree{

    ModifiersTree modifiers();

    TypeTree type();

    IdentifierTree simpleName();

    @Nullable
    ExpressionTree initializer();

    @Nullable
    SyntaxToken endToken();

}
