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

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey;

/**
 * Checks the return type of the method is equal to the header method type
 */
@Rule(
        key = CheckReturnTypeMethod.CHECK_KEY,
        priority = Priority.MAJOR,
        name = "Checking the return type of the method",
        tags = Tags.CONVENTION
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class CheckReturnTypeMethod extends SquidCheck<Grammar> {

    /**
     * Stores a message template.
     */
    public static final String MESSAGE = "The type of the return value \"%s\", does not match the header \"%s\"";

    /**
     * Id code for the plugin.
     */
    public static final String CHECK_KEY = "A1003";

    /**
     * Stores an AstNode instance which hepls verify the next node.
     */
    private AstNode firstNode;

    /**
     * Subscribe the basic rules.
     */
    @Override
    public void init() {
        subscribeTo(RuleKey.MODIFIERS, RuleKey.RESULT_TYPE);
    }

    /**
     * The tour manager AST nodes.
     *
     * @param node where the checks are done.
     */
    @Override
    public void visitNode(AstNode node) {
        checkNode(node.getFirstDescendant(RuleKey.TYPE_METHOD));

    }

    /**
     * Checks if the new node is different from the first node. If it is true a
     * message is created.
     *
     * @param node used to compare.
     */
    private void checkNode(AstNode node) {
        if (firstNode == null) {
            firstNode = node;
        } else if (!firstNode.getTokenValue().equals(node.getTokenValue())) {
            getContext().createLineViolation(this,
                    String.format(MESSAGE, node.getToken().getValue(), firstNode.getToken().getValue()), node);
        }
    }
}
