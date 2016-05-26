/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INITIALIZER_BLOCK_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarInitializerBlockStatementTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(INITIALIZER_BLOCK_STATEMENT);
    }

    @Test
    public void testValidInitilizerBlockStatement() {
        assertThat(parser)
                .matches(";")
                .matches("anyExpression;")
                .matches("if(true){} else {}")
                .matches("while(true){}")
                .matches("do {} while(true);")
                .matches("for(integer index = 0; index<10; index++){}")
                .matches("for(integer currentNumber : numbers){}")
                .matches("break;")
                .matches("continue;")
                .matches("return this;")
                .matches("return someVariable;")
                .matches("throw anyException;")
                .matches("try{} catch(exception anyException){} finally {}");
    }
}
