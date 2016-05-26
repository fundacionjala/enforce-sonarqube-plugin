/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SHIFT_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarShiftExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(SHIFT_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("3")
                .matches("a")
                .matches("someExpression")
                .matches("3 << 4")
                .matches("a >>= b >>= c << d")
                //with nested Additive Expressions
                .matches("a + b - c")
                .matches("a + b >>= c -d");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches(" a < < b")
                .notMatches(" a >> = b")
                .notMatches(" a b <<");
    }
}
