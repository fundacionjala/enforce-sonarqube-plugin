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
package org.fundacionjala.enforce.sonarqube.apex.api.grammar.buildersource;

import org.sonar.sslr.grammar.LexerfulGrammarBuilder;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHARING;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITHOUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.KEYWORD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIER_KEYWORD;

/**
 *
 * @author kevin_titichoca
 */
public class Keyword {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        keyword(grammarBuilder);
        modifierKeyWord(grammarBuilder);
    }

    /**
     * Grammar is created for a class modifier (lookahead or keyword).
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void modifierKeyWord(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(MODIFIER_KEYWORD).is(
                grammarBuilder.optional(MODIFIER),
                grammarBuilder.optional(KEYWORD)
        );
    }

    /**
     * Grammar to identify a sharing rule keyword.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void keyword(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(KEYWORD).is(
                grammarBuilder.firstOf(
                        WITHOUT,
                        WITH),
                SHARING
        );
    }
}
