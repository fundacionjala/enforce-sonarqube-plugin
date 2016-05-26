/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ENUM_DECLARATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarEnumDeclarationTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(ENUM_DECLARATION);
    }

    @Test
    public void testValidEnumDeclaration() {
        assertThat(parser)
                .matches("enum allowedKeyword {transient}")
                .matches("enum specialKeyword {stat}")
                .matches("enum transient {first}")
                .matches("enum sharing {last}");
    }

    @Test
    public void testInvalidEnumDeclaration() {
        assertThat(parser)
                .notMatches("enum {}")
                .notMatches("")
                .notMatches("ENUM transient {}")
                .notMatches("enum sharing {{");
    }
}
