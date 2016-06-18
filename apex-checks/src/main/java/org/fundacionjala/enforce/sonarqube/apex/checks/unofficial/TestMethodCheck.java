/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import com.sonar.sslr.api.AstNode;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;

import java.util.List;

/**
 * Verifies if a class contains test methods.
 */
@Rule(key = TestMethodCheck.CHECK_KEY)
public class TestMethodCheck extends AnnotationMethodCheck {

    /**
     * Stores a message template.
     */
    public static final String MESSAGE = ChecksBundle.getStringFromBundle("TestMethodCheckMessage");

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1007";

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.CLASS_OR_INTERFACE_DECLARATION);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        AstNode modifierNode = null;
        if (astNode.hasParent(ApexGrammarRuleKey.TYPE_DECLARATION)) {
            modifierNode = astNode.getParent().getFirstChild(ApexGrammarRuleKey.MODIFIERS);
        }
        if (!isAnnotation(modifierNode, IS_TEST)) {
            List<AstNode> methods = astNode.getDescendants(ApexGrammarRuleKey.METHOD_DECLARATION);
            methods.stream().forEach((method) -> {
                AstNode parent = method.getParent();
                AstNode firstChild = parent.getFirstDescendant(ApexGrammarRuleKey.MODIFIERS);
                if (isTest(firstChild)) {
                    getContext().createLineViolation(this, methodMessage(method), method);
                }
            });
        }
    }

    /**
     * Returns the method message.
     *
     * @param astNode current node.
     * @return the message.
     */
    private String methodMessage(AstNode astNode) {
        AstNode method = astNode.getFirstDescendant(ApexGrammarRuleKey.METHOD_IDENTIFIER);
        return String.format(MESSAGE, method.getTokenOriginalValue());
    }
}
