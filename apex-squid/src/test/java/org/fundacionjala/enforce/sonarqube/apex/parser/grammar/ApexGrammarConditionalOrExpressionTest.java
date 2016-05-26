/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONDITIONAL_OR_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarConditionalOrExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(CONDITIONAL_OR_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("3")
                .matches("a")
                .matches("someExpression")
                .matches("thisThing || thatThing")
                .matches("5 || 6 || 7")
                .matches("a.b || x.y")
                //with nested "AND" expression
                .matches("a && b")
                .matches("a && b || c")
                .matches("thisThing && thatThing || aThing && anotherThing")
                .matches("a.b || x.y")
                .matches("a.b || x.y")
                .matches("x != null || x > 0 || x instanceof something")
                .matches("true || 0 < x <= y || (x%2 == 0) && x != null");
    }
}
