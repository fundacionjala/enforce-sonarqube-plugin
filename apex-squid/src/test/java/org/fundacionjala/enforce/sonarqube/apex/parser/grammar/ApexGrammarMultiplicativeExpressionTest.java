/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MULTIPLICATIVE_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarMultiplicativeExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(MULTIPLICATIVE_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("3")
                .matches("a")
                .matches("someExpression")
                .matches("3 * 4")
                .matches("3 / 4")
                .matches("3 % 4")
                .matches("3 * 4 / 12 % 1")
                .matches("a * b % c / d")
                //with nested unary expressions
                .matches("+-a")
                .matches("-a * -+-b")
                .matches("a / 5 * ---x");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches(" a ** b")
                .notMatches(" a */ b")
                .notMatches(" a b %")
                .notMatches(" a + b*c");
    }
}
