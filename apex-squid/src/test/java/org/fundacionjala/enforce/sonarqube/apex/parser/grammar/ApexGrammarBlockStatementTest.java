/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarBlockStatementTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(BLOCK_STATEMENT);
    }

    @Test
    public void testValidBlockStatement() {
        assertThat(parser)
                .matches("integer variable;")
                .matches("return null;")
                .matches("final integer transient = this;")
                .matches("doOtherThing(x);")
                .matches("REturn null;");
    }
    
    @Test
    public void testInValidBlockStatement() {
        assertThat(parser)
                .notMatches("");
    }

}
