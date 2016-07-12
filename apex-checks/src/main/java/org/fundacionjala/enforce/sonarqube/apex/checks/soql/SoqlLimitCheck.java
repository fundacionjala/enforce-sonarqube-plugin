/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.soql;

import com.google.common.base.Charsets;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import org.apache.commons.lang.StringUtils;
import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.parser.ApexParser;

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
            if(astNode.is(ApexGrammarRuleKey.STRING_LITERAL_STRING) && astNode.getTokenValue().startsWith("'SELECT")){
                String string = astNode.getTokenOriginalValue();
                String queryAsString = StringUtils.substringBetween(string, "'", "'");
                Parser<Grammar> queryParser = ApexParser.create(new ApexConfiguration(Charsets.UTF_8));
                queryParser.setRootRule(queryParser.getGrammar().rule(ApexGrammarRuleKey.QUERY_EXPRESSION));
                AstNode parsedQuery = queryParser.parse(queryAsString);
                astNode = parsedQuery;
            }
            
            if (!astNode.hasDescendant(ApexGrammarRuleKey.LIMIT_SENTENCE)) {
                getContext().createLineViolation(this, MESSAGE, astNode);
            }
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }
}
