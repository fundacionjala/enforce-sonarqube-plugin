/*
 * The MIT License
 *
 * Copyright 2016 Fundacion Jala.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import java.util.List;
import java.util.Objects;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey;

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
public class AssertMethodCheck extends SquidCheck<Grammar> {

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
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(RuleKey.METHOD_DECLARATION);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        if (!isTest(astNode)) {
            return;
        }
        List<AstNode> expressions = astNode.getDescendants(RuleKey.ARGUMENTS);
        expressions.forEach(expression -> {
            if (isAssert(expression)) {
                String first = getValue(expression, 0);
                if (Objects.equals(first, "true")) {
                    getContext().createLineViolation(this, ASSERT_MESSAGE, expression);
                } else {
                    String second = getValue(expression, 2);
                    if (Objects.equals(first, second)) {
                        getContext().createLineViolation(this, ASSERT_EQUALS_MESSAGE, expression);
                    }
                }
            }
        });
    }

    /**
     * Analyzes if a node is test class or method.
     *
     * @param astNode to be analyzed.
     * @return the analysis result.
     */
    private boolean isTest(AstNode astNode) {
        astNode = astNode.getFirstChild(RuleKey.ANNOTATION);
        if (astNode != null) {
            astNode = astNode.getFirstChild(ApexKeyword.IS_TEST);
        }
        return astNode != null;
    }

    /**
     * Analyzes if a node is assert method.
     *
     * @param expression current node.
     * @return the analysis result.
     */
    private boolean isAssert(AstNode expression) {
        AstNode parent = expression.getParent();
        return parent.getTokenValue().matches("assert(Equals)?");
    }

    /**
     * Returns the token value from a requested index.
     *
     * @param expression current expression.
     * @param index requested index.
     * @return the value.
     */
    private String getValue(AstNode expression, int index) {
        List<AstNode> arguments = expression.getChildren();
        return (arguments.size() < index) ? null
                : arguments.get(index).getTokenValue();
    }
}
