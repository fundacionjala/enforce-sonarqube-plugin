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
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INTEGER_LITERAL_NUMBER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarIntegerLiteralNumberTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(INTEGER_LITERAL_NUMBER);
    }

    @Test
    public void rulesIntegerLiteralNumber() {
        assertThat(parser)
                .matches("12345567890")
                .matches("12345567890l")
                .matches("12345567890L")
                .matches("0x1234567890abcdef")
                .matches("0x1234567890abcdefl")
                .matches("0x1234567890abcdefL")
                .matches("01234567")
                .matches("01234567l")
                .matches("01234567L");
    }

    @Test
    public void rulesIntegerLiteralNumberCaseError() {
        assertThat(parser)
                .notMatches("08")
                .notMatches("0x323bct")
                .notMatches("0123456789");
    }

}
