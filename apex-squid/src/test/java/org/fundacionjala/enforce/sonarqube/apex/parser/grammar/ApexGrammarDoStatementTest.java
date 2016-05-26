/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DO_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarDoStatementTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(DO_STATEMENT);
    }

    @Test
    public void testValidDoStatement() {
        assertThat(parser)
                .matches("do {} while (true);")
                .matches("do do{} while (true); while (trueExpression);")
                .matches("DO{}while(TRUE);");
    }

    @Test
    public void testInvalidDoStatement() {
        assertThat(parser)
                .notMatches("do do while (trueExpression)")
                .notMatches("dododo while(trueExpression);");
    }

}
