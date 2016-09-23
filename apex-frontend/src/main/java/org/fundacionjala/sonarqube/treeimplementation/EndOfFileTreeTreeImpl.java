package org.fundacionjala.sonarqube.treeimplementation;

import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.EndOfFileTree;
import org.fundacionjala.sonarqube.tree.SyntaxToken;
import org.fundacionjala.sonarqube.tree.Tree;

import javax.annotation.Nullable;

public class EndOfFileTreeTreeImpl implements EndOfFileTree {
    @Override
    public boolean is(Kind... kinds) {
        return false;
    }

    @Override
    public void accept(TreeVisitor visitor) {

    }

    @Nullable
    @Override
    public Tree parent() {
        return null;
    }

    @Nullable
    @Override
    public SyntaxToken firstToken() {
        return null;
    }

    @Nullable
    @Override
    public SyntaxToken lastToken() {
        return null;
    }

    @Override
    public Kind kind() {
        return null;
    }
}
