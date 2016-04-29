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
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.EXCEPTION;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.GLOBAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ITERATOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.MAP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.OVERRIDE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PRIVATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PROTECTED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PUBLIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STATIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TESTMETHOD;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRANSIENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VIRTUAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.AT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COMMA;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.DOT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.GT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ANNOTATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BRACKETS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.GENERIC_TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIERS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.REFERENCE_TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SIMPLE_TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SPECIAL_KEYWORDS_AS_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;

/**
 * This class contains constructors for Type rules and its sub rules.
 *
 */
public class Type {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        annotation(grammarBuilder);
        modifiers(grammarBuilder);
        typePi(grammarBuilder);
        referenceType(grammarBuilder);
        simpleType(grammarBuilder);
        classOrInterfaceType(grammarBuilder);
        genericType(grammarBuilder);
    }

    private static void modifiers(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(MODIFIERS).is(grammarBuilder.zeroOrMore(
                grammarBuilder.firstOf(
                        PUBLIC,
                        STATIC,
                        PROTECTED,
                        PRIVATE,
                        GLOBAL,
                        FINAL,
                        ABSTRACT,
                        TRANSIENT,
                        VIRTUAL,
                        OVERRIDE,
                        TESTMETHOD,
                        ANNOTATION))
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
                NAME
        );
    }

    /**
     * Rule for the types used in all the grammar.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void typePi(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE).is(grammarBuilder.firstOf(REFERENCE_TYPE,
                grammarBuilder.sequence(MAP,
                        LT,
                        TYPE,
                        COMMA,
                        TYPE,
                        GT),
                grammarBuilder.sequence(grammarBuilder.firstOf(LIST, SET, ITERATOR),
                        LT,
                        TYPE,
                        GT)
        ));
    }

    /**
     * Rule for the reference types used in the grammar.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void referenceType(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(REFERENCE_TYPE).is(
                SIMPLE_TYPE,
                grammarBuilder.optional(BRACKETS));
    }

    /**
     * Rule for the simple type.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void simpleType(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(SIMPLE_TYPE).is(
                grammarBuilder.firstOf(EXCEPTION,
                        CLASS_OR_INTERFACE_TYPE));
    }

    /**
     * Rule that defines what can be used as a type for a class or interface.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void classOrInterfaceType(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CLASS_OR_INTERFACE_TYPE).is(
                grammarBuilder.firstOf(
                        ALLOWED_KEYWORDS_AS_IDENTIFIER,
                        SPECIAL_KEYWORDS_AS_IDENTIFIER),
                grammarBuilder.zeroOrMore(
                        grammarBuilder.sequence(DOT,
                                grammarBuilder.firstOf(
                                        ALLOWED_KEYWORDS_AS_IDENTIFIER,
                                        SPECIAL_KEYWORDS_AS_IDENTIFIER))
                ));
    }

    private static void genericType(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(GENERIC_TYPE).is(LT,
                TYPE,
                grammarBuilder.optional(COMMA, TYPE),
                GT
        );
    }
}
