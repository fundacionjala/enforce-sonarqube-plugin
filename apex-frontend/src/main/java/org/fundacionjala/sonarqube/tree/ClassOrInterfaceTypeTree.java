package org.fundacionjala.sonarqube.tree;

import org.fundacionjala.sonarqube.api.ApexPunctuator;

public interface ClassOrInterfaceTypeTree extends Tree{

    SyntaxToken keywordsAsIdentifier();

    ApexPunctuator dot();

    SyntaxToken methodCallingFromKeyword();

}
