/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONTINUE_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarContinueStatementTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(CONTINUE_STATEMENT);
    }

    @Test
    public void testValidContinueStatement() {
        assertThat(parser)
                .matches("continue;");
    }

    @Test
    public void testInvalidContinueStatement() {
        assertThat(parser)
                .notMatches("somethingelse")
                .notMatches("continue")
                .notMatches(";")
                .notMatches("somethingelse;");
    }
}
