/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.soql;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import java.util.List;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;

/**
 * Verifies if a class contains test methods.
 */
@Rule(key = SoqlInLoopCheck.CHECK_KEY) // standard setup
public class SoqlInLoopCheck extends SquidCheck<Grammar> {
	/**
     * Stores the rule to subscribe.
     */
    protected ApexGrammarRuleKey ruleKey;
	
    /**
     * Stores a message template.
     */
    protected String message = ChecksBundle.getStringFromBundle("SoqlFileInLoopCheckMessage"); // create a property with standard message ChecksResourceBundle.prop

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "SoqlFileInLoop"; // create new html and json files

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
         subscribeTo(ApexGrammarRuleKey.FOR_STATEMENT, ApexGrammarRuleKey.DO_STATEMENT, ApexGrammarRuleKey.WHILE_STATEMENT);
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
    		  astNode = getStatementAstNode(astNode);
    		  if ((astNode.is(ApexGrammarRuleKey.STATEMENT) && astNode.hasDescendant(ApexGrammarRuleKey.QUERY_EXPRESSION))) {
                  getContext().createLineViolation(this, String.format(message,
                          astNode.getFirstDescendant(ApexGrammarRuleKey.QUERY_EXPRESSION).getTokenOriginalValue()), astNode.getFirstDescendant(ApexGrammarRuleKey.QUERY_EXPRESSION));
              }
          } catch (Exception e) {
              ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
          }
    }
    
    private AstNode getStatementAstNode(AstNode astNode){
    		AstNode statementAstNode = astNode.getFirstChild(ApexGrammarRuleKey.STATEMENT);
	    	if(statementAstNode == null){
	    		statementAstNode = astNode.getLastChild(); // For this Rule, Last Child will be FOR_LOOP or FOR_EACH_LOOP
	    		statementAstNode = getStatementAstNode(statementAstNode);
	    	}
	    	return statementAstNode;
    }
}
