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
package org.fundacionjala.enforce.sonarqube.apex.api.grammar.body;

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NULL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.RETURN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VOID;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.SEMICOLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.RESULT_TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_METHOD;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

/**
 *
 * The class creates the rules for the types of return method.
 */
public class ApexGrammarResultType {

    /**
     * Create rules for the return of a method and its value.
     *
     * @return The return of the result grammar.
     */
    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder grammarBuilder = LexerlessGrammarBuilder.create();
        grammarBuilder.rule(VOID).is(VOID.getValue());
        grammarBuilder.rule(NULL).is(NULL.getValue());
        grammarBuilder.rule(RETURN).is(RETURN.getValue());
        grammarBuilder.rule(SEMICOLON).is(SEMICOLON.getValue());
        grammarBuilder.rule(TYPE_METHOD).is(ApexGrammarType.createGrammarBuilder().build().rule(TYPE));
        grammarBuilder.rule(RESULT_TYPE).is(
                RETURN,
                grammarBuilder.firstOf(
                        VOID,
                        TYPE_METHOD,
                        NULL),
                SEMICOLON
        );
        return grammarBuilder;
    }
}
