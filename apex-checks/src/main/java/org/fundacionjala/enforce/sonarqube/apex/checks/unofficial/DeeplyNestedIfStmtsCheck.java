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
				//System.out.println("bChild >>" + bChild);
				blockStatementChildren = bChild.getChildren(ApexGrammarRuleKey.BLOCK_STATEMENT);
				for(AstNode bsChild : blockStatementChildren){
					//System.out.println("bsChild >>" + bsChild);
					statementChildren = bsChild.getChildren(ApexGrammarRuleKey.STATEMENT);
					//System.out.println("statementChildren >>" + statementChildren);
					for(AstNode sChild : statementChildren){
						//System.out.println("sChild >>" + sChild);
						counter = 0;
						//System.out.println("counter >>" + counter);
						recursiveStatementMethod(sChild);
					}
				}
			}
		} catch (Exception e) {
			ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
		}
	}
	
	private void recursiveStatementMethod(AstNode astStatement){
		//System.out.println(" In recursiveStatementMethod" );
		if(astStatement.hasDirectChildren(ApexGrammarRuleKey.COMPOUND_STATEMENT_EXPRESSION)){
			for(AstNode compoundStatementNode : astStatement.getChildren(ApexGrammarRuleKey.COMPOUND_STATEMENT_EXPRESSION)){
				//System.out.println("compoundStatementNode >>" + compoundStatementNode);
				if(!compoundStatementNode.hasDirectChildren(ApexGrammarRuleKey.BLOCK)){
					//System.out.println("Before Continue");
					continue;
				}
				for(AstNode blockNode : compoundStatementNode.getChildren(ApexGrammarRuleKey.BLOCK)){
					//System.out.println("blockNode >>" + blockNode);
					traverseBlockStatement(blockNode);
				}
			}
		}else // If we found IfStatement then increase counter, validate against max value and set violation if required
			if(astStatement.hasDirectChildren(ApexGrammarRuleKey.IF_STATEMENT)){ 
			//System.out.println("Has If_Statment>>" + astStatement);
			counter ++;
			//System.out.println("counter++_0 >>" + counter);
			AstNode ifStatement = astStatement.getFirstChild(ApexGrammarRuleKey.IF_STATEMENT);
			if(counter >= max){
				//System.out.println("counter++ >>" + counter);
				getContext().createLineViolation(this, MESSAGE, ifStatement, max);
				counter --;
				return;
			}
			for(AstNode statementNode : ifStatement.getChildren(ApexGrammarRuleKey.STATEMENT)){
				//System.out.println("statementNode ++ >>" + statementNode);
				recursiveStatementMethod(statementNode);
				counter --;
			}
		}else{ // If we found any statement rather then IfStatement then process further and call recursive methods to get IFStatements
			//System.out.println("HAs Not If_Statement");
			for(AstNode otherChildren : astStatement.getChildren()){
				//System.out.println("otherChildren >>" + otherChildren);
				if(otherChildren.is(ApexGrammarRuleKey.BLOCK)){
					//System.out.println("blockNode_1 >>" + otherChildren);
					traverseBlockStatement(otherChildren);
				}else{
					for(AstNode statementNode : otherChildren.getChildren(ApexGrammarRuleKey.STATEMENT)){
						//System.out.println("statementNode_2 >>" + statementNode);
						recursiveStatementMethod(statementNode);
					}// end of for(AstNode statementNode....
				} // end of else
			} // end of for(AstNode otherChildren....
		} // end of else...
	} //end of method

	// Method to traverse BlockStatement, used at multiple place.
	private void traverseBlockStatement(AstNode blockNode){
		for(AstNode blockStatementNode : blockNode.getChildren(ApexGrammarRuleKey.BLOCK_STATEMENT)){
			//System.out.println("blockStatementNode >>" + blockStatementNode);
			for(AstNode statementNode : blockStatementNode.getChildren(ApexGrammarRuleKey.STATEMENT)){
				//System.out.println("statementNode >>" + statementNode);
				recursiveStatementMethod(statementNode);
			}
		}
	}
}