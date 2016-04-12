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

import com.sonar.sslr.api.AstNode;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;

/**
 * Verifies if a class contains test methods.
 */
@Rule(
        key = TestMethodCheck.CHECK_KEY,
        priority = Priority.MAJOR,
        name = "Test methods incorrectly located",
        description = "Test methods should be written in a test class",
        tags = Tags.CONVENTION
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("6min")
@ActivatedByDefault
public class TestMethodCheck extends AnnotationMethodCheck {

    /**
     * Stores a message template.
     */
    public static final String MESSAGE = "The \"%s\" method corresponds to a test class.";

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1007";

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.CLASS_DECLARATION);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base. In the event that
     * the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        if (isTest(astNode)) {
            return;
        }
        List<AstNode> methods = astNode.getDescendants(ApexGrammarRuleKey.METHOD_DECLARATION);
        methods.forEach(method -> {
            if (isTest(method)) {
                getContext().createLineViolation(this, methodMessage(astNode), method);
            }
        });
    }

    /**
     * Returns the method message.
     *
     * @param astNode current node.
     * @return the message.
     */
    private String methodMessage(AstNode astNode) {
        AstNode method = astNode.getFirstDescendant(ApexGrammarRuleKey.METHOD_IDENTIFIER);
        return String.format(MESSAGE, method.getTokenValue());
    }
}
