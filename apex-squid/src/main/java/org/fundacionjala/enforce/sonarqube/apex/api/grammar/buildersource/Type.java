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
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ABSTRACT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ANOTATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.AURA_ENABLED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BOOLEAN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BYTE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CHAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DEPRECATED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DOUBLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FLOAT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FUTURE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INVOCABLE_METHOD;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INVOCABLE_VARIABLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IS_TEST;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LONG;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NATIVE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PRIVATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PROTECTED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PUBLIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.READ_ONLY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.REMOTE_ACTION;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHORT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STATIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STRICTFP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SYNCHRONIZED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TEST_SETUP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TEST_VISIBLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRANSIENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VOID;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VOLATILE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.AT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ANNOTATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE_SPECIFIER;

/**
 *
 * @author kevin_titichoca
 */
public class Type {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        typeSpecifier(grammarBuilder);
        type(grammarBuilder);
        annotation(grammarBuilder);
        modifier(grammarBuilder);
    }

    /**
     * Grammar for different access modifiers of a class is created.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void modifier(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(MODIFIER).is(grammarBuilder.firstOf(
                PUBLIC,
                STATIC,
                PROTECTED,
                PRIVATE,
                FINAL,
                ABSTRACT,
                SYNCHRONIZED,
                NATIVE,
                TRANSIENT,
                VOLATILE,
                STRICTFP,
                ANOTATION)
        );
    }

    /**
     * Grammar to identify a sharing rule keyword.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void annotation(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ANNOTATION).is(
                AT,
                grammarBuilder.firstOf(
                        AURA_ENABLED,
                        DEPRECATED,
                        FUTURE,
                        INVOCABLE_METHOD,
                        INVOCABLE_VARIABLE,
                        IS_TEST,
                        READ_ONLY,
                        REMOTE_ACTION,
                        TEST_SETUP,
                        TEST_VISIBLE
                )
        );
    }

    /**
     * Creates rules for the primitive values of a class.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void typeSpecifier(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE_SPECIFIER).is(
                grammarBuilder.firstOf(
                        BOOLEAN,
                        CHAR,
                        BYTE,
                        SHORT,
                        INT,
                        LONG,
                        FLOAT,
                        VOID,
                        DOUBLE,
                        CLASS_NAME
                )
        );
    }

    /**
     * It is responsible for building the rules for the different types of
     * return of a method.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void type(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE).is(TYPE_SPECIFIER);
    }
}
