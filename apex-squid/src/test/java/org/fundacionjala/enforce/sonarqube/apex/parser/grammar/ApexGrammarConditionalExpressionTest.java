/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONDITIONAL_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarConditionalExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(CONDITIONAL_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("boolStatement ? doSomething : x = 3")
                .matches("boolStatement ? null : 0");
    }
}
