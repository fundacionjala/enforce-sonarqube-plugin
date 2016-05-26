/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.WHILE_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarWhileStatemetTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(WHILE_STATEMENT);
    }

    @Test
    public void testValidWhileStatementPI() {
        assertThat(parser)
                .matches("while(true)"
                        + "{}")
                .matches("while(something)"
                        + "while(anotherthing)"
                        + "{}")
                .matches("WHILE(TRUE){"
                        + ""
                        + "}");
    }
}
