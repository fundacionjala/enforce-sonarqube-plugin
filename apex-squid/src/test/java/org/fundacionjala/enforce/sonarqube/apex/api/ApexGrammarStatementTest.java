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
                .matches("'TIPE';")
                .matches("'name';")
                .matches("'myVariable';")
                .matches("'zA';");
    }

    @Test
    public void positiveRulesIfElseStatement() {
        assertThat(grammarBuilder.rule(STATEMENT))
                .matches("if(NAME){}")
                .matches("if(NAME){}else{}")
                .matches("if(NAME)12;")
                .matches("if(NAME)12;else'a';")
                .matches("if(NAME){intnumber;}")
                .matches("if(NAME){intnumber=12;}else{'a';}");
    }

    @Test
    public void positiveRulesStatementBlock() {
        assertThat(grammarBuilder.rule(STATEMENT_BLOCK))
                .matches("{}")
                .matches("{intmyVariable;}")
                .matches("{intmyVariable=23;if(NAME)12;}");
    }

    @Test
    public void positiveRulesWhileStament() {
        assertThat(grammarBuilder.rule(STATEMENT))
                .matches("while(true){}")
                .matches("while(A)12;")
                .matches("while(A){intnumber=0;}");
    }

    @Test
    public void positiveRulesForStament() {
        assertThat(grammarBuilder.rule(STATEMENT))
                .matches("for(intMyObject:listIntegers){}")
                .matches("for(doubleMyObject:listDoubles)intnumber;")
                .matches("for(booleanMyObject:listBooleans){intnumber=i;}");
    }

    @Test
    public void positiveRulesTryCatchStament() {
        assertThat(grammarBuilder.rule(STATEMENT))
                .matches("try{}catch(intex){}")
                .matches("try{intnumber=0;}catch(charex){charmessage=ex.message;}");
    }

    @Test
    public void positiveRulesReturnStament() {
        assertThat(grammarBuilder.rule(STATEMENT))
                .matches("return;")
                .matches("returntrue;")
                .matches("return0;")
                .matches("returnnull;");
    }

    @Test
    public void positiveRulesDmlStament() {
        assertThat(grammarBuilder.rule(STATEMENT))
                .matches("insertnewAcct;")
                .matches("updatemyAcct;")
                .matches("upsertacctList;")
                .matches("deletedoomedAccts;")
                .matches("undeletesavedAccts;");
    }
}
