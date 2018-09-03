/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SWITCH_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarSwitchStatementTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(SWITCH_STATEMENT);
    }

    @Test
    public void testValidSwitchStatement() {
        assertThat(parser)
                .matches("switch on test {"
                        + " When 2  { System.debug('when block 2');} "
                        + " When -3 { System.debug('when block -3');} "
                        + " When else {System.debug('when block else');} "
                        + " }")
                .matches("switch on someInteger() {"
                        + " When 2, 3, 4  { System.debug('when block is for 2, 3 and 4');} "
                        + " When 5, 6 { System.debug('when block is for 5 and 6');} "
                        + " When null {System.debug('when block null');} "
                        + " When else {System.debug('when block else');} "
                        + " }")
                .matches("switch on UserInfo.getUserId() {"
                        + " When TEST, 'test', Test  { System.debug('when block is for 2, 3 and 4');} "
                        + " When 5, 6 { System.debug('when block is for 5 and 6');} "
                        + " When else {System.debug('when block else');} "
                        + " }")
                .matches("switch on UserInfo.getUserId(i) {"
                        + " When TEST, 'test', Test  { System.debug('when block is for 2, 3 and 4');} "
                        + " When 5, 6 { System.debug('when block is for 5 and 6');} "
                        + " When else {System.debug('when block else');} "
                        + " }")
                .matches("switch on UserInfo.getUserId(i) {"
                        + " When TEST, 'test', Test  { System.debug('when block is for 2, 3 and 4');} "
                        + " When 5, 6 { System.debug('when block is for 5 and 6');} "
                        + " When else {System.debug('when block else');} "
                        + " }")
                .matches("switch on sobject {"
                		+ " when Account a { System.debug('account ' + a); } "
                		+ " when Account B { System.debug('account ' + a); } "
                		+ " when Account C { System.debug('account ' + a); } "
                		+ " When null {System.debug('when block null'); } "
                		+ " }");
   
    }
}

