/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarAllowedKeywordsAsIdentifierTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(ALLOWED_KEYWORDS_AS_IDENTIFIER);
    }

    @Test
    public void testValidAllowedKeywordsAsIdentifier() {
        assertThat(parser)
                .matches("anyEntry")
                .matches("transient")
                .matches("returning")
                .matches("search")
                .matches("stat")
                .matches("convertcurrency")
                .matches("savepoint")
                .matches("tolabel")
                .matches("sharing")
                .matches("get")
                .matches("after")
                .matches("before")
                .matches("first")
                .matches("last")
                .matches("category")
                .matches("network")
                .matches("iterator")
                .matches("RETURNING")
                .matches("AFter")
                .matches("TOlabel");
    }
    
    @Test
    public void testInvalidKeywordsAsIdentifier() {
        assertThat(parser)
                .notMatches("")
                .notMatches("   ");
    }

}
