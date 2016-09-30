package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.*;

import javax.annotation.Nullable;
import java.util.List;

public class MemberSelectExpressionTreeImpl extends ApexTree implements MemberSelectExpressionTree{

    private ExpressionTree expression;

    private InternalSyntaxToken dotToken;
    private final IdentifierTree identifier;

    public MemberSelectExpressionTreeImpl(InternalSyntaxToken dotToken, IdentifierTreeImpl identifier) {
        super(Kind.MEMBER_SELECT);

        this.dotToken = dotToken;
        this.identifier = identifier;
    }

    public MemberSelectExpressionTreeImpl(ExpressionTree expression, InternalSyntaxToken dotToken, IdentifierTree identifier) {
        super(Kind.MEMBER_SELECT);

        this.expression = Preconditions.checkNotNull(expression);
        this.dotToken = dotToken;
        this.identifier = Preconditions.checkNotNull(identifier);
    }

    public MemberSelectExpressionTreeImpl completeWithExpression(ExpressionTree expression) {
        Preconditions.checkState(this.expression == null);
        ExpressionTree result = expression;

        this.expression = result;

        return this;
    }

    @Override
    public Kind kind() {
        return Kind.MEMBER_SELECT;
    }

    @Override
    public ExpressionTree expression() {
        return expression;
    }

    @Override
    public SyntaxToken operatorToken() {
        return dotToken;
    }

    @Override
    public IdentifierTree identifier() {
        return identifier;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitMemberSelectExpression(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                Lists.newArrayList(
                        expression,
                        dotToken,
                        identifier));
    }

    @Override
    public Type symbolType() {
        return null;
    }
}
