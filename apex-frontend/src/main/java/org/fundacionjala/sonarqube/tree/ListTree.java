package org.fundacionjala.sonarqube.tree;

import java.util.List;

public interface ListTree<T extends Tree> extends Tree, List<T> {

    List<SyntaxToken> separators();

}
