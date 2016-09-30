package org.fundacionjala.sonarqube.tree;

import java.util.List;

public interface CompilationUnitTree extends Tree {

    List<Tree> types();

    SyntaxToken eofToken();

}
