/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.UNARY_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarUnaryExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(UNARY_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("3")
                .matches("a")
                .matches("someExpression")
                .matches("-3")
                .matches("+-a")
                .matches("-+-3")
                .matches("+something")
                .matches("--+-a")
                //with preincrement and predecrement expressions
                .matches("--a")
                .matches("++b")
                .matches("- ++a")
                .matches("+ - + ++b")
                //with not-plus-minus expressions
                .matches("!a")
                .matches("+!-+b")
                .matches("- !+--!++a")
                .matches("+ - + ++b");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches(" a*")
                .notMatches(" a+")
                .notMatches(" a - b");
    }
}
