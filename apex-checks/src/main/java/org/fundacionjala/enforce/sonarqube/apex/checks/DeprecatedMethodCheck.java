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
 * This class checks that a deprecated method does not contain lines of code.
 */
@Rule(
        key = DeprecatedMethodCheck.CHECK_KEY,
        priority = Priority.INFO,
        name = "Verification methods deprecated",
        description = "Prevent the body of a method contains deprecated code lines",
        tags = Tags.OBSOLETE
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("1min")
@ActivatedByDefault
public class DeprecatedMethodCheck extends SquidCheck<Grammar> {

    /**
     * Stores a message template.
     */
    public static final String MESSAGE = "The \"%s\" method is deprecated, suggest deleting the contents of the method";

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1006";

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
        if (astNode.getFirstDescendant(RuleKey.ANNOTATION) != null) {
            List<AstNode> childrensMethodDeclaration = astNode.getChildren();
            if (validateToken(childrensMethodDeclaration.get(0).getChildren())) {
                AstNode nameMethod = validateStatementBlock(childrensMethodDeclaration);
                if (nameMethod != null) {
                    getContext().createLineViolation(this, String.format(MESSAGE,
                            nameMethod.getTokenValue()), astNode);
                }
            }
        }
    }

    /**
     * Checks if node token type is deprecated.
     *
     * @param astNode is the node to analyze.
     * @return A value of true, if the node is expected.
     */
    private boolean validateToken(List<AstNode> astNode) {
        return astNode.get(1).getTokenValue().equals(ApexKeyword.DEPRECATED.getValue());
    }

    /**
     * Checks if the node BLOCK_STATEMENT has content.
     *
     * @param astNode is the node to analyze.
     * @return If the node has content, returns the same node, null if has not.
     */
    private AstNode validateStatementBlock(List<AstNode> astNode) {
        AstNode nameMethod = null;
        for (AstNode node : astNode) {
            if (node.getName().equals(RuleKey.METHOD_NAME.toString())) {
                nameMethod = node;
            }
            if (node.getName().equals(RuleKey.STATEMENT_BLOCK.toString())) {
                if (node.getChildren().size() > 2) {
                    return nameMethod;
                }

            }
        }
        return null;
    }
}
