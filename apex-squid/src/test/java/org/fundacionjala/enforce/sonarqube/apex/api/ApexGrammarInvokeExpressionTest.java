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

import com.sonar.sslr.api.Grammar;
import org.junit.Test;

import static org.sonar.sslr.tests.Assertions.assertThat;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.INVOKE_EXPRESSION;

public class ApexGrammarInvokeExpressionTest {

    private final Grammar grammarBuilder = ApexGrammar.create(Boolean.FALSE);

    @Test
    public void testExpression() {
        assertThat(grammarBuilder.rule(INVOKE_EXPRESSION))
                .matches("account")
                .notMatches("22")
                .notMatches("'title'")
                .notMatches("100%10");
    }

    @Test
    public void testExpressionWhenInvokeProperties() {
        assertThat(grammarBuilder.rule(INVOKE_EXPRESSION))
                .matches("account.name")
                .notMatches("book.'title'")
                .notMatches("calculator.100%10");
    }

    @Test
    public void testExpressionWhenInvokeMethods() {
        assertThat(grammarBuilder.rule(INVOKE_EXPRESSION))
                .matches("account.update()")
                .matches("book.getTitle().toString()");
    }

    @Test
    public void testExpressionWhenInvokeMethodsWithArguments() {
        assertThat(grammarBuilder.rule(INVOKE_EXPRESSION))
                .matches("account.save(user)")
                .matches("book.setTitle('Avergers')")
                .matches("calculator.sum(11,digit).pow(3).toString()");
    }
}
