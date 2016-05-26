/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DML_OPERATIONS;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarDmlOperationsTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(DML_OPERATIONS);
    }

    @Test
    public void rulesDmlOperations() {
        assertThat(parser)
                .matches("insert primaryExpression")
                .matches("delete primaryExpression")
                .matches("undelete primaryExpression")
                .matches("update primaryExpression")
                .matches("upsert primaryExpression")
                .matches("upsert primaryExpression primaryExpression")
                .matches("merge primaryExpression primaryExpression");
    }

    @Test
    public void rulesDmlOperationsCaseError() {
        assertThat(parser)
                .notMatches("insertprimaryExpression")
                .notMatches("deleteprimaryExpression")
                .notMatches("upsertprimaryExpression")
                .notMatches("merge primaryExpression")
                .notMatches("mergeprimaryExpression");
    }
}
