package org.fundacionjala.sonarqube.tree;

import javax.annotation.Nullable;

public interface MethodInvocationTree extends ExpressionTree {

    @Nullable
    TypeArguments typeArguments();

    ExpressionTree methodSelect();

    Arguments arguments();
}
