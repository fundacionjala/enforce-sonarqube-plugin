/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;
import org.sonar.squidbridge.checks.SquidCheck;

public class DmlStatementCheck extends SquidCheck<Grammar> {

    /**
     * Stores a message template.
     */
    protected String message;

    /**
     * Stores the rule to subscribe.
     */
    protected ApexGrammarRuleKey ruleKey;

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(ruleKey);
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
            if (astNode.hasDescendant(ApexGrammarRuleKey.DML_OPERATIONS)) {
                getContext().createLineViolation(this, String.format(message,
                        astNode.getFirstDescendant(ApexGrammarRuleKey.DML_OPERATIONS).getTokenOriginalValue()), astNode);
            }
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }
}
