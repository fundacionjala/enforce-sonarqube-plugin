/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import java.util.List;
import java.util.Objects;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARGUMENTS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIERS;

/**
 * Verifies if a test method contains invalid asserts.
 */
@Rule(
        key = AssertMethodCheck.CHECK_KEY,
        priority = Priority.MINOR,
        name = "\"assert\" should not have the argument \"true\"",
        description = "It's bad practice to use incorrectly assert methods",
        tags = Tags.CONVENTION
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("6min")
@ActivatedByDefault
public class AssertMethodCheck extends AnnotationMethodCheck {

    /**
     * Stores an error message when to use 'assert(value, value)'.
     */
    public static final String ASSERT_EQUALS_MESSAGE
            = "It's bad practice to use assert(value, value).";

    /**
     * Stores an error message when to use 'assert(true)'.
     */
    public static final String ASSERT_MESSAGE
            = "It's bad practice to use assert(true).";

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1008";

    /**
     * Stores the index of the second argument.
     */
    public static final int SECOND_ARGUMENT = 2;

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.CLASS_OR_INTERFACE_MEMBER);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        AstNode modifierChild = astNode.getFirstDescendant(MODIFIERS);
        if (!isTest(modifierChild)) {
            return;
        }
        List<AstNode> expressions = astNode.getDescendants(ApexGrammarRuleKey.ARGUMENTS);
        expressions.stream().filter((expression) -> (isAssert(expression))).forEach((expression) -> {
            AstNode firstChild = expression.getFirstChild(ARGUMENTS_LIST);
            String first = firstChild.getChildren().get(0).getTokenOriginalValue();
            if (Objects.equals(first, "true")) {
                getContext().createLineViolation(this, ASSERT_MESSAGE, expression);
            } else {
                String second = firstChild.getChildren().get(2).getTokenOriginalValue();
                if (Objects.equals(first, second)) {
                    getContext().createLineViolation(this, ASSERT_EQUALS_MESSAGE, expression);
                }
            }
        });
    }

    /**
     * Analyzes if a node is assert method.
     *
     * @param expression current node.
     * @return the analysis result.
     */
    private boolean isAssert(AstNode expression) {
        AstNode parent = expression.getParent().getParent();
        List<AstNode> children = parent.getChildren();
        for (AstNode child : children) {
            List<AstNode> descendants = child.getDescendants(GenericTokenType.IDENTIFIER);
            for (AstNode descendant : descendants) {
                if (descendant.getTokenOriginalValue().matches("assert(Equals)?")) {
                    return true;
                }
            }
        }
        return false;
    }
}
