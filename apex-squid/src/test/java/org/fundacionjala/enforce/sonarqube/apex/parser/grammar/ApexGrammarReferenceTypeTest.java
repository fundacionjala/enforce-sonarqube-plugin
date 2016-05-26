/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.REFERENCE_TYPE;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarReferenceTypeTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(REFERENCE_TYPE);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("integer")
                .matches("string")
                .matches("SomeClassType")
                .matches("ClassType[]")
                .matches("primitivetype[]")
                .matches("someothertype");
    }
    
    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("*integer")
                .notMatches("string/")
                .notMatches("ClassType]")
                .notMatches("primitivetype[")
                .notMatches("primitivetype[asd]")
                .notMatches("2");
    }
}
