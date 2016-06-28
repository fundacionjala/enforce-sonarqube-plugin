/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("1")
                .matches("a")
                .matches("someIdentifier")
                .matches("null")
                .matches("1009")
                //with assignmentOperators
                .matches("x = 5")
                .matches("x = y = 2")
                .matches("counter += 4")
                //with conditional expression
                .matches("thisValue ? 0 : 1")
                .matches("y += question ? 1 : 10")
                //with AND and OR expressions
                .matches("a || b")
                .matches("aBooleanValue = firstCondition && secondCondition || thirdCondition")
                .matches("a = b || c ? someValue : someOtherValue")
                //with several types of Expressions
                .matches("aBoolean = a == b | c != d && x.something || z == null")
                .matches("var += a instanceof b ^ c && d instanceof SomeClass || f != null == 0")
                .matches("var = x != null ? a&b&c||y : z|y||x")
                .matches("x = new SomeType()")
                .matches("x = new SomeType(p1, null, p2, 0)")
                .matches("accts = [SELECT Name, Phone FROM Account]")
                .matches("myquery = 'SELECT Name, Phone FROM Account'")
                .matches("var = Database.query('SELECT dato FROM table1 LIMIT 5000')")
//                .matches("campaign = [select id, name from campaign where id = :campaign.id]")
                .matches("campaign = [select id, name from campaign]");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("private")
                .notMatches("void")
                .notMatches("class = this")
                .notMatches("*")
                .notMatches("y == question ?? 1 : 10")
                .notMatches("a &&& b == c")
                .notMatches("a =! b &| c");
    }
}
