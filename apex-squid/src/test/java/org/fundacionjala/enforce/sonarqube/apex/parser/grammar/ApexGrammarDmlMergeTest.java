/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DML_MERGE;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarDmlMergeTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(DML_MERGE);
    }

    @Test
    public void rulesDmlMerge() {
        assertThat(parser)
                .matches("merge primaryExpression optionalPrimaryExpression")
                .matches("merge newAcct super.oldAcct");
    }

    @Test
    public void rulesDmlMergeCaseError() {
        assertThat(parser)
                .notMatches("mergeoptionalPrimaryExpression");
    }
}
