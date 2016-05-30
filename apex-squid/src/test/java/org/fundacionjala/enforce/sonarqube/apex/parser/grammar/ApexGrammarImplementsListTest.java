/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.IMPLEMENTS_LIST;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarImplementsListTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(IMPLEMENTS_LIST);
    }

    @Test
    public void negativeRulesMegeType() {
        assertThat(parser)
                .notMatches("_implementsMyClass")
                .notMatches(" Implements_MyClass1")
                .notMatches("=implements_MyClass1");
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("implements MyClass")
                .matches("implements MyClass, MyClass2, Myclass3")
                .matches("implements MyClass<AType>, MyClass2<Atype, AnotherType>, ThirdClass<A, B, C>");
    }
}
