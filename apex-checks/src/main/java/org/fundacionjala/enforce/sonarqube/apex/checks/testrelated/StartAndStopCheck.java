/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import java.util.List;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

/**
 * Verifies that only testStart and testStop are only used once in a testMethod.
 */
@Rule(key = StartAndStopCheck.CHECK_KEY)
public class StartAndStopCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1027";

    private final String MESSAGE = ChecksBundle.getStringFromBundle("StartAndStopCheckMessage");
    
    /**
     * Keyword for the first part of the expression.
     */
    private final String TEST = "TEST";

    /**
     * One of the Keywords for the second part of the expression.
     */
    private final String START = "STARTTEST";

    /**
     * One of the Keywords for the second part of the expression.
     */
    private final String STOP = "STOPTEST";
    
    /**
     * Maximum of allowed instances of the specified method calls.
     */
    private final int MAX_ALLOWED_INSTANCES = 1;

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
        int startCalls = 0;
        int stopCalls = 0;
        List<AstNode> expressions = astNode.getDescendants(ApexGrammarRuleKey.PRIMARY_EXPRESSION);
        for (AstNode expression : expressions) {
            if (isTestMethodCall(expression, START)) {
                startCalls++;
            }
            if (isTestMethodCall(expression, STOP)) {
                stopCalls++;
            }
            if (startCalls > MAX_ALLOWED_INSTANCES || stopCalls > MAX_ALLOWED_INSTANCES) {
                getContext().createLineViolation(this,
                        MESSAGE, astNode, 
                        astNode.getFirstDescendant(ApexGrammarRuleKey.METHOD_IDENTIFIER).getTokenOriginalValue());
                return;
            }
        }
    }
    
    /**
     * Verifies if the expression is a sequence of test.startTest or test.stopTest
     * @param expression 
     * @param keyword
     * @return true if the expression is the right sequence
     */
    private boolean isTestMethodCall(AstNode expression, String keyword) {
        AstNode name = expression.getFirstDescendant(ApexGrammarRuleKey.NAME);
        if (name != null) {
            AstNode first = name.getFirstChild(ApexGrammarRuleKey.METHOD_IDENTIFIER);
            AstNode last = name.getLastChild(ApexGrammarRuleKey.METHOD_IDENTIFIER);
            return first != null && last != null
                    && first.getTokenValue().equals(TEST)
                    && last.getTokenValue().matches(keyword);
        }
        return false;
    }
}
