/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DML_UPSERT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarDmlUpsertTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(DML_UPSERT);
    }

    @Test
    public void rulesDmlUpsert() {
        assertThat(parser)
                .matches("upsert primaryExpression")
                .matches("upsert primaryExpression optionalPrimaryExpression");
    }

    @Test
    public void rulesDmlUpsertCaseError() {
        assertThat(parser)
                .notMatches("upsertprimaryExpression")
                .notMatches("upsertoptionalPrimaryExpression");
    }
}
