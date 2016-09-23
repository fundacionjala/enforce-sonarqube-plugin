package org.fundacionjala.sonarqube.tree;

import javax.annotation.Nullable;
import java.util.List;

public interface ClassTree extends StatementTree {

    @Nullable
    SyntaxToken declarationKeyword();
/*
    @Nullable
    IdentifierTree simpleName();

    TypeParameters typeParameters();

    ModifiersTree modifiers();

    @Nullable
    TypeTree superClass();

    ListTree<TypeTree> superInterfaces();

    @Nullable
    SyntaxToken openBraceToken();

    List<Tree> members();

    @Nullable
    SyntaxToken closeBraceToken();*/

}