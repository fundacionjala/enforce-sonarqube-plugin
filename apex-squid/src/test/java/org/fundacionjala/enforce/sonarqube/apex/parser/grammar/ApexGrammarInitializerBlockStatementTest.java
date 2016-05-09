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

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INITIALIZER_BLOCK_STATEMENT;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarInitializerBlockStatementTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(INITIALIZER_BLOCK_STATEMENT);
    }

    @Test
    public void testValidInitilizerBlockStatement() {
        assertThat(parser)
                .matches(";")
                .matches("anyExpression;")
                .matches("if(true){} else {}")
                .matches("while(true){}")
                .matches("do {} while(true);")
                .matches("for(integer index = 0; index<10; index++){}")
                .matches("for(integer currentNumber : numbers){}")
                .matches("break;")
                .matches("continue;")
                .matches("return this;")
                .matches("return someVariable;")
                .matches("throw anyException;")
                .matches("try{} catch(exception anyException){} finally {}");
    }
}
