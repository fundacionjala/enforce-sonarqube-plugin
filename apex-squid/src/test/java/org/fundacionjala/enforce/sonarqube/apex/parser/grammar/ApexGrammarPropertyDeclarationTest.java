/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.PROPERTY_DECLARATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarPropertyDeclarationTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(PROPERTY_DECLARATION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("integer prop {private get; set;}")
                .matches("integer prop {public get{return variable;}}")
                .matches("integer prop {set {integer prop = 5;}}")
                .matches("Boolean prop {set;}");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                //Without type
                .notMatches("prop {get; set;}")
                //without identifier
                .notMatches("integer {get; set;}")
                //without semicolon
                .notMatches("integer prop {get set}")
                //without braces
                .notMatches("integer prop [get; set;]");
    }
}
