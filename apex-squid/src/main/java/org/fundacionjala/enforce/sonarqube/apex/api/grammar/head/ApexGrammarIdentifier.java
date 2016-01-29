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

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.UNDERSCORE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.BODY_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CHAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.INIT_IDENTIFIER;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.IDENTIFIER;

/**
 * The class creates the rule for the class identifier (name).
 */
public class ApexGrammarIdentifier {

    private final static String RULE_CHAR = "[A-Za-z]";
    private final static String RULE_BODY_IDENTIFIER = "[A-Za-z0-9_]*";
    
    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder grammarBuilder = LexerlessGrammarBuilder.create();
        grammarBuilder.rule(CHAR).is(grammarBuilder.regexp(RULE_CHAR));
        grammarBuilder.rule(UNDERSCORE).is(UNDERSCORE.getValue());
        grammarBuilder.rule(INIT_IDENTIFIER).is(grammarBuilder.firstOf(
                CHAR,
                UNDERSCORE));
        grammarBuilder.rule(BODY_IDENTIFIER).is(grammarBuilder.regexp(RULE_BODY_IDENTIFIER));
        grammarBuilder.rule(IDENTIFIER).is(INIT_IDENTIFIER, BODY_IDENTIFIER);
        return grammarBuilder;
    }
}
