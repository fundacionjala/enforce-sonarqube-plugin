/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ACCESSOR;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarAccessorTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(ACCESSOR);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("get")
                .matches("set")
                .matches("GET")
                .matches("SET")
                .matches("gEt")
                .matches("Set");
    }
    
    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("other")
                .notMatches("something")
                .notMatches("Teg")
                .notMatches("23");
        
    }

}
