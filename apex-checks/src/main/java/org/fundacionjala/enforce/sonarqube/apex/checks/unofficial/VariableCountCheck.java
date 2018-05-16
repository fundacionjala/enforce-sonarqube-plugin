/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

import java.util.List;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.squidbridge.checks.SquidCheck;

import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;

/**
 * Verifies if a class contains test methods.
 */
@Rule(key = VariableCountCheck.CHECK_KEY) // standard setup
public class VariableCountCheck extends SquidCheck<Grammar> {
	/**
     * Stores the rule to subscribe.
     */
    protected ApexGrammarRuleKey ruleKey;
	
    /**
     * Stores a message template.
     */
    protected String message = ChecksBundle.getStringFromBundle("VariableCountCheck"); // create a property with standard message ChecksResourceBundle.prop

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "VariableCount"; // create new html and json files

    /**
     * The structure must have the name of the method.
     */
    public static final int DEFAULT_VARIABLE_COUNT = 6;

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
         subscribeTo(ApexGrammarRuleKey.CLASS_OR_INTERFACE_DECLARATION);
    }

    /*
     * Rule Property to make configurable variable
     */
    @RuleProperty(
    	    key = "max",
    	    description = "Maximum allowed variables in class.",
    	    defaultValue = ""+DEFAULT_VARIABLE_COUNT)
    	  int max = DEFAULT_VARIABLE_COUNT;
    

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
    	  try {
    		  checkVariableCount(astNode);
    	  } catch (Exception e) {
              ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
          }
    }
    
    private void checkVariableCount(AstNode astNode){
    	List<AstNode> lstAstNode = astNode.getDescendants(ApexGrammarRuleKey.PROPERTY_DECLARATION, ApexGrammarRuleKey.FIELD_DECLARATION);
    	
        if(lstAstNode.size() >= max){
        	getContext().createLineViolation(this, message, astNode, max);
    		
        }
    }
}
