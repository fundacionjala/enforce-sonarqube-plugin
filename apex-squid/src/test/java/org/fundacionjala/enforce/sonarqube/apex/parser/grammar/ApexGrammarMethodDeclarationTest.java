/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_DECLARATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarMethodDeclarationTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(METHOD_DECLARATION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("integer isMethod(){}")
                .matches("void isMethod(){}")
                .matches("bool isMethod(integer x, final bool y){}")
                .matches("string isMethod(bool y){}")
                .matches("floatn isMethod(integer a, final integer b, integer c){}")
                .matches("void isMethod();")
                .matches("bool isMethod(integer x, final bool y);")
                .matches("bool IsMethod(){integer myVariable = 3;}");
    }
}
