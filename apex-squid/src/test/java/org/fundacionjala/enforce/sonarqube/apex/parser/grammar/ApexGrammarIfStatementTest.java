/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.IF_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarIfStatementTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(IF_STATEMENT);
    }

    @Test
    public void testValidIfStatement() {
        assertThat(parser)
                .matches("if(anExpression)"
                        + "{}")
                .matches("if(otherExpression)"
                        + "{}"
                        + "else "
                        + "if(anotherExpression)"
                        + "{}")
                .matches("if(expression)"
                        + "{}"
                        + "else "
                        + "{}")
                .matches("if(x==0)"
                        + "{}"
                        + "else "
                        + "{}")
                .matches("if(a instanceof b)\n"
                        + " a.doSomething(0);\n"
                        + "else \n"
                        + "b.doSomething(1);")
                .matches("IF(a INSTANCEOF b)"
                        + "a.doSOMETHING(0);");
    }
}
