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

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.METHOD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.RESULT_TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_PATAMETERS;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

/**
 * The class creates the rules for the body of a method.
 */
public class ApexGrammarMethodDeclaration {

    /**
     * Create rules to the last line of the method and the completion of the
     * method.
     *
     * @return The grammar of the method declaration.
     */
    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder grammarBuilder = LexerlessGrammarBuilder.create();
        grammarBuilder.rule(RBRACE).is(RBRACE.getValue());
        grammarBuilder.rule(METHOD_DECLARATION).is(
                ApexGrammarTypeParameters.createGrammarBuilder().build().rule(TYPE_PATAMETERS),
                ApexGrammarResultType.createGrammarBuilder().build().rule(RESULT_TYPE),
                RBRACE
        );
        return grammarBuilder;
    }
}
