/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.PRIMARY_SUFFIX;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarPrimarySuffixTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(PRIMARY_SUFFIX);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("[someExpression]")
                .matches("[++x-!-y]")
                .matches("[4-5*8/2]")
                .matches(".aMethod")
                .matches(".size")
                .matches("()")
                .matches("(parameter)")
                .matches("(p1, p2, this)")
                .matches("(1, null)");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("[something")
                .notMatches("(something")
                .notMatches(".3");
    }
}
