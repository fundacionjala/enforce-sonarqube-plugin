/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.OCTAL_LITERAL;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarOctalLiteralTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(OCTAL_LITERAL);
    }

    @Test
    public void rulesOctalLiteral() {
        assertThat(parser)
                .matches("0123")
                .matches("001")
                .matches("0");
    }

    @Test
    public void rulesOctalLiteralCaseError() {
        assertThat(parser)
                .notMatches("08");
    }
}
