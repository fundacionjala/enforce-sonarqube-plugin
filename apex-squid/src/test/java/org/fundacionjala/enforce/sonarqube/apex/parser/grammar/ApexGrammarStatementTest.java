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

import org.junit.Before;
import org.junit.Test;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;

import static org.sonar.sslr.tests.Assertions.assertThat;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT;

public class ApexGrammarStatementTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(STATEMENT);
    }

    @Test
    public void rulesDmlStament() {
        assertThat(parser)
                .matches("insert newAcct;")
                .matches("update myAcct;")
                .matches("upsert acctList;")
                .matches("delete doomedAccts;")
                .matches("undelete savedAccts;");
    }

    @Test
    public void rulesForStament() {
        assertThat(parser)
                .matches("for(Object MyObject : listIntegers){ }")
                .matches("for(int MyObject:listIntegers){}")
                .matches("for(double MyObject:listDoubles)intnumber;")
                .matches("for(boolean MyObject:listBooleans){int number=i;}");
    }

    @Test
    public void rulesIfElseStatement() {
        assertThat(parser)
                .matches("if(NAME){}")
                .matches("if(NAME){}else{}")
                .matches("if(NAME)12;")
                .matches("if(NAME)12;else'a';")
                .matches("if(NAME){int number;}")
                .matches("if(NAME){int number=12;}else{'a';}");
    }

    @Test
    public void rulesTryCatchStament() {
        assertThat(parser)
                .matches("try{}catch(Exception ex){}")
                .matches("try{int number=0;}catch(Exception ex){char message=ex.message;}");
    }

    @Test
    public void rulesWhileStament() {
        assertThat(parser)
                .matches("while(trueExpression){}")
                .matches("while(A)12;")
                .matches("while(A){int number=0;}");
    }

    @Test
    public void rulesReturnStament() {
        assertThat(parser)
                .matches("return trueExpression;")
                .matches("return 0;")
                .matches("return nullExpression;");
    }

    @Test
    public void rulesVariableDeclaration() {
        assertThat(parser)
                .matches("int myVariable;")
                .matches("private double myVariable[];")
                .matches("public boolean my_Variable[];");
    }

    @Test
    public void rulesExpression() {
        assertThat(parser)
                .matches("1;")
                .matches("12;")
                .matches("1009;")
                .matches("'A';")
                .matches("'B';")
                .matches("'c';")
                .matches("'z';")
                .matches("'TIPE';")
                .matches("'name';")
                .matches("'myVariable';")
                .matches("'zA';");
    }
}
