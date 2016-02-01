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

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BOOLEAN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BYTE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CHAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DOUBLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FLOAT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LONG;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHORT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.PRIMITIVE_TYPE;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

/**
 * The class is responsible for creating the rules for primitive data types.
 */
public class ApexGrammarPrimitiveType {

    /**
     * Create rules for the primitive values of a class.
     *
     * @return The grammar of the primitive data types of a class.
     */
    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder grammarBuilder = LexerlessGrammarBuilder.create();
        grammarBuilder.rule(BOOLEAN).is(BOOLEAN.getValue());
        grammarBuilder.rule(CHAR).is(CHAR.getValue());
        grammarBuilder.rule(BYTE).is(BYTE.getValue());
        grammarBuilder.rule(SHORT).is(SHORT.getValue());
        grammarBuilder.rule(INT).is(INT.getValue());
        grammarBuilder.rule(LONG).is(LONG.getValue());
        grammarBuilder.rule(FLOAT).is(FLOAT.getValue());
        grammarBuilder.rule(DOUBLE).is(DOUBLE.getValue());
        grammarBuilder.rule(PRIMITIVE_TYPE).is(grammarBuilder.firstOf(BOOLEAN,
                CHAR,
                BYTE,
                SHORT,
                INT,
                LONG,
                FLOAT,
                DOUBLE));
        return grammarBuilder;
    }
}
