/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INITIALIZER_BLOCK_MEMBER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarInitializerBlockMemberTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(INITIALIZER_BLOCK_MEMBER);
    }

    @Test
    public void testValidInitializerBlockMember() {
        assertThat(parser)
                .matches("{{integer something;}}")
                .matches("integer variable;")
                .matches("Boolean booleanVariable;")
                .matches("if(true) {}")
                .matches("while(true) {}")
                .matches(";");
    }

    @Test
    public void testInValidInitializerBlockMember() {
        assertThat(parser)
                .notMatches("")
                .notMatches("{}{int variable;}")
                .notMatches("int variableWithNoSemicolon");
    }

}
