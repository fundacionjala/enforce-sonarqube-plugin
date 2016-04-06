/*
 * The MIT License
 *
 * Copyright 2016 Fundacion Jala.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.sonar.squidbridge.checks.SquidCheck;

import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;

/**
 * Verifies if a class or method is an annotation.
 */
public abstract class AnnotationMethodCheck extends SquidCheck<Grammar> {
    
    /**
     * Analyzes if a node is test class or method.
     *
     * @param astNode to be analyzed.
     * @return the analysis result.
     */
    protected boolean isTest(AstNode astNode) {
        return isAnnotation(astNode, ApexKeyword.IS_TEST);
    }
    
    /**
     * Analyzes if a node is deprecated class or method.
     *
     * @param astNode to be analyzed.
     * @return the analysis result.
     */
    protected boolean isDeprecated(AstNode astNode) {
        return isAnnotation(astNode, ApexKeyword.DEPRECATED);
    }
    
    /**
     * Analyzes if a node is annotation class or method.
     *
     * @param astNode to be analyzed.
     * @param keyword to be found.
     * @return the analysis result.
     */
    protected boolean isAnnotation(AstNode astNode, ApexKeyword keyword) {
        boolean result = Boolean.FALSE;
        if (astNode.hasDirectChildren(ApexGrammarRuleKey.ANNOTATION)) {
            AstNode annotation = astNode.getFirstChild(ApexGrammarRuleKey.ANNOTATION);
            result = annotation.hasDirectChildren(keyword);
        }
        return result;
    }
}
