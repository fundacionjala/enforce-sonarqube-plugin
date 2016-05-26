/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_EACH_LOOP;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarForEachLoopTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(FOR_EACH_LOOP);
    }

    @Test
    public void testValidForEachLoop() {
        assertThat(parser)
                .matches("(integer variable: anotherVariable) {}")
                .matches("(bool trueExpression: anotherVariable) {}");
    }

    @Test
    public void testInvalidForEachLoop() {
        assertThat(parser)
                .notMatches("integer variable;")
                .notMatches("(integer variable: anotherVariable {}")
                .notMatches("integer variable anotherVariable) {}")
                .notMatches("somethingelse;");
    }
}
