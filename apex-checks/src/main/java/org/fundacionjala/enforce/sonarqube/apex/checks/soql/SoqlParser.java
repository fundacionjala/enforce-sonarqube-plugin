/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.soql;

import org.apache.commons.lang.StringUtils;
import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;
import org.fundacionjala.enforce.sonarqube.apex.parser.ApexParser;

import com.google.common.base.Charsets;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;

public class SoqlParser {

    /**
     * String used to log.
     */
    private static final String CLASS_TO_LOG = "SoqlParser";

    /**
     * String used to log.
     */
    private static final String METHOD_TO_LOG = "parseQuery";

    /**
     * The beginning of a SOQL sentence.
     */
    private static final String SOQL_START = "'SELECT";

    /**
     * Checks if a node is a query by checking if it's a string and then it's
     * beginning.
     *
     * @param astNode The node to check.
     * @return True if the node is a query as String.
     */
    public static boolean isStringQuery(AstNode astNode) {
        return astNode.is(ApexGrammarRuleKey.STRING_LITERAL_STRING)
                && astNode.getTokenValue().startsWith(SOQL_START);
    }

    /**
     * Re-parses a query that was retrieved as a String.
     *
     * @param astNode The String's node.
     * @return The query as a new QUERY_EXPRESSION node.
     */
    public static AstNode parseQuery(AstNode astNode) {
        AstNode parsedQuery = null;
        try {
            String string = astNode.getTokenOriginalValue();
            String queryAsString = StringUtils.substringBetween(string, "'", "'");
            Parser<Grammar> queryParser = ApexParser.create(new ApexConfiguration(Charsets.UTF_8));
            queryParser.setRootRule(queryParser.getGrammar().rule(ApexGrammarRuleKey.QUERY_EXPRESSION));
            parsedQuery = queryParser.parse(queryAsString);
        } catch (Exception e) {
            ChecksLogger.logCheckError(CLASS_TO_LOG, METHOD_TO_LOG, e.toString());
        }
        return parsedQuery;
    }
}
