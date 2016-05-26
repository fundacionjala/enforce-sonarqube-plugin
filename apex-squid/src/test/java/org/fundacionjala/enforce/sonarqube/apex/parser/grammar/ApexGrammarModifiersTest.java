/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIERS;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarModifiersTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(MODIFIERS);
    }

    @Test
    public void positiveBasicRules() {
        assertThat(parser)
                .matches("public")
                .matches("static")
                .matches("protected")
                .matches("private")
                .matches("global")
                .matches("final")
                .matches("abstract")
                .matches("transient")
                .matches("virtual")
                .matches("override")
                .matches("Abstract")
                .matches("testmethod");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("_Transient")
                .notMatches(" _ ")
                .notMatches("P static")
                .notMatches(" public_")
                .notMatches(" _final")
                .notMatches("gLobla")
                .notMatches("OveRRivde")
                .notMatches("Virtual_")
                .notMatches("testMethossfd__");
    }
}
