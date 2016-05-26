/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EQUALITY_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarEqualityExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(EQUALITY_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("3")
                .matches("a")
                .matches("someExpression")
                .matches("thisThing == thatThing")
                .matches("thisThing != thatThing")
                .matches("5 == 6 != 7 == a")
                .matches("a.b == x.y");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches(" a = b")
                .notMatches(" a ! b")
                .notMatches(" a !! b");
    }
}
