/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 * Added By Rahul Agrawal: to Check Class Length, It should not be more than 500 Lines
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
@Rule(key = ClassLengthCheck.CHECK_KEY)
public class ClassLengthCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "ClassLength";

    /**
     * The structure must have the name of the method.
     */
    public static final int DEFAULT_CLASS_LENGTH = 600;

    private final String MESSAGE = ChecksBundle.getStringFromBundle("ClassLengthCheckMessage");

    /**
     * The variables are initialized and subscribe the base rule.
     */
    
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.CLASS_OR_INTERFACE_BODY);
    }

    /*
     * Rule Property to make configurable variable
     */
    @RuleProperty(
    	    key = "max",
    	    description = "Maximum allowed statements per function",
    	    defaultValue = ""+DEFAULT_CLASS_LENGTH)
    	  int max = DEFAULT_CLASS_LENGTH;
    
    
    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        try {
            LookForClassLength(astNode);
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }
    
    private void LookForClassLength(AstNode classDeclarationNode) {
    	if(classDeclarationNode.getNextSibling().getTokenLine() > max){
	        getContext().createLineViolation(this, MESSAGE, classDeclarationNode, max);
        }
        
    }
}