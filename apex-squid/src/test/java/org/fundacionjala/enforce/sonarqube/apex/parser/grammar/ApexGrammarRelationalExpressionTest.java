/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.RELATIONAL_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarRelationalExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(RELATIONAL_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("3")
                .matches("a")
                .matches("someExpression")
                .matches("a < b")
                .matches("x > y > z > 0")
                .matches("thisThing >= thatThing")
                .matches("0 <= thisThing <= thatThing >= x")
                //with nested ShiftExpressions
                .matches("a << b")
                .matches("a >>= b < c << d");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches(" a < < b")
                .notMatches(" a b <")
                .notMatches(" a = b")
                .notMatches(" a <> 3");
    }
}
