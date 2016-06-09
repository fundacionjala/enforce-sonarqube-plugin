/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.api.grammar.buildersource;

import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.*;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.*;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.*;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.*;

import static com.sonar.sslr.api.GenericTokenType.EOF;
import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;

/**
 * This class contains constructors for most used rules and its sub rules.
 *
 */
public class MostUsed {

    public static void create(LexerfulGrammarBuilder grammarBuilder, boolean errorRecoveryEnabled) {
        allowedKeywordsAsIdentifier(grammarBuilder);
        specialKeywordsAsIdentifier(grammarBuilder);
        block(grammarBuilder, errorRecoveryEnabled);
        name(grammarBuilder);
        decimalLiteral(grammarBuilder);
        hexLiteral(grammarBuilder);
        octalLiteral(grammarBuilder);
        integerLiteralNumber(grammarBuilder);
        stringLiteralString(grammarBuilder);
        booleanLiteral(grammarBuilder);
        nullLiteral(grammarBuilder);
        literal(grammarBuilder);
        decimalFloatingPointLiteral(grammarBuilder);
        hexadecimalFloatingPointLiteral(grammarBuilder);
        floatingPointLiteralNumber(grammarBuilder);
        allowedKeywordsAsIdentifierForMethods(grammarBuilder);
        commonIdentifier(grammarBuilder);
    }

