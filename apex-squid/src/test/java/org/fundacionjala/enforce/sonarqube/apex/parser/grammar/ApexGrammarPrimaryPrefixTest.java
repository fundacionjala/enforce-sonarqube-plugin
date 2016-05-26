/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.PRIMARY_PREFIX;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarPrimaryPrefixTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(PRIMARY_PREFIX);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("1")
                .matches("true")
                .matches("false")
//                TODO: uncomment this when the old rules are deleted and it should work
//                .matches("this")
                .matches("'something'")
                .matches("someIdentifier")
                .matches("a.b.c")
                .matches("this.something")
                .matches("super.something")
                .matches("something.something")
                .matches("(anExpression)")
                .matches("(a.b.c)")
                .matches("(++x)")
                .matches("(4-3*x-y+--+-z-2)")
                .matches("(null)")
                .matches("(x = z = 3-5)")
                .matches("(x ? y : z)");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("class")
                .notMatches("5-3")
                .notMatches(".b");
    }
}
