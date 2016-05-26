/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_INIT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarForInitTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(FOR_INIT);
    }

    @Test
    public void testValidForInit() {
        assertThat(parser)
                .matches("integer addition = 3")
                .matches("integer division,anotherDivision")
                .matches("final integer division = 3")
                .matches("variable,anotherOne");
    }

    @Test
    public void testInvalidForInit() {
        assertThat(parser)
                .notMatches("integer variable;")
                .notMatches("integer variable; anotherVariable;");
    }
}
