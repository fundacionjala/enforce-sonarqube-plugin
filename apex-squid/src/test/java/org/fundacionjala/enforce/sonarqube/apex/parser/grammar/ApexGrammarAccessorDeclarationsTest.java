/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ACCESSOR_DECLARATIONS;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarAccessorDeclarationsTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(ACCESSOR_DECLARATIONS);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("private get; \n"
                        + "public set;")
                .matches("protected final get; \n"
                        + "public static set;")
                .matches("public set;")
                .matches("public GET;")
                .matches("priVATe set; \n"
                        + "pubLIC gET;");
    }
    
    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("public GET")
                .notMatches("private set;;")
                .notMatches("integer set;")
                .notMatches("public other;")
                .notMatches("public get,set;");
    }

}
