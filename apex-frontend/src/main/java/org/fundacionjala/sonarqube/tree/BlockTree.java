package org.fundacionjala.sonarqube.tree;


import java.util.List;

public interface BlockTree extends StatementTree{

    SyntaxToken openBraceToken();

    List<StatementTree> body();

    SyntaxToken closeBraceToken();

}
