/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MAP_VALUES;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarMapValuesTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(MAP_VALUES);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("someKey => someValue")
                .matches("someKey => someValue,"
                        + "otherKey => otherValue,"
                        + "anotherKey => anotherValue")
                .matches("(someKey = true ? a : b ) => 3-(5*y)/x++");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("key = value")
                .notMatches("key ==> value")
                .notMatches("key => a, b");
    }
}
