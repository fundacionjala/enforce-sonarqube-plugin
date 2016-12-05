package org.fundacionjala.sonarqube.tree;

public interface TypeParameterTree extends Tree{
    IdentifierTree identifier();

    SyntaxToken extendToken();

    ListTree<Tree> bounds();
}
