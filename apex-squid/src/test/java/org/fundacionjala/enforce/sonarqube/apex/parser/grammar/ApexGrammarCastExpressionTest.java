/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CAST_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarCastExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(CAST_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("(someType) someVariable")
//                TODO: uncomment this when the old rules are deleted and it should work
//                .matches("(someType) this")
                .matches("(someType) --+-++5")
                .matches("(someType) --!+x")
                .matches("(string[]) x")
                .matches("(list<someType>) x")
                .matches("(map<integer, string>) x");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("a")
                .notMatches("() x")
                .notMatches("(2) b")
                .notMatches("b (someType)");
    }
}
