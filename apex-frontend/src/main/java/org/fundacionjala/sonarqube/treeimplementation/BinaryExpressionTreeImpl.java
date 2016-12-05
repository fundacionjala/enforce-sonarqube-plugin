package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.tree.*;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;

public class BinaryExpressionTreeImpl extends ApexTree implements BinaryExpressionTree {

    private final Tree.Kind kind;

    private final ExpressionTree leftOperand;
    private final InternalSyntaxToken operator;
    private final ExpressionTree rightOperand;

    public BinaryExpressionTreeImpl(Tree.Kind kind, ExpressionTree leftOperand, InternalSyntaxToken operator, ExpressionTree rightOperand) {
        super(kind);
        this.kind = Preconditions.checkNotNull(kind);
        this.leftOperand = Preconditions.checkNotNull(leftOperand);
        this.operator = operator;
        this.rightOperand = Preconditions.checkNotNull(rightOperand);
    }

    @Override
    public ExpressionTree leftOperand() {
        return leftOperand;
    }

    @Override
    public SyntaxToken operatorToken() {
        return operator;
    }

    @Override
    public ExpressionTree rightOperand() {
        return rightOperand;
    }

    @Override
    public Kind kind() {
        return kind;
    }

    @Override
    public void accept(BaseTreeVisitor visitor) {
        visitor.visitBinaryExpression(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Lists.newArrayList(
                leftOperand,
                operator,
                rightOperand);
    }

    @Override
    public TypeTree symbolType() {
        return null;
    }
}
