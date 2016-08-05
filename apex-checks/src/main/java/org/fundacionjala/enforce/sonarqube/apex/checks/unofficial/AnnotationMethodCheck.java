/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.sonar.squidbridge.checks.SquidCheck;

/**
 * Verifies if a class or method is an annotation.
 */
public abstract class AnnotationMethodCheck extends SquidCheck<Grammar> {
    
    static final String IS_TEST = "isTest";
    
    static final String DEPRECATED = "deprecated";
    
    /**
     * Analyzes if a node is test class or method.
     *
     * @param astNode to be analyzed.
     * @return the analysis result.
     */
    protected boolean isTest(AstNode astNode) {
        return isAnnotation(astNode, IS_TEST);
    }
    
    /**
     * Analyzes if a node is deprecated class or method.
     *
     * @param astNode to be analyzed.
     * @return the analysis result.
     */
    protected boolean isDeprecated(AstNode astNode) {
        return isAnnotation(astNode, DEPRECATED);
    }
    
    /**
     * Analyzes if a node is annotation class or method.
     *
     * @param astNode to be analyzed.
     * @param keyword to be found.
     * @return the analysis result.
     */
    protected boolean isAnnotation(AstNode astNode, String keyword) {
        boolean result = Boolean.FALSE;
        if (astNode.hasDirectChildren(ApexGrammarRuleKey.ANNOTATION)) {
            AstNode annotation = astNode.getFirstChild(ApexGrammarRuleKey.ANNOTATION);
            result = annotation.getFirstChild(ApexGrammarRuleKey.NAME).getTokenOriginalValue().equals(keyword);
        }
        return result;
    }
}
