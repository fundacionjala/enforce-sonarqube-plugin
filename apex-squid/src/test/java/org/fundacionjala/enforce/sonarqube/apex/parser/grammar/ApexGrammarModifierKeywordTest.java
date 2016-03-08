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
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.junit.Before;
import org.junit.Test;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;

import static org.sonar.sslr.tests.Assertions.assertThat;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.MODIFIER_KEYWORD;

public class ApexGrammarModifierKeywordTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(MODIFIER_KEYWORD);
    }

    @Test
    public void buildingModifierWithOnlyCasesLookahead() {
        assertThat(parser)
                .matches("static")
                .matches("public")
                .matches("final")
                .matches("abstract")
                .matches("synchronized")
                .matches("native")
                .matches("transient")
                .matches("volatile")
                .matches("strictfp")
                .matches("anotation");
    }

    @Test
    public void buildingModifierDifferentCorrectCases() {
        assertThat(parser)
                .matches("static")
                .matches("public with sharing")
                .matches("final without sharing")
                .matches("abstract with sharing")
                .matches("synchronized with sharing")
                .matches("native with sharing")
                .matches("transient with sharing");
    }

    @Test
    public void buildingModifierDifferentIncorrectCases() {
        assertThat(parser)
                .notMatches("_")
                .notMatches("static _")
                .notMatches("public With sharing")
                .notMatches("final  without Sharing")
                .notMatches("  _abstract with sharing")
                .notMatches("synchronized_with sharing")
                .notMatches("  transient with sharing_  ");
    }
}
