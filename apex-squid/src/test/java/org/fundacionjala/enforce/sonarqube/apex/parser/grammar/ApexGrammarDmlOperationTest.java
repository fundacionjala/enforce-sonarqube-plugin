/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DML_OPERATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarDmlOperationTest extends ApexRuleTest{
@Before
    public void init() {
        setRootRule(DML_OPERATION);
    }

    @Test
    public void RulesDmlOperation() {
        assertThat(parser)
                .matches("insert primaryExpression")
                .matches("update prmaryExpression")
                .matches("undelete primaryExpression")
                .matches("delete primaryExpression");
    }

    @Test
    public void RulesDmlOperationCaseError() {
        assertThat(parser)
                .notMatches("insertnewAcct;")
                .notMatches("updatemyAcct;")
                .notMatches("upsertacctList;")
                .notMatches("deletedoomedAccts;")
                .notMatches("undeletesavedAccts;");
    }    
    
}
