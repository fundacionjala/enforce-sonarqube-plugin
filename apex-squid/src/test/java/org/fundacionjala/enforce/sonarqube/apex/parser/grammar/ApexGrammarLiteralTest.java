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

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LITERAL;
import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarLiteralTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(LITERAL);
    }

    @Test
    public void testValidLiteral() {
        assertThat(parser)
                .matches("323l")
                .matches("323d")
                .matches("1.1f")
                .matches("0x123abcde.1235p212F")
                .matches("1.2323e+12d")
                .matches("0x123f")
                .matches("0x123.432p-323")
                .matches("'anyString'")
                .matches("true")
                .matches("false")
                .matches("null")
                .matches("without")
                .matches("limit");
    }

    @Test
    public void testInvalidLiteral() {
        assertThat(parser)
                .notMatches("323l120x234abcd")
                .notMatches("0x234234.p-234f")
                .notMatches("True")
                .notMatches("False")
                .notMatches("NULL")
                .notMatches("nuLL")
                .notMatches("nul");
    }
}
