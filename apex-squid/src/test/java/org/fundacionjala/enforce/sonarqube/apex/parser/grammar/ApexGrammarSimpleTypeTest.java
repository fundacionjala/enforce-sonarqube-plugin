/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SIMPLE_TYPE;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarSimpleTypeTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(SIMPLE_TYPE);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("integer")
                .matches("string")
                .matches("SomeClassType")
                .matches("someothertype")
                .matches("exception");
    }
    
    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("*integer")
                .notMatches("string/")
                .notMatches("-exception")
                .notMatches("2");
    }
}
