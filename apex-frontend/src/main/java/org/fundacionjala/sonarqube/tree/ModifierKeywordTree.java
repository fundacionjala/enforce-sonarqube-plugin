package org.fundacionjala.sonarqube.tree;

public interface ModifierKeywordTree extends ModifierTree{

    Modifier modifier();

    SyntaxToken keyword();

}
