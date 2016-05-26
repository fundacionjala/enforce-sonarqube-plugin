/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATOR;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarVariableDeclaratorTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(VARIABLE_DECLARATOR);
    }

    @Test
    public void testValidVariableDeclarator() {
        assertThat(parser)
                .matches("transient = this")
                .matches("summ = 1")
                .matches("group")
                .matches("aNewVariableWithNoValue")
                .matches("myVariable = this.that");
    }

    @Test
    public void testInvalidVariableDeclarator() {
        assertThat(parser)
                .notMatches("");
    }
}
