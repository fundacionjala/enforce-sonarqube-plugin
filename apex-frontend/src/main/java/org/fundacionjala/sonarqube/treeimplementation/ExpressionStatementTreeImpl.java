package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.tree.*;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;

import javax.annotation.Nullable;
import java.util.Collections;

public class ExpressionStatementTreeImpl extends ApexTree implements ExpressionStatementTree {

    private final ExpressionTree expression;
    private final InternalSyntaxToken semicolonToken;

    public ExpressionStatementTreeImpl(ExpressionTree expression, @Nullable InternalSyntaxToken semicolonToken) {
        super(Kind.EXPRESSION_STATEMENT);

        this.expression = Preconditions.checkNotNull(expression);
        this.semicolonToken = semicolonToken;
    }

    @Override
    public Kind kind() {
        return Kind.EXPRESSION_STATEMENT;
    }

    @Override
    public ExpressionTree expression() {
        return expression;
    }

    @Override
    // FIXME There isn't always a semicolon, for example within "for" initializers
    public SyntaxToken semicolonToken() {
        return semicolonToken;
    }

    @Override
    public void accept(BaseTreeVisitor visitor) {
        visitor.visitExpressionStatement(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                Collections.singletonList(expression),
                semicolonToken != null ? Collections.singletonList(semicolonToken) : Collections.<Tree>emptyList());
    }

}
