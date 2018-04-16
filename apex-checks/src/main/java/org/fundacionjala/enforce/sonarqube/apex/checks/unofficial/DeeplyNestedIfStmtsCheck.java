/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 * Added By Rahul Agrawal: to Check Class Length, It should not be more than 500 Lines
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import org.sonar.squidbridge.checks.SquidCheck;

import org.sonar.check.Rule;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;

/**
 * Verification of the name of the method in a class.
 */
@Rule(key = DeeplyNestedIfStmtsCheck.CHECK_KEY)
public class DeeplyNestedIfStmtsCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "DeeplyNestedIfStmts";

    /**
     * The structure must have the name of the method.
     */
    public final Integer DEFAULT_IF_DEPTH = 3;

    private final String MESSAGE = ChecksBundle.getStringFromBundle("DeeplyNestedIfStmtsCheckMessage");

    /**
     * The variables are initialized and subscribe the base rule.
     */
    
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.IF_STATEMENT);
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
        		int count = 0;
        		if (astNode.is(ApexGrammarRuleKey.IF_STATEMENT)) {  	// subscribeTo latches onto the first case of the argument
        			count = 1;										// so we will likely always increment by one
        		}
            while(astNode.hasDescendant(ApexGrammarRuleKey.IF_STATEMENT)) {
        			System.out.println(astNode + ", Count: "+ count);
            		System.out.println("Descendants: " + astNode.getDescendants(ApexGrammarRuleKey.IF_STATEMENT));
            		if (!astNode.getPreviousAstNode().getTokenValue().equals("ELSE")){ 	// do not increment if it's an else
            			count++;															// increment for if statement
            		}
            		astNode = astNode.getFirstDescendant(ApexGrammarRuleKey.IF_STATEMENT);
            }
            if (astNode.is(ApexGrammarRuleKey.IF_STATEMENT) && !astNode.getPreviousAstNode().getTokenValue().equals("ELSE")) {
            		count++;
            }
            if (count > DEFAULT_IF_DEPTH) {
            		getContext().createLineViolation(this, MESSAGE, astNode, DEFAULT_IF_DEPTH.toString());
            }
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }
}