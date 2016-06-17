/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LITERAL;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarLiteralTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(LITERAL);
    }

    @Test
    public void testValidLiteral() {
        assertThat(parser)
                .matches("323l")
                .matches("323d")
                .matches("1.1f")
                .matches("0x123abcde.1235p212F")
                .matches("1.2323e+12d")
                .matches("0x123f")
                .matches("0x123.432p-323")
                .matches("'anyString'")
                .matches("true")
                .matches("false")
                .matches("null")
                .matches("without")
                .matches("limit")
                .matches("True")
                .matches("False")
                .matches("NULL")
                .matches("nuLL");
    }

    @Test
    public void testInvalidLiteral() {
        assertThat(parser)
                .notMatches("323l120x234abcd")
                .notMatches("0x234234.p-234f")
                .notMatches("nul");
    }
}
