/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONDITIONAL_AND_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarConditionalAndExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(CONDITIONAL_AND_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("3")
                .matches("a")
                .matches("someExpression")
                .matches("thisThing && thatThing")
                .matches("5 && 6 && 7")
                .matches("a.b && x.y")
                //with nested INCLUSIVE_OR expression
                .matches("a | b")
                .matches("a | b | c && d && e | f");
    }
    
    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches(" a b &&")
                .notMatches(" a !& b")
                .notMatches(" a &| b")
                .notMatches(" a || b");
    }
}
