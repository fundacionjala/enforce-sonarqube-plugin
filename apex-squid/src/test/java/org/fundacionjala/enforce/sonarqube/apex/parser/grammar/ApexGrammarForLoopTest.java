/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_LOOP;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarForLoopTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(FOR_LOOP);
    }

    @Test
    public void testValidForLoop() {
        assertThat(parser)
                .matches("(integer variable = 3; addition; expression){}")
                .matches("(;;){}")
                .matches("(; dosomething; updateExpression){}")
                .matches("(integer addition; addToAddition; updateExpression){}")
                .matches("(integer addition,multiplication; addBoth; updateExpression){}");
    }

    @Test
    public void testInvalidForLoop() {
        assertThat(parser)
                .notMatches("integer variable =3; doSomethin{}")
                .notMatches("(;;)");
    }
}
