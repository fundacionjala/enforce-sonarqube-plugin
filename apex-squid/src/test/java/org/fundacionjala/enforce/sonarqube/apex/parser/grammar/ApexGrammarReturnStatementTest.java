/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.RETURN_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarReturnStatementTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(RETURN_STATEMENT);
    }

    @Test
    public void testValidReturnStatement() {
        assertThat(parser)
                .matches("return;")
                .matches("return something;")
                .matches("return this;");
    }

    @Test
    public void testInvalidReturnStatement() {
        assertThat(parser)
                .notMatches("somethingelse")
                .notMatches("return")
                .notMatches(";")
                .notMatches("somethingelse;");
    }
}
