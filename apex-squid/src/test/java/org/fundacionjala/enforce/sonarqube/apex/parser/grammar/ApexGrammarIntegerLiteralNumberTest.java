/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INTEGER_LITERAL_NUMBER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarIntegerLiteralNumberTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(INTEGER_LITERAL_NUMBER);
    }

    @Test
    public void rulesIntegerLiteralNumber() {
        assertThat(parser)
                .matches("12345567890")
                .matches("12345567890l")
                .matches("12345567890L")
                .matches("0x1234567890abcdef")
                .matches("0x1234567890abcdefl")
                .matches("0x1234567890abcdefL")
                .matches("01234567")
                .matches("01234567l")
                .matches("01234567L");
    }

    @Test
    public void rulesIntegerLiteralNumberCaseError() {
        assertThat(parser)
                .notMatches("08")
                .notMatches("0x323bct")
                .notMatches("0123456789");
    }

}
