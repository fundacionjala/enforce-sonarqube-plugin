/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ACCESSOR_BODY;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarAccessorBodyTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(ACCESSOR_BODY);
    }

    @Test
    public void validAccessorBody() {
        assertThat(parser)
                .matches("{}")
                .matches("{final integer transient;}")
                .matches("{integer validVariable;}")
                .matches("{iNTEger mixedCASSEVAriable;}");
    }

    @Test
    public void testInvalidAccessorBody() {
        assertThat(parser)
                .notMatches("hi")
                .notMatches("{}{}")
                .notMatches("");
    }
}
