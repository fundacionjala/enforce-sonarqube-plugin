package org.fundacionjala.sonarqube.treeimplementation;

import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.tree.Modifier;
import org.fundacionjala.sonarqube.tree.ModifierKeywordTree;
import org.fundacionjala.sonarqube.tree.SyntaxToken;

public class ModifierKeywordTreeImpl extends InternalSyntaxToken implements ModifierKeywordTree {

    private final Modifier modifier;

    public ModifierKeywordTreeImpl(Modifier modifier, InternalSyntaxToken internalSyntaxToken) {
        super(internalSyntaxToken);
        this.modifier = modifier;
    }

    @Override
    public Modifier modifier() {
        return modifier;
    }

    @Override
    public SyntaxToken keyword() {
        return this;
    }
}
