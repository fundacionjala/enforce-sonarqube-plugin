/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.soql;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

@Rule(key = SoqlLimitCheck.CHECK_KEY)
public class SoqlLimitCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "S1001";

    /**
     * Display message of the check.
     */
    private final String MESSAGE = ChecksBundle.getStringFromBundle("SoqlLimitCheckMessage");

    @Override
    public void init() {
    	subscribeTo(ApexGrammarRuleKey.QUERY_EXPRESSION, ApexGrammarRuleKey.STRING_LITERAL_STRING);
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
        	AstNode nodeToCheck = astNode;
            if(SoqlParser.isStringQuery(astNode)){
                AstNode parsedQuery = SoqlParser.parseQuery(astNode);
                if(parsedQuery != null) {
                    nodeToCheck = parsedQuery;
                }
            }

            if (nodeToCheck.is(ApexGrammarRuleKey.QUERY_EXPRESSION) && !nodeToCheck.hasDescendant(ApexGrammarRuleKey.LIMIT_SENTENCE)) {
                getContext().createLineViolation(this, MESSAGE, astNode);
            }
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }
}
