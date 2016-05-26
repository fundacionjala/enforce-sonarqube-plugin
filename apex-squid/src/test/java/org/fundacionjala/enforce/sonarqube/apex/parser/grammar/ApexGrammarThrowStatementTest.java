/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.THROW_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarThrowStatementTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(THROW_STATEMENT);
    }

    @Test
    public void testValidThrowStatement() {
        assertThat(parser)
                .matches("throw something;");
    }

    @Test
    public void testInvalidThrowStatement() {
        assertThat(parser)
                .notMatches("somethingelse")
                .notMatches("throw")
                .notMatches(";")
                .notMatches("somethingelse;");
    }
}
