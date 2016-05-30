/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXTENDS_LIST;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarExtendsListTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(EXTENDS_LIST);
    }

    @Test
    public void negativeRulesMergeType() {
        assertThat(parser)
                .notMatches("extendMyClass")
                .notMatches("_extends_MyClass1");
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("Extends _MyClass1")
                .matches("extends MyClass")
                .matches("extends MyClass.AType")
                .matches("extends exception")
                .matches("extends MyClass1");
    }
}
