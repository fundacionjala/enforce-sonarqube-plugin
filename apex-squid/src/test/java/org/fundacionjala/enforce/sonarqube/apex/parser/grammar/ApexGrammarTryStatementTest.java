/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TRY_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarTryStatementTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(TRY_STATEMENT);
    }

    @Test
    public void testValidTryStatement() {
        assertThat(parser)
                .matches("try{} catch(Object exceptionObject){}")
                .matches("try{} catch(Object exceptionObject){} catch(Object anotherException){}")
                .matches("try{} catch(Object exceptionObject){} finally {}")
                .matches("try{} catch(System.exception e){}")
                .matches("try{delete new List<Account>{account}} catch(System.Exception e) {result = true}")
                .matches("try{delete new List<Account>{account}} catch(Exception e) {result = true}");
    }

    @Test
    public void testInvalidTryStatement() {
        assertThat(parser)
                .notMatches("try{}{}")
                .notMatches("try{} catch{}")
                .notMatches("try catch")
                .notMatches("try {} catch(){finally{}}")
                .notMatches("try{delete new List<Account>{account}} catch(Exception.exception e) {result = true}");
    }
}
