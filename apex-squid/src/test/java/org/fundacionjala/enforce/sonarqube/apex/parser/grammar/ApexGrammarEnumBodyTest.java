/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ENUM_BODY;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarEnumBodyTest extends ApexRuleTest{
    
    @Before
    public void setUp() {
        setRootRule(ENUM_BODY);
    }
    
    @Test
    public void testValidEnumBody() {
        assertThat(parser)
                .matches("anotherText,someString")
                .matches("onlyOneKeyword")
                .matches("transient")
                .matches("group")
                .matches("stat,without");
    }
    
    @Test
    public void testInvalidEnumBody() {
        assertThat(parser)
                .notMatches("'',''")
                .notMatches("");
    }
         
}
