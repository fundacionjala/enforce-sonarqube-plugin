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
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FLOATING_POINT_LITERAL_NUMBER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarFloatingPointLiteralNumberTest extends ApexRuleTest{
    
    @Before
    public void setUp() {
        setRootRule(FLOATING_POINT_LITERAL_NUMBER);
    }
    
    @Test
    public void testValidFloatingPointLiteralNumber() {
        assertThat(parser)
                .matches("1234545.6789e-12345")
                .matches(".123345e-1235")
                .matches("123123e232d")
                .matches("12312345D") 
                .matches("312345d")
                .matches("0x1234567890abcdefp-123456789F")
                .matches("0x1234567890abcdef.abcdef1234567890")
                .matches("0x123345ab.abcdep+123D");
    }
}