    /**
     * Grammar to define which are the allowed keywords to be use as identifier.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void allowedKeywordsAsIdentifier(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ALLOWED_KEYWORDS_AS_IDENTIFIER).is(
                grammarBuilder.firstOf(
                        IDENTIFIER,
                        TRANSIENT,
                        RETURNING,
                        SEARCH,
                        STAT,
                        CONVERT_CURRENCY,
                        SAVE_POINT,
                        TO_LABEL,
                        SHARING,
                        GET,
                        AFTER,
                        BEFORE,
                        FIRST,
                        LAST,
                        CATEGORY,
                        NETWORK,
                        ITERATOR));
    }

    /**
     * Grammar to define which are the allowed keywords to be used as
     * identifier.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void specialKeywordsAsIdentifier(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(SPECIAL_KEYWORDS_AS_IDENTIFIER).is(
                grammarBuilder.firstOf(
                        WITHOUT,
                        OFFSET,
                        DATA,
                        GROUP,
                        LIMIT
                ));
    }

    /**
     * Grammar to define which are the allowed keywords to be used as identifier
     * for methods.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void allowedKeywordsAsIdentifierForMethods(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ALLOWED_KEYWORDS_AS_IDENTIFIER_FOR_METHODS).is(
                grammarBuilder.firstOf(
                        IDENTIFIER,
                        //                        SOQL_DATE_LITERAL,
                        //                        SOQL_NDATE_LITERAL,
                        ARRAY,
                        EXCEPTION,
                        INT,
                        PACKAGE,
                        TRANSIENT,
                        ACTIVATE,
                        ANY,
                        AUTONOMOUS,
                        BEGIN,
                        BIGDECIMAL,
                        BYTE,
                        CASE,
                        CAST,
                        CHAR,
                        COLLECT,
                        CONST,
                        DEFAULT,
                        END,
                        EXIT,
                        EXPORT,
                        FLOAT,
                        GOTO,
                        GROUP,
                        HINT,
                        IMPORT,
                        INNER,
                        INTO,
                        JOIN,
                        LOOP,
                        NUMBER,
                        OF,
                        OUTER,
                        PARALLEL,
                        RETRIEVE,
                        RETURNING,
                        SEARCH,
                        SHORT,
                        STAT,
                        SWITCH,
                        SYNCHRONIZED,
                        THEN,
                        TRANSACTION,
                        WHEN,
                        CONVERT_CURRENCY,
                        ROLLBACK,
                        SAVE_POINT,
                        TO_LABEL,
                        SHARING,
                        WITHOUT,
                        WITH,
                        SET,
                        GET,
                        AFTER,
                        TRIGGER,
                        FIRST,
                        LAST,
                        CATEGORY,
                        NETWORK,
                        DELETE,
                        INSERT,
                        MERGE,
                        UNDELETE,
                        UPDATE,
                        UPSERT,
                        ITERATOR
                ));
    }

    /**
     * Grammar to define block rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void block(LexerfulGrammarBuilder grammarBuilder, boolean errorRecoveryEnabled) {
        if (errorRecoveryEnabled) {
            grammarBuilder.rule(RECOVERED_STATEMENT).is(
                    grammarBuilder.anyTokenButNot(
                            grammarBuilder.firstOf(BLOCK_STATEMENT, RBRACE, EOF, SEMICOLON))
            );
            grammarBuilder.rule(RECOVERED_STATEMENTS).is(
                    grammarBuilder.zeroOrMore(RECOVERED_STATEMENT)
            );
            grammarBuilder.rule(BLOCK).is(
                    LBRACE,
                    grammarBuilder.zeroOrMore(
                            grammarBuilder.firstOf(BLOCK_STATEMENT, RECOVERED_STATEMENT)),
                    RBRACE
            );
        } else {
            grammarBuilder.rule(BLOCK).is(
                    LBRACE,
                    grammarBuilder.zeroOrMore(BLOCK_STATEMENT),
                    RBRACE
            );
        }
    }

    /**
     * Defines the rule for name.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void name(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(NAME).is(
                METHOD_IDENTIFIER,
                grammarBuilder.zeroOrMore(
                        DOT,
                        METHOD_IDENTIFIER
                )
        );
    }

    /**
     * Defines decimal literal rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void decimalLiteral(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DECIMAL_LITERAL).is(
                NUMERIC
        );
    }

    /**
     * Defines rule for hexadecimal literal.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void hexLiteral(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(HEX_LITERAL).is(
                NUMERIC,
                HEXADECIMAL
        );
    }

    /**
     * Defines rule for octal literal.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void octalLiteral(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(OCTAL_LITERAL).is(
                NUMERIC
        );
    }

    /**
     * Defines rule for integer literal number.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void integerLiteralNumber(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(INTEGER_LITERAL_NUMBER).is(
                grammarBuilder.firstOf(
                        HEX_LITERAL,
                        OCTAL_LITERAL,
                        DECIMAL_LITERAL
                )
        );
    }

    /**
     * Defines rule for string literal string.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void stringLiteralString(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STRING_LITERAL_STRING).is(
                STRING
        );
    }

    /**
     * Defines rule for boolean literal.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void booleanLiteral(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(BOOLEAN_LITERAL).is(
                grammarBuilder.firstOf(
                        TRUE,
                        FALSE)
        );
    }

    /**
     * Defines rule for null literal.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void nullLiteral(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(NULL_LITERAL).is(
                NULL
        );
    }

    /**
     * Defines rule for literal.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void literal(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(LITERAL).is(
                grammarBuilder.firstOf(
                        FLOATING_POINT_LITERAL_NUMBER,
                        INTEGER_LITERAL_NUMBER,
                        STRING_LITERAL_STRING,
                        BOOLEAN_LITERAL,
                        NULL_LITERAL,
                        SPECIAL_KEYWORDS_AS_IDENTIFIER,
                        INTEGER_LITERAL_NUMBER
                )
        );
    }

    private static void decimalFloatingPointLiteral(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DECIMAL_FLOATING_POINT_LITERAL).is(
                grammarBuilder.firstOf(
                        grammarBuilder.sequence(
                                grammarBuilder.oneOrMore(NUMERIC),
                                DOT,
                                NUMERIC
                        ),
                        grammarBuilder.sequence(
                                DOT,
                                grammarBuilder.oneOrMore(NUMERIC)
                        ),
                        NUMERIC)
        );
    }

    private static void hexadecimalFloatingPointLiteral(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(HEXADECIMAL_FLOATING_POINT_LITERAL).is(
                NUMERIC,
                HEXADECIMAL
        );
    }

    private static void floatingPointLiteralNumber(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FLOATING_POINT_LITERAL_NUMBER).is(
                grammarBuilder.firstOf(
                        HEXADECIMAL_FLOATING_POINT_LITERAL,
                        DECIMAL_FLOATING_POINT_LITERAL
                )
        );
    }

    private static void commonIdentifier(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(COMMON_IDENTIFIER).is(
                grammarBuilder.firstOf(
                        ALLOWED_KEYWORDS_AS_IDENTIFIER,
                        SPECIAL_KEYWORDS_AS_IDENTIFIER
                )
        );
    }
}
