/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ANNOTATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarAnnotationTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(ANNOTATION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("@something")
                .matches("@deprecated")
                .matches("@isTest")
                .matches("@CAPS")
                .matches("@SOMEAnotation")
                .matches("@annotationWithEmptyParam()")
                .matches("@annotationWithParam(p1 = a.something)")
                .matches("@SOMEAnotation (p1 = 3"
                        + " p2 = 'someString')")
                .matches("@isTest(seeAllData = true)");
    }
    
    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("@public")
                .notMatches("@override")
                .notMatches("@is Test")
                .notMatches("@1");
    }
}
