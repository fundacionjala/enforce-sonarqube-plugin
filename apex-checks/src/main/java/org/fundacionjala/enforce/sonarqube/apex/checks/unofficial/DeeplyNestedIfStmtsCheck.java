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

import java.util.List;

import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
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
        		recursive(astNode);
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }
    
	public int recursive(AstNode astNode) {
    		int count = 1;
    		List<AstNode> children = astNode.getChildren(ApexKeyword.IF); 	// this is getting just one child
    		for (AstNode sib : children) {									// bad recursion
   			if (!sib.getPreviousAstNode().equals(ApexKeyword.ELSE)){
        			count++;
    			}
    			if(sib.hasDescendant(ApexGrammarRuleKey.IF_STATEMENT)) {
    				count += recursive(sib.getFirstDescendant(ApexGrammarRuleKey.IF_STATEMENT));
    			}
    			if (count > DEFAULT_IF_DEPTH) {
    				getContext().createLineViolation(this, MESSAGE, sib, DEFAULT_IF_DEPTH.toString());
    			}
    		}
		return count;
    }
}