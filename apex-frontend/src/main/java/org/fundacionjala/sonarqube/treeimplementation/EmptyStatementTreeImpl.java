package org.fundacionjala.sonarqube.treeimplementation;

import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.ApexTree;
import org.fundacionjala.sonarqube.tree.EmptyStatementTree;
import org.fundacionjala.sonarqube.tree.SyntaxToken;
import org.fundacionjala.sonarqube.tree.Tree;

import java.util.Collections;

public class EmptyStatementTreeImpl extends ApexTree implements EmptyStatementTree {
    private final InternalSyntaxToken semicolonToken;

    public EmptyStatementTreeImpl(InternalSyntaxToken semicolonToken) {
        super(Kind.EMPTY_STATEMENT);
        this.semicolonToken = semicolonToken;
    }

    @Override
    public Kind kind() {
        return Kind.EMPTY_STATEMENT;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitEmptyStatement(this);
    }

    @Override
    public SyntaxToken semicolonToken() {
        return semicolonToken;
    }

    @Override
    public Iterable<Tree> children() {
        return Collections.<Tree>singletonList(semicolonToken);
    }
}
