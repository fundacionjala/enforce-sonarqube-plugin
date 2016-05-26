/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FLOATING_POINT_LITERAL_NUMBER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarFloatingPointLiteralNumberTest extends ApexRuleTest{
    
    @Before
    public void setUp() {
        setRootRule(FLOATING_POINT_LITERAL_NUMBER);
    }
    
    @Test
    public void testValidFloatingPointLiteralNumber() {
        assertThat(parser)
                .matches("1234545.6789e-12345")
                .matches(".123345e-1235")
                .matches("123123e232d")
                .matches("12312345D") 
                .matches("312345d")
                .matches("0x1234567890abcdefp-123456789F")
                .matches("0x1234567890abcdef.abcdef1234567890")
                .matches("0x123345ab.abcdep+123D");
    }
}
