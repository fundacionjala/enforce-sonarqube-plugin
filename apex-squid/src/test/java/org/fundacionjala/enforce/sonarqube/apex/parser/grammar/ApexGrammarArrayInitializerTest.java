/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARRAY_INITIALIZER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarArrayInitializerTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(ARRAY_INITIALIZER);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches(" ")
                .matches("3")
                .matches("a")
                .matches("x++")
                .matches("something")
                .matches("x.y.z(p1, p2, 3).w[1-3].v")
                .matches("this")
                .matches("1, 2, 3, x, y, z")
                .matches("0, (x-3 + (4*y)), this")
                .matches("A,B,C,e,d");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("()")
                .notMatches("a; b")
                .notMatches("*");
    }
}
