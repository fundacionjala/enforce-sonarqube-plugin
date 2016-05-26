/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DECIMAL_FLOATING_POINT_LITERAL;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarDecimalFloatingPointLiteralTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(DECIMAL_FLOATING_POINT_LITERAL);
    }

    @Test
    public void testValidDecimalFloatingPointLiteral() {
        assertThat(parser)
                .matches("123123.23422e-32323f")
                .matches("123123.23422d")
                .matches(".23422e+123f")
                .matches(".23422f")
                .matches("123.123f")
                .matches("123123e-23422F")
                .matches("123123e+23422F")
                .matches("123123d");
    }
}
