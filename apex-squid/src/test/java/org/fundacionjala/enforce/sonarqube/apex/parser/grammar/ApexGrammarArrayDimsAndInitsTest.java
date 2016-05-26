/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARRAY_DIMS_AND_INITS;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarArrayDimsAndInitsTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(ARRAY_DIMS_AND_INITS);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("[3]")
                .matches("[var]")
                .matches("[something.x]")
                .matches("[anArray.size()]")
                .matches("[a(p1, p2).b[3]]")
                .matches("[x = b <= c ? 5-3 : r*s/t-2]")
                .matches("[]{}")
                .matches("[]{1,2,3,X,Y,Z,'x','y',this}")
                .matches("[]{a.b(p1).c(), x.y[0].z[4-3*x]}")
                .matches("[]{this}")
                .matches("[]{THIS}");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("[2")
                .notMatches("(3)")
                .notMatches("[]");
    }
}
