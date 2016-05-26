/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARGUMENTS_LIST;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammrArgumentsListTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(ARGUMENTS_LIST);
    }

    @Test
    public void testValidArgumentsList() {
        assertThat(parser)
                .matches("this,this")
                .matches("3")
                .matches("abc,3")
                .matches("this,this,this,this,this")
                .matches("3,3,3,3,3");
    }

    @Test
    public void testInValidArgumentsList() {
        assertThat(parser)
                .notMatches("3,,3,,3,,3")
                .notMatches("this,,ss,ssd,est");
    }
}
