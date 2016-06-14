/*
 * The MIT License
 *
 * Copyright 2016 Fundacion Jala.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.junit.Test;
import org.junit.Before;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SOQL_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarSOQLExpressionTest extends ApexRuleTest {
/**
    @Before
    public void init() {
        setRootRule(SOQL_EXPRESSION);
    }

    @Test
    public void positiveRules_SOQLExpresion() {
        assertThat(parser)
                .matches("Database.query(SELECT dato FROM table1 LIMIT 5000)")
                .matches("[SELECT dato FROM table1 LIMIT 5000]");
    }
    
    @Test
    public void negativeRules_SOQLExpresion() {
        assertThat(parser)
                .notMatches("SELECTdato")
                .notMatches("SELECTfield1,field2")
                .notMatches("SELECT dato FROM table1 LIMIT ")
                .notMatches("SELECT dato FROM table1");
    }*/
}
