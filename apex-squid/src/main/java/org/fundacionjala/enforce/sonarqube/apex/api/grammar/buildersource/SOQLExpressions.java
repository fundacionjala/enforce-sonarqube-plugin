/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.api.grammar.buildersource;

import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.AND_SOQL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.AS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ASC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CASE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.COUNT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CUBE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DES;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.EXCLUDES;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FIRST;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FROM;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.GROUP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INCLUDES;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LAST;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LIKE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LIMIT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NOT_SOQL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NULL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NULLS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.OFFSET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ORDER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.OR_SOQL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ROLLUP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SELECT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WHERE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.ASSIGN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COMMA;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.DOT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.GEQUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.GT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LEQUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.NOTEQUALS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.UNDERSCORE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.INTEGER_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.STRING;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALIASSTATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_SOBJECT_NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.AND_SOQL_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONDITIONAL_SOQL_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.COUNT_EXPR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FIELD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FIELD_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FILTERING_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FROM_SENTENCE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.GROUP_BY_SENTENCE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.GROUP_BY_TYPES;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LIMIT_SENTENCE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NAME_CHAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.OPERATORS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ORDER_BY_SENTENCE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.OR_SOQL_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.QUERY_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SELECT_SENTENCE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SIMPLE_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SOQL_EXTERNAL_VARIABLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SOQL_NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.WHERE_SENTENCE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.WITH_SENTENCE;

import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;

/**
 * This class contains constructors for SOQL rules and its sub rules.
 *
 */
