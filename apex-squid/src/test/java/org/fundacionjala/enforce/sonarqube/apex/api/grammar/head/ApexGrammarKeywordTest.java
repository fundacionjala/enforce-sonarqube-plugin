/*
 * The MIT License
 *
 * Copyright 2016 Jalasoft.
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
package org.fundacionjala.enforce.sonarqube.apex.api.grammar.head;

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITHOUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.KEYWORD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WITHOUT_SHARING;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarKeywordTest {

    private final LexerlessGrammarBuilder b = ApexGrammarKeyword.createGrammarBuilder();

    @Test
    public void positiveBasicRulesWith() {
        assertThat(b.build().rule(WITH))
                .matches("with")
                .notMatches("without");
    }

    @Test
    public void positiveBasicRulesWithout() {
        assertThat(b.build().rule(WITHOUT))
                .matches("without")
                .notMatches("with");
    }

    @Test
    public void positiveBasicRulesSharing() {
        assertThat(b.build().rule(WITHOUT))
                .matches("without")
                .notMatches("with");
    }

    @Test
    public void positiveBasicRulesWithoutSharing() {
        assertThat(b.build().rule(WITHOUT_SHARING))
                .matches("withoutsharing");
    }

    @Test
    public void positiveBasicRules() {
        assertThat(b.build().rule(KEYWORD))
                .matches("withsharing")
                .matches("withoutsharing");
    }

    @Test
    public void negativeRules() {
        assertThat(b.build().rule(KEYWORD))
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
