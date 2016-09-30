package org.fundacionjala.sonarqube.tree;

import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.ApexLexer;

public interface MethodTree extends Tree{

    ModifiersTree modifiers();

    InternalSyntaxToken returnType();

    InternalSyntaxToken simpleName();



}
