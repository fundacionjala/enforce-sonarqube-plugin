package org.fundacionjala.sonarqube.treeimplementation;

import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.tree.ApexTree;
import org.fundacionjala.sonarqube.tree.NamingKeywordsTree;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;

public class NamingKeywordsTreeImpl extends ApexTree implements NamingKeywordsTree {

    private InternalSyntaxToken identifier;

    public NamingKeywordsTreeImpl(Kind kind, InternalSyntaxToken identifier) {
        super(kind);
        this.identifier = identifier;
    }

    @Override
    protected Iterable<Tree> children() {
        return null;
    }

    @Override
    public InternalSyntaxToken idendifier() {
        return identifier;
    }

    @Override
    public void accept(BaseTreeVisitor visitor) {

    }

    @Override
    public Kind kind() {
        return null;
    }
}
