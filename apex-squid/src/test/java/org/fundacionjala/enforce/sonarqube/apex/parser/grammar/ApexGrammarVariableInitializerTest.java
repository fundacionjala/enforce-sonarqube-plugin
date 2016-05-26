/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_INITIALIZER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarVariableInitializerTest extends ApexRuleTest{
    
    @Before
    public void setUp() {
        setRootRule(VARIABLE_INITIALIZER);
    }
    
    @Test
    public void testValidVariableInitializer() {
        assertThat(parser)
                .matches("this")
                .matches("myVariable.that")
                .matches("this.that");
    }
    
    @Test
    public void testInvalidVariableInitializer() {
        assertThat(parser)
                .notMatches("")
                .notMatches("varible,variable");
    }
    
}
