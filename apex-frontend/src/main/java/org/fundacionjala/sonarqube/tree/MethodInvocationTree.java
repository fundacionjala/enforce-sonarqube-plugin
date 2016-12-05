package org.fundacionjala.sonarqube.tree;

import org.fundacionjala.sonarqube.semantic.Symbol;

import javax.annotation.Nullable;

public interface MethodInvocationTree extends ExpressionTree {

    @Nullable
    TypeArguments typeArguments();

    ExpressionTree methodSelect();

    Arguments arguments();

    Symbol.Kind symbol();
}
