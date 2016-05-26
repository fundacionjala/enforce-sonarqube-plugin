/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOCATION_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarAllocationExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(ALLOCATION_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("new SomeType()")
                .matches("new AnotherClass.SomeType()")
                .matches("new SomeType(p1, p2, 0, null)")
                .matches("new SomeType(p1, p2) {}")
                .matches("new SomeType[5]")
                .matches("new SomeType[5-2*4/y]")
                .matches("new SomeType[a.b().c[1]]")
                .matches("new SomeType[]{1, 2, x, y, this}")
                .matches("new list<SomeType>()")
                .matches("new iterator<set<someType>>()")
                .matches("new set<SomeType>(p1, 3, null, this)")
                .matches("new set<SomeType>{}")
                .matches("new map<K, V>()")
                .matches("new map<K, V>{}")
                .matches("new map<K, V>(1, 2 , x, y)")
                .matches("new map<K, V>{k1 => 2 , x => y}")
                .matches("new SOMEtype()");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("new")
                .notMatches("new 3")
                .notMatches("new 'aString'")
                .notMatches("new null")
                .notMatches("new SomeType");
    }
}
