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

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT_BLOCK;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT_IF;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WHILE_STATEMENT;

public class ApexGrammarStatementTest {

    private final Grammar grammarBuilder = ApexGrammar.create(Boolean.FALSE);

    @Test
    public void positiveRulesVariableDeclaration() {
        assertThat(grammarBuilder.rule(STATEMENT))
                .matches("intmyVariable;")
                .matches("privatedoublemyVariable[];")
                .matches("publicbooleanmy_Variable[];");
    }

    @Test
    public void positiveRulesExpression() {
        assertThat(grammarBuilder.rule(STATEMENT))
                .matches("1;")
                .matches("12;")
                .matches("1009;")
                .matches("'A';")
                .matches("'B';")
                .matches("'c';")
                .matches("'z';")
                .matches("\"TIPE\";")
                .matches("\"name\";")
                .matches("\"myVariable\";")
                .matches("\"zA\";");
    }

    @Test
    public void positiveRulesIfElseStatement() {
        assertThat(grammarBuilder.rule(STATEMENT_IF))
                .matches("if(NAME)12;")
                .matches("if(NAME)12;else'a';");

    }

    @Test
    public void positiveRulesIfStatement() {
        assertThat(grammarBuilder.rule(STATEMENT))
                .matches("if(NAME)12;")
                .matches("if(NAME)12;else'a';");

    }

    @Test
    public void positiveRulesStatementBlock() {
        assertThat(grammarBuilder.rule(STATEMENT_BLOCK))
                .matches("{intmyVariable;}")
                .matches("{intmyVariable;if(NAME)12;");

    }

    @Test
    public void positiveRules_StatementBlock() {
        assertThat(grammarBuilder.rule(STATEMENT))
                .matches("{intmyVariable;}")
                .matches("{intmyVariable;if(NAME)12;");

    }

    @Test
    public void positiveRulesWhileStament() {
        assertThat(grammarBuilder.rule(WHILE_STATEMENT))
                .matches("While(A)12;")
                .matches("While(A){12;}");

    }
    
     @Test
    public void positiveRules_WhileStament() {
        assertThat(grammarBuilder.rule(STATEMENT))
                .matches("While(A)12;")
                .matches("While(A){12;}");

    }
}
