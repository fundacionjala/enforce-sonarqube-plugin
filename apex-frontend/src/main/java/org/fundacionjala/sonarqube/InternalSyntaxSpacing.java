package org.fundacionjala.sonarqube;

import org.fundacionjala.sonarqube.tree.ApexTree;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;

public class InternalSyntaxSpacing extends ApexTree{
    private final int start;
    private final int end;

    public InternalSyntaxSpacing(int start, int end) {
        super(null);
        this.start = start;
        this.end = end;
    }

    @Override
    public Tree.Kind kind() {
        // FIXME should have a dedicated kind associated with a dedicated interface.
        return Tree.Kind.TRIVIA;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Iterable<Tree> children() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(BaseTreeVisitor visitor) {
        // Do nothing at the moment. Spacings are dropped anyway.
    }

    public int start() {
        return start;
    }

    public int end() {
        return end;
    }
}
