package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.*;

public class ConditionalExpressionTreeImpl extends ApexTree implements ConditionalExpressionTree{

    private ExpressionTree condition;
    private final InternalSyntaxToken queryToken;
    private final ExpressionTree trueExpression;
    private final InternalSyntaxToken colonToken;
    private final ExpressionTree falseExpression;

    public ConditionalExpressionTreeImpl(InternalSyntaxToken queryToken, ExpressionTree trueExpression, InternalSyntaxToken colonToken, ExpressionTree falseExpression) {

        super(Kind.CONDITIONAL_EXPRESSION);
        this.queryToken = queryToken;
        this.trueExpression = trueExpression;
        this.colonToken = colonToken;
        this.falseExpression = falseExpression;
    }

    public ConditionalExpressionTreeImpl complete(ExpressionTree condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public Kind kind() {
        return Kind.CONDITIONAL_EXPRESSION;
    }

    @Override
    public ExpressionTree condition() {
        return condition;
    }

    @Override
    public SyntaxToken questionToken() {
        return queryToken;
    }

    @Override
    public ExpressionTree trueExpression() {
        return trueExpression;
    }

    @Override
    public SyntaxToken colonToken() {
        return colonToken;
    }

    @Override
    public ExpressionTree falseExpression() {
        return falseExpression;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitConditionalExpression(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Lists.newArrayList(
                condition,
                queryToken,
                trueExpression,
                colonToken,
                falseExpression
        );
    }

    @Override
    public Type symbolType() {
        return null;
    }
}
