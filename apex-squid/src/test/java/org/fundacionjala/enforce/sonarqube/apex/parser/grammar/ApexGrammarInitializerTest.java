/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INITIALIZER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarInitializerTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(INITIALIZER);
    }

    @Test
    public void testValidInitializer() {
        assertThat(parser)
                .matches("static { integer x;}")
                .matches("{{{integer x;}}}")
                .matches("static {{}}");
    }

    @Test
    public void testInvalidInitializer() {
        assertThat(parser)
                .notMatches("someString {}")
                .notMatches("static {}{}{}");

    }
}