public class SOQLExpressions {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        query(grammarBuilder);
        selectSentence(grammarBuilder);
        countMethod(grammarBuilder);
        typeField(grammarBuilder);
        soqlName(grammarBuilder);
        nameChar(grammarBuilder);
        fromSentence(grammarBuilder);
        aliasStatement(grammarBuilder);
        withSentence(grammarBuilder);
        whereSentence(grammarBuilder);
        fieldExpression(grammarBuilder);
        simpleExpression(grammarBuilder);
        filteringExpressions(grammarBuilder);
        operatorToComparisson(grammarBuilder);
        conditionalSOQLExpression(grammarBuilder);
        andSOQLExpression(grammarBuilder);
        orSOQLExpression(grammarBuilder);
        limitSentence(grammarBuilder);
        orderBySentence(grammarBuilder);
        groupBySentence(grammarBuilder);
        groupByTypesSentence(grammarBuilder);
    }

    /**
     * It is responsible for setting the rule for NAME_CHAR.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void nameChar(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(NAME_CHAR).is(
                grammarBuilder.firstOf(
                        IDENTIFIER,
                        DOT,
                        UNDERSCORE
                ));
    }

    /**
     * It is responsible for setting the rule for SOQL_NAME.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void soqlName(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(SOQL_NAME).is(
                grammarBuilder.firstOf(IDENTIFIER, ALLOWED_KEYWORDS_AS_SOBJECT_NAME),
                grammarBuilder.zeroOrMore(NAME_CHAR));
    }

    /**
     * It is responsible for setting the rule for query.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void query(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(QUERY_EXPRESSION).is(
                SELECT_SENTENCE,
                FROM_SENTENCE,
                grammarBuilder.optional(WITH_SENTENCE),
                grammarBuilder.optional(WHERE_SENTENCE),
                grammarBuilder.optional(ORDER_BY_SENTENCE),
                grammarBuilder.optional(GROUP_BY_SENTENCE),
                grammarBuilder.optional(LIMIT_SENTENCE));
    }

    /**
     * It is responsible for setting the rule for select sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void selectSentence(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(SELECT_SENTENCE).is(
                SELECT, grammarBuilder.firstOf(COUNT_EXPR, FIELD),
                grammarBuilder.zeroOrMore(
                        COMMA,
                        grammarBuilder.firstOf(COUNT_EXPR, FIELD)),
                grammarBuilder.zeroOrMore(COMMA, LPAREN, QUERY_EXPRESSION, RPAREN));
    }

    /**
     * It is responsible for setting the rule for COUNT().
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void countMethod(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(COUNT_EXPR).is(
                COUNT,
                LPAREN,
                grammarBuilder.optional(SOQL_NAME),
                RPAREN);
    }

    /**
     * It is responsible for setting the rules for field.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void typeField(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FIELD).is(SOQL_NAME);
    }

    /**
     * It is responsible for setting the rule for from sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void fromSentence(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FROM_SENTENCE).is(FROM,
                SOQL_NAME, grammarBuilder.optional(ALIASSTATEMENT),
                grammarBuilder.zeroOrMore(
                        COMMA, SOQL_NAME,
                        grammarBuilder.optional(ALIASSTATEMENT)
                ));
    }

    /**
     * It is responsible for setting the rule for optional 'as' sentence in
     * from.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void aliasStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ALIASSTATEMENT).is(AS, SOQL_NAME);
    }

    /**
     * It is responsible for setting the rule for with sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void withSentence(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(WITH_SENTENCE).is(
                WITH,
                SIMPLE_EXPRESSION);
    }

    /**
     * It is responsible for setting the rule for where sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void whereSentence(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(WHERE_SENTENCE).is(WHERE,
                SIMPLE_EXPRESSION,
                grammarBuilder.zeroOrMore(CONDITIONAL_SOQL_EXPRESSION));
    }

    /**
     * It is responsible for setting the rule for conditional expressions in
     * where sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void conditionalSOQLExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CONDITIONAL_SOQL_EXPRESSION).is(
                grammarBuilder.firstOf(
                        AND_SOQL_EXPRESSION,
                        OR_SOQL_EXPRESSION));
    }

    /**
     * It is responsible for setting the rule for operators in where sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void operatorToComparisson(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(OPERATORS).is(
                grammarBuilder.firstOf(
                        ASSIGN,
                        GT,
                        GEQUT,
                        LT,
                        LEQUT,
                        LIKE,
                        NOTEQUALS
                ));
    }

    /**
     * It is responsible for setting the rule for FIELD expression in where
     * sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void fieldExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FIELD_EXPRESSION).is(
                SOQL_NAME,
                OPERATORS,
                grammarBuilder.firstOf(
                        STRING,
                        INTEGER_LITERAL,
                        SOQL_EXTERNAL_VARIABLE,
                        NULL));

        grammarBuilder.rule(SOQL_EXTERNAL_VARIABLE).is(
                COLON,
                NAME);
    }

    /**
     * It is responsible for setting the rule for operators in where sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void filteringExpressions(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FILTERING_EXPRESSION).is(
                SOQL_NAME,
                grammarBuilder.optional(NOT_SOQL),
                grammarBuilder.firstOf(
                        IN,
                        INCLUDES,
                        EXCLUDES),
                LPAREN,
                grammarBuilder.firstOf(SOQL_NAME, QUERY_EXPRESSION),
                grammarBuilder.zeroOrMore(COMMA, SOQL_NAME),
                RPAREN
        );
    }

    /**
     * It is responsible for setting the rule for SIMPLE expression in where
     * sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void simpleExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(SIMPLE_EXPRESSION).is(
                grammarBuilder.firstOf(
                        FIELD_EXPRESSION,
                        FILTERING_EXPRESSION
                )
        );
    }

    /**
     * It is responsible for setting the rule for AND expression in where
     * sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void andSOQLExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(AND_SOQL_EXPRESSION).is(
                AND_SOQL, SIMPLE_EXPRESSION);
    }

    /**
     * It is responsible for setting the rule for OR expression in where
     * sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void orSOQLExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(OR_SOQL_EXPRESSION).is(
                OR_SOQL, SIMPLE_EXPRESSION);
    }

    /**
     * It is responsible for setting the rule for limit sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void limitSentence(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(LIMIT_SENTENCE).is(
                LIMIT,
                INTEGER_LITERAL,
                grammarBuilder.optional(OFFSET, INTEGER_LITERAL)
        );
    }

    /**
     * It is responsible for setting the rule for order by sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void orderBySentence(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ORDER_BY_SENTENCE).is(
                ORDER, BY,
                SOQL_NAME,
                grammarBuilder.optional(
                        grammarBuilder.firstOf(ASC, DES),
                        grammarBuilder.optional(
                                NULLS,
                                grammarBuilder.firstOf(FIRST, LAST))),
                grammarBuilder.zeroOrMore(
                        COMMA,
                        SOQL_NAME,
                        grammarBuilder.optional(
                                grammarBuilder.firstOf(ASC, DES),
                                grammarBuilder.optional(
                                        NULLS,
                                        grammarBuilder.firstOf(FIRST, LAST)))
                )
        );
    }

    /**
     * It is responsible for setting the rule for group by sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void groupBySentence(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(GROUP_BY_SENTENCE).is(
                GROUP, BY,
                grammarBuilder.firstOf(
                        SOQL_NAME,
                        GROUP_BY_TYPES
                )
        );
    }

    /**
     * It is responsible for setting the rule for group by types sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void groupByTypesSentence(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(GROUP_BY_TYPES).is(
                grammarBuilder.firstOf(CUBE, ROLLUP),
                LPAREN,
                SOQL_NAME,
                grammarBuilder.zeroOrMore(COMMA, SOQL_NAME),
                RPAREN
        );
    }
}
