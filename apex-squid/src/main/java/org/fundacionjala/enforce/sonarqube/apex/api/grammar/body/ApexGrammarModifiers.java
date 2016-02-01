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

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.DEFAULT_VALUE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.LOOKAHEAD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.MODIFIERS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.head.ApexGrammarIdentifier;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.head.ApexGrammarLookahead;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_METHOD;

/**
 * The class creates the rules for the head of a method.
 */
public class ApexGrammarModifiers {

    /**
     * Create rules for the header of a method.
     *
     * @return The grammar of modifiers.
     */
    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder grammarBuilder = LexerlessGrammarBuilder.create();
        grammarBuilder.rule(LPAREN).is(LPAREN.getValue());
        grammarBuilder.rule(RPAREN).is(RPAREN.getValue());
        grammarBuilder.rule(LBRACE).is(LBRACE.getValue());
        grammarBuilder.rule(TYPE_METHOD).is(ApexGrammarType.createGrammarBuilder().build().rule(TYPE));
        grammarBuilder.rule(MODIFIERS).is(
                ApexGrammarLookahead.createGrammarBuilder().build().rule(LOOKAHEAD),
                TYPE_METHOD,
                ApexGrammarIdentifier.createGrammarBuilder().build().rule(IDENTIFIER),
                LPAREN,
                ApexGrammarDefaultValue.createGrammarBuilder().build().rule(DEFAULT_VALUE),
                RPAREN,
                LBRACE
        );
        return grammarBuilder;
    }
}
