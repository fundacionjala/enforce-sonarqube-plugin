/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FORMAL_PARAMETERS;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarFormalParametersTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(FORMAL_PARAMETERS);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("(integer x)")
                .matches("(final integer x)")
                .matches("()")
                .matches("(integer x, string y)")
                .matches("(bool var, final integer x, final doble y)");
    }
}
