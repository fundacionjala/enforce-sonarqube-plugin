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
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UNDELETE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UPDATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UPSERT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WHEN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITHOUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.DOT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.NUMERIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER_FOR_METHODS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DECIMAL_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SOQL_DATE_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SOQL_NDATE_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SPECIAL_KEYWORDS_AS_IDENTIFIER;

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
}
