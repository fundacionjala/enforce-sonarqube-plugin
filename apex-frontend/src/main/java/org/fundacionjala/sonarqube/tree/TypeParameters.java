package org.fundacionjala.sonarqube.tree;

import javax.annotation.Nullable;

public interface TypeParameters extends ListTree<TypeParameterTree> {
    @Nullable
    SyntaxToken openBracketToken();

    @Nullable
    SyntaxToken closeBracketToken();
}
