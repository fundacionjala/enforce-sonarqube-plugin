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

import static org.sonar.sslr.tests.Assertions.assertThat;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXPRESSION;

public class ApexGrammarExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(EXPRESSION);
    }

    @Test
    public void positiveRules_LiteralExpresion_IntegerExpresion() {
        assertThat(parser)
                .matches("1")
                .matches("12")
                .matches("1009");
    }

    @Test
    public void positiveRules_LiteralExpresion_StringLiteral() {
        assertThat(parser)
                .matches("'A'")
                .matches("'B'")
                .matches("'c'")
                .matches("'z'")
                .matches("'TIPE'")
                .matches("'name'")
                .matches("'myVariable'")
                .matches("'zA'");

    }

    @Test
    public void positiveRulesNumeralExpresion() {
        assertThat(parser)
                .matches("11-10")
                .matches("22+22")
                .matches("10*22")
                .matches("100/20")
                .matches("100%10");
    }

    @Test
    public void positiveRulesNumericExpresionOperationSimple() {
        assertThat(parser)
                .matches("11++")
                .matches("A++");
    }

    @Test
    public void positiveRulesTestingExpression() {
        assertThat(parser)
                .matches("10>5")
                .matches("8>15");
    }

    @Test
    public void positiveRulesIdentifier() {
        assertThat(parser)
                .matches("MyVariable")
                .matches("PATHERN");
    }

    @Test
    public void positiveRulesNull() {
        assertThat(parser)
                .matches("null");
    }

    @Test
    public void positiveRulesSuper() {
        assertThat(parser)
                .matches("super");
    }

    @Test
    public void positiveRulesThis() {
        assertThat(parser)
                .matches("this");
    }

    @Test
    public void positiveRulesCastingExpression() {
        assertThat(parser)
                .matches("(int)MyVariable")
                .matches("(MyObjecto)MyVariable");
    }

    @Test
    public void positiveRulesStringExpression() {
        assertThat(parser)
                .matches("1")
                .matches("MyVariable")
                .matches("NAME")
                .matches("instance.name")
                .notMatches(".name");
    }

    @Test
    public void positiveRulesCreatingExpression() {
        assertThat(parser)
                .matches("newMyClass()")
                .matches("newMyClass(name)")
                .matches("newMyClass(name,id,type)");
    }

    @Test
    public void positiveRulesInvokeExpression() {
        assertThat(parser)
                .matches("array.length")
                .matches("list.size()")
                .matches("map.put(23,'message').values().toString()");
    }
}
