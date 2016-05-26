/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.GET_SHARING_RULES;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarGetSharingRulesTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(GET_SHARING_RULES);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("with sharing")
                .matches("without sharing");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("with")
                .notMatches("without")
                .notMatches("with with")
                .notMatches("sharing")
                .notMatches("without with");
    }
}
