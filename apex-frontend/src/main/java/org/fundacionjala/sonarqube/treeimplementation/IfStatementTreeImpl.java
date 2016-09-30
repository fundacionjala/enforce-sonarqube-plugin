package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.*;

import javax.annotation.Nullable;
import java.util.Collections;

/**
 * Created by kevin_titichoca on 30-09-16.
 */
public class IfStatementTreeImpl extends ApexTree implements IfStatementTree {

    private InternalSyntaxToken ifKeyword;
    private InternalSyntaxToken openParenToken;
    private ExpressionTree condition;
    private InternalSyntaxToken closeParenToken;
    private StatementTree thenStatement;
    @Nullable
    private final InternalSyntaxToken elseKeyword;
    @Nullable
    private final StatementTree elseStatement;

    public IfStatementTreeImpl(InternalSyntaxToken elseKeyword, StatementTree elseStatement) {
        super(Kind.IF_STATEMENT);
        this.elseKeyword = elseKeyword;
        this.elseStatement = Preconditions.checkNotNull(elseStatement);
    }

    public IfStatementTreeImpl(InternalSyntaxToken ifKeyword, InternalSyntaxToken openParenToken, ExpressionTree condition, InternalSyntaxToken closeParenToken,
                               StatementTree thenStatement) {

        super(Kind.IF_STATEMENT);
        this.ifKeyword = ifKeyword;
        this.openParenToken = openParenToken;
        this.condition = Preconditions.checkNotNull(condition);
        this.closeParenToken = closeParenToken;
        this.thenStatement = Preconditions.checkNotNull(thenStatement);
        this.elseStatement = null;
        this.elseKeyword = null;
    }

    public IfStatementTreeImpl complete(InternalSyntaxToken ifKeyword, InternalSyntaxToken openParenToken, ExpressionTree condition, InternalSyntaxToken closeParenToken,
                                        StatementTree thenStatement) {
        Preconditions.checkState(this.condition == null, "Already completed");
        this.ifKeyword = ifKeyword;
        this.openParenToken = openParenToken;
        this.condition = Preconditions.checkNotNull(condition);
        this.closeParenToken = closeParenToken;
        this.thenStatement = Preconditions.checkNotNull(thenStatement);

        return this;
    }

    @Override
    public Kind kind() {
        return Kind.IF_STATEMENT;
    }

    @Override
    public SyntaxToken ifKeyword() {
        return ifKeyword;
    }

    @Override
    public SyntaxToken openParenToken() {
        return openParenToken;
    }

    @Override
    public ExpressionTree condition() {
        return condition;
    }

    @Override
    public SyntaxToken closeParenToken() {
        return closeParenToken;
    }

    @Override
    public StatementTree thenStatement() {
        return thenStatement;
    }

    @Nullable
    @Override
    public SyntaxToken elseKeyword() {
        return elseKeyword;
    }

    @Nullable
    @Override
    public StatementTree elseStatement() {
        return elseStatement;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitIfStatement(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                Lists.newArrayList(ifKeyword, openParenToken, condition, closeParenToken, thenStatement),
                elseKeyword != null ? Lists.newArrayList(elseKeyword, elseStatement) : Collections.<Tree>emptyList());
    }
}
