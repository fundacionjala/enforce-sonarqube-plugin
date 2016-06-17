/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import com.sonar.sslr.api.AstNode;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;

/**
 * This class checks that a deprecated method does not contain lines of code.
 */
@Rule(key = DeprecatedMethodCheck.CHECK_KEY)
public class DeprecatedMethodCheck extends AnnotationMethodCheck {

    /**
     * Stores a message template.
     */
    public static final String MESSAGE = ChecksBundle.getStringFromBundle("DeprecatedMethodCheckMessage");

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1006";

    /**
     * Stores the number of elements for empty block counting the start and end
     * brace.
     */
    public static final int EMPTY_BLOCK = 2;

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.METHOD_DECLARATION);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        AstNode parent = astNode.getParent();
        AstNode modifiersNode = parent.getFirstDescendant(ApexGrammarRuleKey.MODIFIERS);
        AstNode blockNode = astNode.getFirstDescendant(ApexGrammarRuleKey.BLOCK);
        if (isDeprecated(modifiersNode) && !isEmptyBlock(blockNode)) {
            AstNode method = astNode.getFirstDescendant(ApexGrammarRuleKey.METHOD_IDENTIFIER);
            getContext().createLineViolation(this, String.format(MESSAGE,
                    method.getTokenOriginalValue()), method);
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
