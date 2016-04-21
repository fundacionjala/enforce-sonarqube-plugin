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
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_LOOP;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarForLoopTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(FOR_LOOP);
    }

    @Test
    public void testValidForLoop() {
        assertThat(parser)
                .matches("(int variable = 3; addition; expression){}")
                .matches("(;;){}")
                .matches("(; dosomething; updateExpression){}")
                .matches("(int addition; addToAddition; updateExpression){}")
                .matches("(int addition,multiplication; addBoth; updateExpression){}");
    }

    @Test
    public void testInvalidForLoop() {
        assertThat(parser)
                .notMatches("int variable =3; doSomethin{}")
                .notMatches("(;;)");
    }
}
