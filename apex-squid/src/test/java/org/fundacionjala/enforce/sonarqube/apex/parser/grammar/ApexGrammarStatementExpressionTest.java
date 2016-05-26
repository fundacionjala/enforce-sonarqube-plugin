/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarStatementExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(STATEMENT_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("++x")
                .matches("--x")
                .matches("++(a.b-c.d*5)")
                .matches("x++")
                .matches("(x.z()*y)--")
                .matches("(x.z()) = (a.b(c, null))")
                .matches("x.z()*= (4-2)/(2+y)");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("x 1")
                .notMatches("x**")
                .notMatches("++");
    }
}
