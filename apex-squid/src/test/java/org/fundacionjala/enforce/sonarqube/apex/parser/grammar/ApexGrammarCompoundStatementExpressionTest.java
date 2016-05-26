/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.COMPOUND_STATEMENT_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarCompoundStatementExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(COMPOUND_STATEMENT_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("a.b()++;")
                .matches("x[1].size() = 2;")
                .matches("new SomeType(someParam).create()--;")
                .matches("x.y(){integer z = 3-2;}")
                .matches("new SomeType(someParam);")
                .matches("new SomeType(someParam){ integer var = otherVariable.that; }")
                .matches("System.assert(true);");
    }
}
