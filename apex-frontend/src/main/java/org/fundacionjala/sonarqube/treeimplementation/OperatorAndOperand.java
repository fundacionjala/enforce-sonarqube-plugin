package org.fundacionjala.sonarqube.treeimplementation;

import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.tree.ExpressionTree;

public class OperatorAndOperand {

    private final InternalSyntaxToken operator;
    private final ExpressionTree operand;

    public OperatorAndOperand(InternalSyntaxToken operator, ExpressionTree operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public InternalSyntaxToken operator() {
        return operator;
    }

    public ExpressionTree operand() {
        return operand;
    }
}
