/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE_CLASS;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarTypeClassTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(TYPE_CLASS);
    }

    @Test
    public void checkingPositiveRules() {
        assertThat(parser)
                .matches("class")
                .matches("interface")
                .matches("Class")
                .matches("Interface");
    }
    
    @Test
    public void checkingNegativeRules() {
        assertThat(parser)
                .notMatches("clas")
                .notMatches("inteface")
                .notMatches("cass")
                .notMatches("interfase");
    }
}
