package org.fundacionjala.sonarqube.tree;

public interface TypeArguments extends ListTree<Tree>{

    SyntaxToken openBracketToken();

    SyntaxToken closeBracketToken();

}
