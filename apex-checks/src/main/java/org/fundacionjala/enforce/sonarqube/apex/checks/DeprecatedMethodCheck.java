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
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;

/**
 * This class checks that a deprecated method does not contain lines of code.
 */
@Rule(
        key = DeprecatedMethodCheck.CHECK_KEY,
        priority = Priority.INFO,
        name = "Deprecated code should be removed eventually",
        description = "Prevent the body of a method contains deprecated code lines",
        tags = Tags.OBSOLETE
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("1min")
@ActivatedByDefault
public class DeprecatedMethodCheck extends AnnotationMethodCheck {

    /**
     * Stores a message template.
     */
    public static final String MESSAGE = "The \"%s\" method is deprecated, suggest deleting the contents of the method";

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1006";

    /**
     * Stores the number of elements for empty block counting the start and end brace.
     */
    public static final int EMPTY_BLOCK = 2;

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.METHOD_DECLARATION_PI);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base. In the event that
     * the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        AstNode parent = astNode.getParent().getParent();
        AstNode modifiersNode = parent.getFirstDescendant(ApexGrammarRuleKey.MODIFIERS);
        AstNode blockNode = astNode.getFirstDescendant(ApexGrammarRuleKey.BLOCK);
        if (isDeprecated(modifiersNode) && !isEmptyBlock(blockNode)) {
            AstNode method = astNode.getFirstDescendant(ApexGrammarRuleKey.METHOD_IDENTIFIER);
            getContext().createLineViolation(this, String.format(MESSAGE,
                    method.getTokenValue()), method);
        }
    }

    /**
     * Checks if the node represents a empty statement block.
     *
     * @param astNode to be analyzed.
     * @return the analysis result.
     */
    private boolean isEmptyBlock(AstNode astNode) {
        return astNode.getNumberOfChildren() == EMPTY_BLOCK;
    }
}
