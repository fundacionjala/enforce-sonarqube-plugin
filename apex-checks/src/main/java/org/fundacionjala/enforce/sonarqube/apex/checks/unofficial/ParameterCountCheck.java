/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 * Added By Rahul Agrawal: to Check Parameter Count in Methods/Constructor, It should not be more than 5 Parameters
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import org.sonar.squidbridge.checks.SquidCheck;

import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;

/**
 * Verification of the name of the method in a class.
 */
@Rule(key = ParameterCountCheck.CHECK_KEY)
public class ParameterCountCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "ParameterCount";

    /**
     * The structure must have the name of the method.
     */
    public static final int DEFAULT_PARAM_COUNT = 5;

    private final String MESSAGE = ChecksBundle.getStringFromBundle("ParameterCountCheckMessage");

    

    /**
     * The variables are initialized and subscribe the base rule.
     */
    
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.METHOD_DECLARATION, ApexGrammarRuleKey.CONSTRUCTOR_DECLARATION);
    }
    
    /*
     * Rule Property to make configurable variable
     */
    @RuleProperty(
    	    key = "max",
    	    description = "Maximum allowed statements per function",
    	    defaultValue = ""+DEFAULT_PARAM_COUNT)
    	  int max = DEFAULT_PARAM_COUNT;
    
    

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        try {
            lookForParameterCount(astNode);
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }
    
    private void lookForParameterCount(AstNode methodDeclarationNode) {
        if(methodDeclarationNode.getDescendants(ApexGrammarRuleKey.FORMAL_PARAMETER).size() > max){
	        getContext().createLineViolation(this, MESSAGE, methodDeclarationNode, max);
        }
        
    }
}