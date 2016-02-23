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
package org.fundacionjala.enforce.sonarqube.apex.api;

import org.junit.Test;

import com.sonar.sslr.api.Grammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.VARIABLE_INITILIZER;

public class ApexGrammarVariableInitializerTest {

    private final Grammar grammarBuilder = ApexGrammar.create(Boolean.FALSE);

    @Test
    public void positiveRules_LiteralExpresion_IntegerExpresion() {
        assertThat(grammarBuilder.rule(VARIABLE_INITILIZER))
                .matches("1")
                .matches("12")
                .matches("1009");
    }

    @Test
    public void positiveRules_LiteralExpresion_StringLiteral() {
        assertThat(grammarBuilder.rule(VARIABLE_INITILIZER))
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
    public void positiveRules_NumericExpresion() {
        assertThat(grammarBuilder.rule(VARIABLE_INITILIZER))
                .matches("11-10")
                .matches("22+22")
                .matches("10*22")
                .matches("100/20")
                .matches("100%10");
    }

    @Test
    public void positiveRules_NumericExpresionOperationSimple() {
        assertThat(grammarBuilder.rule(VARIABLE_INITILIZER))
                .matches("11++")
                .matches("A++");
    }

    @Test
    public void positive_RulesOperationsSimpleMinus() {
        assertThat(grammarBuilder.rule(VARIABLE_INITILIZER))
                .matches("10>5")
                .matches("4>5");
    }

    @Test
    public void positiveRules_Identifier() {
        assertThat(grammarBuilder.rule(VARIABLE_INITILIZER))
                .matches("MY")
                .matches("myVariable");
    }

    @Test
    public void positiveRules_Null() {
        assertThat(grammarBuilder.rule(VARIABLE_INITILIZER))
                .matches("null");
    }

    @Test
    public void positiveRules_Super() {
        assertThat(grammarBuilder.rule(VARIABLE_INITILIZER))
                .matches("super");
    }

    @Test
    public void positiveRules_This() {
        assertThat(grammarBuilder.rule(VARIABLE_INITILIZER))
                .matches("this");
    }

    @Test
    public void positiveRules_CastingExpression() {
        assertThat(grammarBuilder.rule(VARIABLE_INITILIZER))
                .matches("(int)MyVariable")
                .matches("(MyObjecto)MyVariable");
    }
    
    @Test
    public void positiveRules_StringExpression() {
        assertThat(grammarBuilder.rule(VARIABLE_INITILIZER))
                .matches("1")
                .matches("MyVariable")
                .matches("NAME");
    }
}
