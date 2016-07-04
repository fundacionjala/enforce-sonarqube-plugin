/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.api.grammar.buildersource;

import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.*;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.*;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.*;

/**
 * This class contains constructors for Type rules and its sub rules.
 *
 */
public class Type {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        annotation(grammarBuilder);
        modifiers(grammarBuilder);
        type(grammarBuilder);
        referenceType(grammarBuilder);
        simpleType(grammarBuilder);
        classOrInterfaceType(grammarBuilder);
        classOrInterfaceErasureType(grammarBuilder);
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
                        WEBSERVICE,
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
                NAME,
                grammarBuilder.optional(
                        grammarBuilder.sequence(LPAREN,
                                grammarBuilder.zeroOrMore(EXPRESSION),
                                RPAREN))
        );
    }

    /**
     * Rule for the types used in all the grammar.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void type(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE).is(grammarBuilder.firstOf(
                REFERENCE_TYPE,
                grammarBuilder.sequence(MAP,
                        LT,
                        TYPE,
                        COMMA,
                        TYPE,
                        GT),
                grammarBuilder.sequence(
                        grammarBuilder.firstOf(LIST, SET, ITERATOR),
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
                                        ALLOWED_KEYWORDS_AS_IDENTIFIER_FOR_METHODS,
                                        SPECIAL_KEYWORDS_AS_IDENTIFIER))
                ));
    }
    
    /**
     * Rule that defines what can be used as an "implements" parameter type.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void classOrInterfaceErasureType(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CLASS_OR_INTERFACE_ERASURE_TYPE).is(
                CLASS_OR_INTERFACE_TYPE,
                grammarBuilder.optional(
                        grammarBuilder.sequence(
                                LT,
                                TYPE,
                                grammarBuilder.zeroOrMore(
                                        COMMA,
                                        TYPE
                                ),
                                GT
                        )
                )
        );
    }

    /**
     * Rule that defines the generic type for allocationExpressions.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void genericType(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(GENERIC_TYPE).is(LT,
                TYPE,
                grammarBuilder.optional(COMMA, TYPE),
                GT
        );
    }
}
