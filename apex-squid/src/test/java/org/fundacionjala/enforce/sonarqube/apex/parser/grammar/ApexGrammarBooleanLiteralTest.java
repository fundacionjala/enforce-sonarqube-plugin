/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BOOLEAN_LITERAL;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarBooleanLiteralTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(BOOLEAN_LITERAL);
    }

    @Test
    public void testValidBooleanLiteral() {
        assertThat(parser)
                .matches("true")
                .matches("false")
                .matches("TRue")
                .matches("False");
    }

    @Test
    public void testInvalidBooleanLiteral() {
        assertThat(parser)
                .notMatches("falSes")
                .notMatches("trues")
                .notMatches("");
    }
}
