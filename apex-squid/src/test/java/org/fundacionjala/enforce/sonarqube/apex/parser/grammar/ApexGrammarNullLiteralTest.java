/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NULL_LITERAL;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarNullLiteralTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(NULL_LITERAL);
    }

    @Test
    public void testValidNullLiteral() {
        assertThat(parser)
                .matches("null")
                .matches("NULL")
                .matches("nuLL");
    }

    @Test
    public void testInvalidNullLiteral() {
        assertThat(parser)
                .notMatches("nul");
    }
}
