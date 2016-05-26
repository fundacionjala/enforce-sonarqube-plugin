/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.PRIMARY_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarPrimaryExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(PRIMARY_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("anExpression")
                .matches("null")
                .matches("pExpression[sExpression]")
                .matches("pExpression.sExpression")
                .matches("pExpression(sExpression)")
                .matches("x.y[++x-!-y]")
//                TODO: uncomment this when the old rules are deleted and it should work
//                .matches("x.doSomething(this)")
                .matches("x.doThis(p1, null, p2).doToo().doThree(thisValue, 3)")
                .matches("a.someArray[0].doThis(this, that)");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("a..b")
                .notMatches("a(b.)")
                .notMatches("x.3");
    }
}
