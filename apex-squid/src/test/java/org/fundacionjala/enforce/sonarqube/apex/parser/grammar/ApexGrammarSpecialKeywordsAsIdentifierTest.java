/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SPECIAL_KEYWORDS_AS_IDENTIFIER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarSpecialKeywordsAsIdentifierTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(SPECIAL_KEYWORDS_AS_IDENTIFIER);
    }

    @Test
    public void testValidSpecialKeywordsAsIdentifier() {
        assertThat(parser)
                .matches("without")
                .matches("offset")
                .matches("data")
                .matches("group")
                .matches("limit")
                //mix of uppercasse and lowercasse
                .matches("withOUT");
    }

    @Test
    public void testInvalidSpecialKeywordsAsIdentifier() {
        assertThat(parser)
                .notMatches("otherKeywordsThanThespecifiedOnes")
                .notMatches("spaces between keywords")
                //empty keyword
                .notMatches("");
    }
}
