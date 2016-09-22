package org.fundacionjala.sonarqube.tree;

import org.fundacionjala.sonarqube.tree.Tree;

public interface SyntaxTrivia extends Tree {

    String comment();

    int startLine();

    int column();
}
