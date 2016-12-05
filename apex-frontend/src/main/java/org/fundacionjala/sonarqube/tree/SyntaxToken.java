package org.fundacionjala.sonarqube.tree;

import java.util.List;

public interface SyntaxToken extends Tree {

    String text();

    List<SyntaxTrivia> trivias();

    int line();

    int column();

    int endLine();

    int endColumn();
}
