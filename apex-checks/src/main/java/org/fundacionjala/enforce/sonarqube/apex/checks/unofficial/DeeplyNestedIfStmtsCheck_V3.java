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

import java.util.*;

import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;

/**
 * Verification of the name of the method in a class.
 */
@Rule(key = DeeplyNestedIfStmtsCheck.CHECK_KEY)
public class DeeplyNestedIfStmtsCheck_V3 extends SquidCheck<Grammar> {

	/**
	 * It is the code of the rule for the plugin.
	 */
	public static final String CHECK_KEY = "DeeplyNestedIfStmts";

	public final Integer DEFAULT_IF_DEPTH = 3;

	private final String MESSAGE = ChecksBundle.getStringFromBundle("DeeplyNestedIfStmtsCheckMessage");

	/**
	 * The variables are initialized and subscribe the base rule.
	 */
	@Override
	public void init() {
		subscribeTo(ApexGrammarRuleKey.METHOD_DECLARATION);
	}

	/**
	 * Start at the top of each method declaration.
	 * Inspect each top level if-then statement.
	 *
	 * @param node starts us at the very top of the method declaration.
	 */
	@Override
	public void visitNode(AstNode node) {
		try {
			List<AstNode> topLevelChildren = node.getFirstChild(ApexGrammarRuleKey.BLOCK).getChildren(ApexGrammarRuleKey.BLOCK_STATEMENT);		// top level of method body
			for(AstNode child : topLevelChildren) {
				if (child.hasDirectChildren(ApexGrammarRuleKey.STATEMENT)) {
					child = child.getFirstChild(ApexGrammarRuleKey.STATEMENT);
					resetParamValues();
					recursiveMethod(child);
				}
			}
		} catch (Exception e) {
			ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
		}
	}

	int counter = 0;
	int ifLevel = 0;
	AstNode errorIfNode = null;
	AstNode alreadyErrorNode = null;
	
	/**
	 * This method is used to reset local parameters.
	 * 
	 */
	private void resetParamValues(){
		counter = 0;
		ifLevel = 0;
		errorIfNode = null;
		alreadyErrorNode = null;
	}
	
	/**
	 * This is recursive method to get the depth of IF_STATEMENTS.
	 * 
	 * @param node starts us at the STATEMENT GrammarRule Key to check availability of further IF_STATEMENT.
	 */
	private void recursiveMethod(AstNode astNode){
		List<AstNode> ifStatement = astNode.getChildren(ApexGrammarRuleKey.IF_STATEMENT);
		if(ifStatement.isEmpty() && counter == ifLevel){
			ifLevel--;
		}
		for (AstNode moreIfs: ifStatement) {
			ifLevel++;
			if(ifLevel <= counter){
				counter = ifLevel;
			}else{
				counter++;
			}
			errorIfNode = moreIfs;
			List<AstNode> blockAstNode = getNestedBlockStatements(moreIfs);
			for(AstNode child : blockAstNode) {
				if (child.hasDirectChildren(ApexGrammarRuleKey.STATEMENT)) {
					child = child.getFirstChild(ApexGrammarRuleKey.STATEMENT);
					recursiveMethod(child);
				}
			}
		}
		if(counter >= DEFAULT_IF_DEPTH){
			if(alreadyErrorNode != errorIfNode){
				alreadyErrorNode = errorIfNode;
				getContext().createLineViolation(this, MESSAGE, errorIfNode, DEFAULT_IF_DEPTH.toString());
			}
			counter = ifLevel;
			ifLevel--;
		}
		
	}
	
	/**
	 * This method is used to get all BLOCK_STATEMENT exist in current IF_STATEMENT.
	 * 
	 * @param node starts us at the IF_STATEMENT GrammarRule Key to get list of BLOCK_STATEMENT.
	 */
	private List<AstNode> getNestedBlockStatements(AstNode node){
		List<AstNode> blockAstNode = new ArrayList<AstNode>();
		if(node.hasDirectChildren(ApexGrammarRuleKey.STATEMENT)){
			node = node.getFirstChild(ApexGrammarRuleKey.STATEMENT);
			if(node.hasDirectChildren(ApexGrammarRuleKey.BLOCK)){
				blockAstNode = node.getFirstChild(ApexGrammarRuleKey.BLOCK)
											.getChildren(ApexGrammarRuleKey.BLOCK_STATEMENT);
			}
		}
		return blockAstNode;
	}
}