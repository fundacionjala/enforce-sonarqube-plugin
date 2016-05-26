/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STRING_LITERAL_STRING;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarStringLiteralStringTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(STRING_LITERAL_STRING);
    }

    @Test
    public void testValidStringLiteralString() {
        assertThat(parser)
                .matches("'anyword'")
                .matches("'single quotes'");
    }

    @Test
    public void testInvalidStringLiteralString() {
        assertThat(parser)
                .notMatches("");
    }
}