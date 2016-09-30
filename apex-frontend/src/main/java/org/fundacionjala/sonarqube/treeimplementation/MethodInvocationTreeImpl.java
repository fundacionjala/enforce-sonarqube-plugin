package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.*;

import javax.annotation.Nullable;
import java.util.Collections;

public class MethodInvocationTreeImpl extends ApexTree implements MethodInvocationTree {

    private final ExpressionTree methodSelect;
    private final Arguments arguments;
    @Nullable
    private TypeArguments typeArguments;

    public MethodInvocationTreeImpl(ExpressionTree methodSelect, ArgumentListTreeImpl arguments) {
        super(Kind.METHOD_INVOCATION);
        this.methodSelect = Preconditions.checkNotNull(methodSelect);
        this.typeArguments = typeArguments;
        this.arguments = Preconditions.checkNotNull(arguments);
    }

    @Override
    public Kind kind() {
        return Kind.METHOD_INVOCATION;
    }

    @Nullable
    @Override
    public TypeArguments typeArguments() {
        return typeArguments;
    }

    @Override
    public SyntaxToken firstToken() {
        if (typeArguments() != null && methodSelect.is(Tree.Kind.MEMBER_SELECT)) {
            ExpressionTree expression = ((MemberSelectExpressionTree) methodSelect).expression();
            SyntaxToken firstToken = expression.firstToken();
            if (firstToken != null) {
                return firstToken;
            }
        }
        return super.firstToken();
    }

    @Override
    public ExpressionTree methodSelect() {
        return methodSelect;
    }

    @Override
    public Arguments arguments() {
        return arguments;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitMethodInvocation(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                typeArguments != null ? Collections.singletonList(typeArguments) : Collections.<Tree>emptyList(),
                Lists.newArrayList(methodSelect, arguments));
    }

    @Override
    public Type symbolType() {
        return null;
    }
}
