/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.HEXADECIMAL_FLOATING_POINT_LITERAL;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarHexadecimalFloatingPointLiteralTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(HEXADECIMAL_FLOATING_POINT_LITERAL);
    }

    @Test
    public void testValidHexadecimalFloatingPointLiteral() {
        assertThat(parser)
                .matches("0x1234567890abcdefp-12345F")
                .matches("0X12354abcdep-12345F")
                .matches("0x12345abce.1234abdcedfD")
                .matches("0x112345ab.1235abdcep1235d");
    }
    
    @Test
    public void testInvalidHexadecimalFloatingPointLiteral(){
        assertThat(parser)
                .notMatches("0x1235534327443.p-2734823f");
    }

}
