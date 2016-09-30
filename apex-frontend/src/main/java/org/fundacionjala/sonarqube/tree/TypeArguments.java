package org.fundacionjala.sonarqube.tree;

import org.fundacionjala.sonarqube.treeimplementation.ListTreeImpl;

public interface TypeArguments extends ListTree<Tree>{

    SyntaxToken openBracketToken();

    SyntaxToken closeBracketToken();

}
