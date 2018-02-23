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

import org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;

/**
 * Verification of the name of the method in a class.
 */
@Rule(key = MethodLengthCheck.CHECK_KEY)
public class MethodLengthCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "MethodLength";

    /**
     * The structure must have the name of the method.
     */
    public static final Integer DEFAULT_METHOD_LENGTH = 80;

    private final String MESSAGE = ChecksBundle.getStringFromBundle("MethodLengthCheckMessage");

    /**
     * The variables are initialized and subscribe the base rule.
     */
    
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.METHOD_DECLARATION, ApexGrammarRuleKey.QUERY_EXPRESSION);
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
            LookForMethodLength(astNode);
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }
    
    private void LookForMethodLength(AstNode methodDeclarationNode) {
    	List<AstNode> astNodes = methodDeclarationNode.getDescendants(ApexPunctuator.LBRACE, ApexPunctuator.RBRACE);
    	int lBraceLineNumber = 0;
    	int rBraceLineNumber = 0;
    	int astIdex = 0;
    	
    	
    	if (astNodes.get(astIdex).getName().equals("LBRACE")){
			lBraceLineNumber = astNodes.get(astIdex).getTokenLine();
	 	}
    	astIdex = astNodes.size() - 1;
		if (astNodes.get(astIdex).getName().equals("RBRACE")){
			rBraceLineNumber = astNodes.get(astIdex).getTokenLine();
		}
        
        if((rBraceLineNumber - lBraceLineNumber) > DEFAULT_METHOD_LENGTH){
			getContext().createLineViolation(this, MESSAGE, methodDeclarationNode, DEFAULT_METHOD_LENGTH.toString());
		}
    }
}