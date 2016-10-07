package org.fundacionjala.sonarqube.tree;

import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.ApexLexer;

import java.util.List;

public interface MethodTree extends Tree{

    ModifiersTree modifiers();

    TypeTree returnType();

    InternalSyntaxToken simpleName();

    SyntaxToken openParenToken();

    List<VariableTree> parameters();

    SyntaxToken closeParenToken();

    BlockTree block();

}
