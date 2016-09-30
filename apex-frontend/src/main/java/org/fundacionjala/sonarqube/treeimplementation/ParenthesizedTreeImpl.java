package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.*;

public class ParenthesizedTreeImpl extends ApexTree implements ParenthesizedTree {

    private final InternalSyntaxToken openParenToken;
    private final ExpressionTree expression;
    private final InternalSyntaxToken closeParenToken;

    public ParenthesizedTreeImpl(InternalSyntaxToken openParenToken, ExpressionTree expression, InternalSyntaxToken closeParenToken) {
        super(Kind.PARENTHESIZED_EXPRESSION);
        this.openParenToken = openParenToken;
        this.expression = Preconditions.checkNotNull(expression);
        this.closeParenToken = closeParenToken;
    }

    @Override
    public Kind kind() {
        return Kind.PARENTHESIZED_EXPRESSION;
    }

    public SyntaxToken openParenToken() {
        return openParenToken;
    }

    @Override
    public SyntaxToken openParen() {
        return null;
    }

    @Override
    public ExpressionTree expression() {
        return expression;
    }

    @Override
    public SyntaxToken closePare() {
        return null;
    }

    public SyntaxToken closeParenToken() {
        return closeParenToken;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitParenthesized(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Lists.newArrayList(
                openParenToken,
                expression,
                closeParenToken);
    }

    @Override
    public Type symbolType() {
        return null;
    }
}
