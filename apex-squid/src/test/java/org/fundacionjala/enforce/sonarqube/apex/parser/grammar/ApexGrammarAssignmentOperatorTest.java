/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;


import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ASSIGNMENT_OPERATOR;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarAssignmentOperatorTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(ASSIGNMENT_OPERATOR);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("=")
                .matches("/=")
                .matches("-=")
                .matches("+=")
                .matches("%=")
                .matches("*=")
                .matches("<<=")
                .matches(">>>=")
                .matches("&=")
                .matches("^=")
                .matches("|=");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("==")
                .notMatches("===")
                .notMatches("/ =");
    }
}
