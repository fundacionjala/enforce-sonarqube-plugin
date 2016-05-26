/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarTypeTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(TYPE);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("integer")
                .matches("string")
                .matches("SomeClassType")
                .matches("someothertype")
                .matches("list<something>")
                .matches("set<SomeClass>")
                .matches("map<SomeKeyClass, SomeValueclass>");
    }

    @Test
    public void positiveNestedRules() {
        assertThat(parser)
                .matches("list<set<list<thisThing>>>")
                .matches("set<SomeClass[]>")
                .matches("map<list<aThing>, Value>");
    }
}
