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
					List<AstNode> ifStatement = child.getChildren(ApexGrammarRuleKey.IF_STATEMENT);											// first level if statements that appear
					for (AstNode moreIfs: ifStatement) {
						recursive(moreIfs); 
					}
				}
			}
		} catch (Exception e) {
			ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
		}
	}

	/**
	 * Recursively drill down from if-then condition to if-then body. 
	 * Increment count each time there is an if-then per level.
	 * 
	 * @param node go from if-then condition to if-then body/ statement.
	 * @return number of recursive calls - 1.
	 */
	private int recursive(AstNode node) {
		int count = 1;																														// we start at top level so count at 1
		if (node.hasDirectChildren(ApexGrammarRuleKey.STATEMENT)) { 																			// we drill from if-then condition to body
			node = node.getFirstChild(ApexGrammarRuleKey.STATEMENT);
			List<AstNode> blockAstNode = node.getFirstChild().getChildren(ApexGrammarRuleKey.BLOCK_STATEMENT);
			for (AstNode child : blockAstNode) {
				child = child.getFirstChild(ApexGrammarRuleKey.STATEMENT);																	// looking at if-then statement/ body
				if (!child.getPreviousAstNode().is(ApexKeyword.ELSE) && child.hasDirectChildren(ApexGrammarRuleKey.IF_STATEMENT)){				// check to see if there other if-then
					count += recursive(child.getFirstChild(ApexGrammarRuleKey.IF_STATEMENT)) + 1;												// in the body and recurse + 1 to count
				}
				if (count > DEFAULT_IF_DEPTH) {
					getContext().createLineViolation(this, MESSAGE, child, DEFAULT_IF_DEPTH.toString());
				}
			}
		}
		return count;
	}
}