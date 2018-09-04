/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.GENERIC_TYPE;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarGenericTypeTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(GENERIC_TYPE);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("<SomeType>")
                .matches("<SomeType, SomeOtherType>")
                .matches("<list<someType>>")
                .matches("<list<set<someType>>>");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("<someType")
                .notMatches("<sometype,>")
                .notMatches("<>");
    }
}
