/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import java.util.List;

import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;

import static org.fundacionjala.enforce.sonarqube.apex.checks.testrelated.TestClassCheck.IS_TEST;

@Rule(key = SeeAllDataTestCheck.CHECK_KEY)
public class SeeAllDataTestCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1023";

    /**
     * Stores a message template.
     */
    private final String MESSAGE = ChecksBundle.getStringFromBundle("SeeAllDataCheckMessage");

    /**
     * Keyword of the seeAllData parameter.
     */
    private final String SEE_ALL_DATA = "SEEALLDATA";

    /**
     * Second keyword, value "true" assigned to "seeAllData".
     */
    private final String TRUE = "TRUE";

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
        try {
            AstNode identifier = astNode.getFirstDescendant(ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER,
                    ApexGrammarRuleKey.SPECIAL_KEYWORDS_AS_IDENTIFIER);
            if (astNode.getFirstDescendant(ApexGrammarRuleKey.TYPE_CLASS).hasDirectChildren(ApexKeyword.CLASS)) {
                AstNode testAnnotation = getTestAnnotation(astNode);
                if (testAnnotation != null && testAnnotation.hasDirectChildren(ApexGrammarRuleKey.EXPRESSION)) {
                    AstNode expression = testAnnotation.getFirstDescendant(ApexGrammarRuleKey.EXPRESSION);
                    List<AstNode> assignments = expression.getDescendants(ApexPunctuator.ASSIGN);
                    for (AstNode assignment : assignments) {
                        AstNode previousSibling = assignment.getFirstAncestor(
                                ApexGrammarRuleKey.ASSIGNMENT_OPERATOR).getPreviousSibling();
                        AstNode nextSibling = assignment.getFirstAncestor(
                                ApexGrammarRuleKey.ASSIGNMENT_OPERATOR).getNextSibling();
                        if (previousSibling.getTokenValue().equals(SEE_ALL_DATA)
                                && nextSibling.getTokenValue().equals(TRUE)) {
                            getContext().createLineViolation(this,
                                    MESSAGE, testAnnotation, identifier.getTokenOriginalValue());
                        }
                    }
                }
            }
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }

    /**
     * Gets the node that contains the "@isTest" annotation.
     *
     * @param astNode The class declaration node whose modifiers contain the
     * annotation.
     * @return The node of the "@isTest" annotation.
     */
    private AstNode getTestAnnotation(AstNode astNode) {
        AstNode testAnnotation = null;
        AstNode modifiers = astNode.getParent().getFirstDescendant(ApexGrammarRuleKey.MODIFIERS);
        if (modifiers.hasDescendant(ApexGrammarRuleKey.ANNOTATION)) {
            List<AstNode> annotations = modifiers.getDescendants(ApexGrammarRuleKey.ANNOTATION);
            for (AstNode annotation : annotations) {
                String annotationValue = annotation.getFirstDescendant(ApexGrammarRuleKey.NAME).getTokenValue();
                if (annotationValue.equals(IS_TEST)) {
                    testAnnotation = annotation;
                }
            }
        }
        return testAnnotation;
    }
}
