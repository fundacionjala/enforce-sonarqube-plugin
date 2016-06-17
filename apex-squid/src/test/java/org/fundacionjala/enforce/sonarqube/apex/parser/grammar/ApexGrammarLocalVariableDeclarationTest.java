/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LOCAL_VARIABLE_DECLARATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarLocalVariableDeclarationTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(LOCAL_VARIABLE_DECLARATION);
    }

    @Test
    public void testValidLocalVariableDeclaration() {
        assertThat(parser)
                .matches("final integer variable")
                .matches("integer variable,variable1")
                .matches("integer variable")
                .matches("final integer variable = this.that")
                .matches("Boolean CONSTANT = true");
    }

    @Test
    public void testInValidLocalVariableDeclaration() {
        assertThat(parser)
                .notMatches("")
                .notMatches("integer final variable")
                .notMatches("integer final VAriable;");
    }
}
