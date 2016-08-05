/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.RESULT_TYPE;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarResultTypeTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(RESULT_TYPE);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("void")
//                this cases should work when the old rules are deleted
//                .matches("boolean")
//                .matches("double")
                .matches("string")
                .matches("integer");
    }
}
