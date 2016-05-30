/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DECIMAL_LITERAL;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarDecimalLiteralTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(DECIMAL_LITERAL);
    }

    @Test
    public void rulesDecimalLiteral() {
        assertThat(parser)
                .matches("156")
                .matches("4")
                .matches("10");
    }

    @Test
    public void rulesDecimalLiteralCaseError() {
        assertThat(parser)
                .notMatches("012345678");
    }

}
