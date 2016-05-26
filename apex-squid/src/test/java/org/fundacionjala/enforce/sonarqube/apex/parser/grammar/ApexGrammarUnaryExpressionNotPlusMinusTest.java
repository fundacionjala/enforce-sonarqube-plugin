/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.UNARY_EXPRESSION_NOT_PLUS_MINUS;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarUnaryExpressionNotPlusMinusTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(UNARY_EXPRESSION_NOT_PLUS_MINUS);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("!x")
                .matches("!+x")
                .matches("!-!x")
                .matches("!x.y")
                .matches("!-+++--!-+x")
                //with cast expression
                .matches("(someType) var")
                .matches("(someType) -!+-var")
                .matches("(list<someType>) var")
                .matches("(someType[]) var")
                //with primary expression
                .matches("var")
                .matches("3")
                .matches("'something'")
                .matches("a.b.c[0].d(p1, p2)")
                .matches("x++")
                .matches("x--");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("a!")
                .notMatches("!")
                .notMatches("!-")
                .notMatches("a ! b");
    }
}
