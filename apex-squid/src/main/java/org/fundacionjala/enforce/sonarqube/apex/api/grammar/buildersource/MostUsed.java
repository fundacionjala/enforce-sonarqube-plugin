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
import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ACTIVATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.AFTER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ANY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ARRAY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.AUTONOMOUS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BEFORE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BEGIN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BIGDECIMAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BYTE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CASE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CAST;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CATEGORY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CHAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.COLLECT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CONST;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CONVERT_CURRENCY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DATA;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DEFAULT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DELETE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.END;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.EXCEPTION;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.EXIT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.EXPORT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FALSE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FIRST;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FLOAT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.GET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.GOTO;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.GROUP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.HINT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IMPORT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INNER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INSERT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INTO;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ITERATOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.JOIN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LAST;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LIMIT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LOOP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.MERGE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NETWORK;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NULL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NUMBER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.OF;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.OFFSET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.OUTER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PACKAGE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PARALLEL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.RETRIEVE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.RETURNING;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ROLLBACK;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SAVE_POINT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SEARCH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHARING;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHORT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STAT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SWITCH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SYNCHRONIZED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.THEN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TO_LABEL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRANSACTION;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRANSIENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRIGGER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRUE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UNDELETE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UPDATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UPSERT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WHEN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITHOUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.DOT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.MINUS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.PLUS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.HEXADECIMAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.NUMERIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.STRING;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER_FOR_METHODS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BOOLEAN_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DECIMAL_EXPONENT_NUMBER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DECIMAL_FLOATING_POINT_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DECIMAL_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DOT_NUMBER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FLOATING_POINT_LITERAL_NUMBER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.HEXADECIMAL_EXPONENT_NUMBER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.HEXADECIMAL_FLOATING_POINT_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.HEX_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INTEGER_LITERAL_NUMBER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NULL_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NUMBER_DOT_NUMBER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NUMBER_EXPONENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.OCTAL_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SPECIAL_KEYWORDS_AS_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STRING_LITERAL_STRING;

/**
 * This class contains constructors for most used rules and its sub rules.
 *
 */
public class MostUsed {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        allowedKeywordsAsIdentifier(grammarBuilder);
        specialKeywordsAsIdentifier(grammarBuilder);
        block(grammarBuilder);
        name(grammarBuilder);
        decimalLiteral(grammarBuilder);
        hexLiteral(grammarBuilder);
        octalLiteral(grammarBuilder);
        integerLiteralNumber(grammarBuilder);
        stringLiteralString(grammarBuilder);
        booleanLiteral(grammarBuilder);
        nullLiteral(grammarBuilder);
        literal(grammarBuilder);
        decimalExponentNumber(grammarBuilder);
        decimalFloatingPointLiteral(grammarBuilder);
        hexadecimalExponentNumber(grammarBuilder);
        hexadecimalFloatingPointLiteral(grammarBuilder);
        floatingPointLiteralNumber(grammarBuilder);
        allowedKeywordsAsIdentifierForMethods(grammarBuilder);
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
    private static void block(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(BLOCK).is(
                LBRACE,
                grammarBuilder.zeroOrMore(BLOCK_STATEMENT),
                RBRACE
        );
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
                        INTEGER_LITERAL_NUMBER,
                        STRING_LITERAL_STRING,
                        BOOLEAN_LITERAL,
                        NULL_LITERAL,
                        SPECIAL_KEYWORDS_AS_IDENTIFIER
                )
        );
    }

    /**
     * Defines rule for decimal exponent number.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void decimalExponentNumber(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DECIMAL_EXPONENT_NUMBER).is(
                IDENTIFIER,
                grammarBuilder.optional(
                        grammarBuilder.firstOf(PLUS, MINUS)
                ),
                grammarBuilder.zeroOrMore(NUMERIC)
        );

    }

    private static void decimalFloatingPointLiteral(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DECIMAL_FLOATING_POINT_LITERAL).is(
                grammarBuilder.firstOf(
                        NUMBER_DOT_NUMBER,
                        DOT_NUMBER,
                        NUMBER_EXPONENT)
        );

        grammarBuilder.rule(NUMBER_DOT_NUMBER).is(
                grammarBuilder.zeroOrMore(NUMERIC),
                DOT,
                NUMERIC,
                grammarBuilder.optional(DECIMAL_EXPONENT_NUMBER)
        );

        grammarBuilder.rule(DOT_NUMBER).is(
                DOT,
                grammarBuilder.zeroOrMore(NUMBER),
                grammarBuilder.optional(DECIMAL_EXPONENT_NUMBER)
        );

        grammarBuilder.rule(NUMBER_EXPONENT).is(
                grammarBuilder.zeroOrMore(NUMERIC),
                grammarBuilder.optional(DECIMAL_EXPONENT_NUMBER)
        );
    }

    private static void hexadecimalExponentNumber(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(HEXADECIMAL_EXPONENT_NUMBER).is(
                IDENTIFIER,
                grammarBuilder.optional(
                        grammarBuilder.firstOf(PLUS, MINUS)
                ),
                grammarBuilder.zeroOrMore(NUMERIC)
        );
    }

    private static void hexadecimalFloatingPointLiteral(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(HEXADECIMAL_FLOATING_POINT_LITERAL).is(
                NUMERIC,
                HEXADECIMAL,
                grammarBuilder.optional(DOT),
                grammarBuilder.optional(HEXADECIMAL_EXPONENT_NUMBER)
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
}
