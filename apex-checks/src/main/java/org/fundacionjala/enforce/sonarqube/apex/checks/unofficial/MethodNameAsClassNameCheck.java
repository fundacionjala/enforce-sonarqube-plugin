/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

//import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_DECLARATION;

import java.util.List;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;

/**
 * Verifies if a class contains test methods.
 */
@Rule(key = MethodNameAsClassNameCheck.CHECK_KEY) // standard setup
public class MethodNameAsClassNameCheck extends SquidCheck<Grammar> {
	/**
     * Stores the rule to subscribe.
     */
    protected ApexGrammarRuleKey ruleKey;
	
    /**
     * Stores a message template.
     */
    protected String message = ChecksBundle.getStringFromBundle("MethodNameAsClassNameCheck"); // create a property with standard message ChecksResourceBundle.prop

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "MethodNameAsClassName"; // create new html and json files

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
         subscribeTo(ApexGrammarRuleKey.CLASS_OR_INTERFACE_DECLARATION);
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
    		  checkMethodNameAsClassName(astNode);
    	  } catch (Exception e) {
              ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
          }
    }
    
    private void checkMethodNameAsClassName(AstNode astNode){
    	String className = astNode.getFirstDescendant(ApexGrammarRuleKey.COMMON_IDENTIFIER).getTokenOriginalValue();
    	List<AstNode> lstAstNode = astNode.getDescendants(ApexGrammarRuleKey.METHOD_DECLARATION);
    	
    	for(AstNode astMethodNode : lstAstNode){
    	
    		if(className.equals(astMethodNode.getFirstDescendant(ApexGrammarRuleKey.METHOD_IDENTIFIER).getTokenOriginalValue()) ){
    			getContext().createLineViolation(this, message, astMethodNode);
    		}
    	}
    }
}
