/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarStatementTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(STATEMENT);
    }

    @Test
    public void testValidStatementPi() {
        assertThat(parser)
                .matches("{}")
                .matches(";")
                .matches("if('anExpression')"
                        + "{}")
                .matches("if('anExpression')"
                        + "{}"
                        + "else "
                        + "if('anotherExpression')"
                        + "{}")
                .matches("while(trueExpression){}")
                .matches("do {} while (trueExpression);")
                .matches("break;")
                .matches("continue;")
                .matches("return something;")
                .matches("return this;")
                .matches("throw someException;")
                .matches("for(integer addition = 0; doSomething; updateIterator){}")
                .matches("for(Object current:listOfObjects){}")
                .matches("try{} catch(Object variable){} finally{}")
                .matches("insert primaryExpression;")
                .matches("delete primaryExpression;")
                .matches("upsert primaryExpression;")
                .matches("upsert primaryExpression primaryExpression;")
                .matches("merge primaryExpression primaryExpression;");

    }
}
