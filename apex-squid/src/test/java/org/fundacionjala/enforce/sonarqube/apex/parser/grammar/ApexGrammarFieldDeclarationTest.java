/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FIELD_DECLARATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarFieldDeclarationTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(FIELD_DECLARATION);
    }

    @Test
    public void testValidFieldDeclaration() {
        assertThat(parser)
                .matches("integer myVariable;")
                .matches("integer addition,takeaway;")
                .matches("Boolean isActive = true;")
                .matches("integer addition = 0;")
                .matches("ClassType transient;")
                .matches("Iterator iterator = iteratorParameter;");
    }

    @Test
    public void testInvalidFieldDeclaration() {
        assertThat(parser)
                .notMatches("char varCharMissingSemicolon")
                .notMatches("ClassType missingValue =;");
    }
}
