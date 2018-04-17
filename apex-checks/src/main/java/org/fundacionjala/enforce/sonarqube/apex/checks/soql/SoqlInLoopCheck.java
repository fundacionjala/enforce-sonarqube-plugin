/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.soql;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

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
    		  if(astNode.getType() == ApexGrammarRuleKey.DO_STATEMENT || astNode.getType() == ApexGrammarRuleKey.WHILE_STATEMENT){
    			  astNode = astNode.getFirstChild(ApexGrammarRuleKey.STATEMENT);
    		  }else if(astNode.getType() == ApexGrammarRuleKey.FOR_STATEMENT && astNode.hasDirectChildren(ApexGrammarRuleKey.FOR_EACH_LOOP)){
    			  astNode = astNode.getFirstChild(ApexGrammarRuleKey.FOR_EACH_LOOP).getFirstChild(ApexGrammarRuleKey.STATEMENT);
    		  }
    		  
    		  if (astNode.getType() == ApexGrammarRuleKey.STATEMENT && astNode.hasDescendant(ApexGrammarRuleKey.QUERY_EXPRESSION)) {
                  getContext().createLineViolation(this, String.format(message,
                          astNode.getFirstDescendant(ApexGrammarRuleKey.QUERY_EXPRESSION).getTokenOriginalValue()), astNode);
              }
          } catch (Exception e) {
              ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
          }
    } // TODO: what if nested? what if conditional if inside loop?
}
