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

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHARING;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITHOUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.KEYWORD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WITHOUT_SHARING;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WITH_SHARING;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

/**
 * The class creates the rules for keywords, which is composed of the words with
 * or without + sharing .
 */
public class ApexGrammarKeyword {

    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder grammarBuilder = LexerlessGrammarBuilder.create();

        grammarBuilder.rule(WITH).is(WITH.getValue());
        grammarBuilder.rule(WITHOUT).is(WITHOUT.getValue());
        grammarBuilder.rule(SHARING).is(SHARING.getValue());
        grammarBuilder.rule(WITH_SHARING).is(WITH, SHARING);
        grammarBuilder.rule(WITHOUT_SHARING).is(WITHOUT, SHARING);

        grammarBuilder.rule(KEYWORD).is(grammarBuilder.firstOf(
                WITHOUT_SHARING,
                WITH_SHARING));

        return grammarBuilder;
    }
}
