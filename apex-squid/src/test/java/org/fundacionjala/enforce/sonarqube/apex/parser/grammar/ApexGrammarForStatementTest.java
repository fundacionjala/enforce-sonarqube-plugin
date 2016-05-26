/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarForStatementTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(FOR_STATEMENT);
    }

    @Test
    public void rulesForStament() {
        assertThat(parser)
                .matches("for(Object myObject : listIntegers){ }")
                .matches("for(integer myObject:listIntegers){}")
                .matches("for(doub myObject:listDoubles){}")
                .matches("for(bool myObject:listBooleans){}")
                .matches("for(integer myVariable; doSomething; updateVariable){}")
                .matches("FOR(integer MYVARIABLE; dOsOMETHING; updaTEVARIABLE){}")
                .matches("for(integer addition = 3; doSomething; updateAddition){}")
                .matches("        for(Accts newAcct : listAccts){\n"
                        + "           merge newAcct this.anotherAccount; \n"
                        + "        }\n");
    }

    @Test
    public void rulesForStamentCaseError() {
        assertThat(parser)
                .notMatches("for(ObjectMyObject:listIntegers){ }")
                .notMatches("for(intMyObject:listIntegers){}")
                .notMatches("for(doubleMyObject:listDoubles)intnumber;")
                .notMatches("for(booleanMyObject:listBooleans){integer number=i;}");
    }
}
