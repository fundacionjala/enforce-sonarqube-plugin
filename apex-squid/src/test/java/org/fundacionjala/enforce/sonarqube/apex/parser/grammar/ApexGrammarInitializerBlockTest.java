/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INITIALIZER_BLOCK;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarInitializerBlockTest extends ApexRuleTest{
    
    @Before
    public void setUp() {
        setRootRule(INITIALIZER_BLOCK);
    }
    
    @Test
    public void testValidInitializerBlock() {
        assertThat(parser).matches("{ { integer x;"
                + "integer y; } }");
    }
    
    
    @Test
    public void testInValidInitializerBlock() {
        assertThat(parser).notMatches("{}{}{}{}{{int x;}}")
                .notMatches("");
    }
    
}
