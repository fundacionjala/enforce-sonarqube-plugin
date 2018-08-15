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

import java.util.ArrayList;
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

	public static final int DEFAULT_IF_DEPTH = 3;

	private final String MESSAGE = ChecksBundle.getStringFromBundle("DeeplyNestedIfStmtsCheckMessage");

	/**
	 * The variables are initialized and subscribe the base rule.
	 */
	@Override
	public void init() {
		subscribeTo(ApexGrammarRuleKey.METHOD_DECLARATION);
	}
	
	/*
     * Rule Property to make configurable variable
     */
    @RuleProperty(
    	    key = "max",
    	    description = "Maximum allowed nested IF statement in each method",
    	    defaultValue = ""+DEFAULT_IF_DEPTH)
    	  int max = DEFAULT_IF_DEPTH;

    int counter = 0;
    
	/**
	 * Start at the top of each method declaration.
	 * Inspect each top level if-then statement.
	 *
	 * @param node starts us at the very top of the method declaration.
	 */
	@Override
	public void visitNode(AstNode node) {
		try {
			List<AstNode> blockChildren = node.getChildren(ApexGrammarRuleKey.BLOCK);
			List<AstNode> blockStatementChildren;
			List<AstNode> statementChildren;
			
			for(AstNode bChild : blockChildren){
				blockStatementChildren = bChild.getChildren(ApexGrammarRuleKey.BLOCK_STATEMENT);
				for(AstNode bsChild : blockStatementChildren){
					statementChildren = bsChild.getChildren(ApexGrammarRuleKey.STATEMENT);
					for(AstNode sChild : statementChildren){
						counter = 0;
						recursiveStatementMethod(sChild);
					}
				}
			}
		} catch (Exception e) {
			ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
		}
	}
	
	private void recursiveStatementMethod(AstNode astStatement){
		if(astStatement.hasDirectChildren(ApexGrammarRuleKey.COMPOUND_STATEMENT_EXPRESSION)){
			for(AstNode compoundStatementNode : astStatement.getChildren(ApexGrammarRuleKey.COMPOUND_STATEMENT_EXPRESSION)){
				if(!compoundStatementNode.hasDirectChildren(ApexGrammarRuleKey.BLOCK)){
					continue;
				}
				for(AstNode blockNode : compoundStatementNode.getChildren(ApexGrammarRuleKey.BLOCK)){
					traverseBlockStatement(blockNode);
				}
			}
		}else // If we found IfStatement then increase counter, validate against max value and set violation if required
			if(astStatement.hasDirectChildren(ApexGrammarRuleKey.IF_STATEMENT)){ 
			counter ++;
			AstNode ifStatement = astStatement.getFirstChild(ApexGrammarRuleKey.IF_STATEMENT);
			if(counter >= max){
				getContext().createLineViolation(this, MESSAGE, ifStatement, max);
				counter --;
				return;
			}
			for(AstNode statementNode : ifStatement.getChildren(ApexGrammarRuleKey.STATEMENT)){
				recursiveStatementMethod(statementNode);
				counter --;
			}
		}else{ // If we found any statement rather then IfStatement then process further and call recursive methods to get IFStatements
			for(AstNode otherChildren : astStatement.getChildren()){
				if(otherChildren.is(ApexGrammarRuleKey.BLOCK)){
					traverseBlockStatement(otherChildren);
				}else{
					for(AstNode statementNode : otherChildren.getChildren(ApexGrammarRuleKey.STATEMENT)){
						recursiveStatementMethod(statementNode);
					}// end of for(AstNode statementNode....
				} // end of else
			} // end of for(AstNode otherChildren....
		} // end of else...
	} //end of method

	// Method to traverse BlockStatement, used at multiple place.
	private void traverseBlockStatement(AstNode blockNode){
		for(AstNode blockStatementNode : blockNode.getChildren(ApexGrammarRuleKey.BLOCK_STATEMENT)){
			for(AstNode statementNode : blockStatementNode.getChildren(ApexGrammarRuleKey.STATEMENT)){
				recursiveStatementMethod(statementNode);
			}
		}
	}
}