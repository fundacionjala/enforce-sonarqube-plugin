package org.fundacionjala.sonarqube.tree;

import java.util.List;

public interface MethodTree extends Tree{

    ModifiersTree modifiers();

    TypeTree returnType();

    IdentifierTree simpleName();

    SyntaxToken openParenToken();

    List<VariableTree> parameters();

    SyntaxToken closeParenToken();

    BlockTree block();

}
