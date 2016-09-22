package org.fundacionjala.sonarqube.tree;

import java.util.List;

public interface CompilationUnitTree extends Tree {

    List<TypeDeclarationTree> typeDeclarations();

    SyntaxToken eofToken();

}
