/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INSTANCE_OF_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarInstanceOfExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(INSTANCE_OF_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("3")
                .matches("a")
                .matches("someExpression")
                .matches("thisThing instanceof someType")
                .matches("thisThing instanceof list<someType>")
                .matches("thisThing instanceof map<someKey, someType>")
                //with nested Relational Expressions
                .matches("a < b")
                .matches("a >= b instanceof SomeClass")
                .matches("a < b <= c instanceof SomeClass")
                .matches(" a instanceOf b")
                .matches(" a InstanceOf b");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches(" a instance of b")
                .notMatches(" a instanceof 3");
    }
}
