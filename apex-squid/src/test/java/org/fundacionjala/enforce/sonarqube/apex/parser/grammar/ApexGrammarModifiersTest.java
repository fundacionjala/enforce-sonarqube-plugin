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

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIERS;
import org.junit.Before;
import org.junit.Test;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarModifiersTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(MODIFIERS);
    }

    @Test
    public void positiveBasicRules() {
        assertThat(parser)
                .matches("public")
                .matches("static")
                .matches("protected")
                .matches("private")
                .matches("global")
                .matches("final")
                .matches("abstract")
                .matches("transient")
                .matches("virtual")
                .matches("override")
                .matches("Abstract")
                .matches("testmethod");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("_Transient")
                .notMatches(" _ ")
                .notMatches("P static")
                .notMatches(" public_")
                .notMatches(" _final")
                .notMatches("gLobla")
                .notMatches("OveRRivde")
                .notMatches("Virtual_")
                .notMatches("testMethossfd__");
    }
}
