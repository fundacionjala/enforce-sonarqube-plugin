/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.HEX_LITERAL;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarHexLiteralTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(HEX_LITERAL);
    }

    @Test
    public void rulesHexLiteral() {
        assertThat(parser)
                .matches("0x4564afe")
                .matches("0X46465AFED")
                .matches("0x1323abcde");
    }

    @Test
    public void rulesHexLiteralCaseError() {
        assertThat(parser)
                .notMatches("AFEad")
                .notMatches("1323adf0X");
    }
}
