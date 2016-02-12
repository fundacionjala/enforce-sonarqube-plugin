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

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.KEYWORD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WITHOUT_SHARING;

public class ApexGrammarKeywordTest {

    private final Grammar grammarBuilder = ApexGrammar.create(Boolean.FALSE);

    @Test
    public void positiveBasicRulesWithoutSharing() {
        assertThat(grammarBuilder.rule(WITHOUT_SHARING))
                .matches("withoutsharing");
    }

    @Test
    public void positiveBasicRules() {
        assertThat(grammarBuilder.rule(KEYWORD))
                .matches("withsharing")
                .matches("withoutsharing");
    }

    @Test
    public void negativeRules() {
        assertThat(grammarBuilder.rule(KEYWORD))
                .notMatches(" ? ")
                .notMatches("_with sharing ")
                .notMatches("_with_sharing ")
                .notMatches("_withSharing")
                .notMatches("with sharinG ")
                .notMatches(" without_sharing ")
                .notMatches("withoutSharing")
                .notMatches(" Without Sharing ");
    }
}
