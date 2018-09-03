/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.api.grammar.buildersource;

import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.*;
//import static org.fundacionjala.enforce.sonarqube.apex.api.SOQLKeyword.*;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.*;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.*;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.*;

import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;

/**
 * This class contains constructors for SOQL rules and its sub rules.
 *
 */
public class SOQLExpressions {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        query(grammarBuilder);
        selectSentence(grammarBuilder);
        aggregateMethod(grammarBuilder);
        dateMethods(grammarBuilder);
        dateLiteralsWithInteger(grammarBuilder);
        dateLiterals(grammarBuilder);
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
        soqlExternalVariable(grammarBuilder);
        havingSentence(grammarBuilder);
        whereSentenceExpression(grammarBuilder);
        //soqlStringSet(grammarBuilder);
        //soqlFilterExpressionSet(grammarBuilder);
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
                grammarBuilder.optional(GROUP_BY_SENTENCE),
                grammarBuilder.optional(HAVING_SENTENCE),
                grammarBuilder.optional(ORDER_BY_SENTENCE),
                grammarBuilder.optional(LIMIT_SENTENCE),
        		grammarBuilder.optional(FOR, UPDATE)
                );
    }

    /**
     * It is responsible for setting the rule for select sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void selectSentence(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(SELECT_SENTENCE).is(
                SELECT, grammarBuilder.firstOf(AGGREGATE_EXPR, FIELD, DATE_METHOD_EXPR),
                grammarBuilder.zeroOrMore(
                        COMMA,
                        grammarBuilder.firstOf(AGGREGATE_EXPR, FIELD, DATE_METHOD_EXPR)),
                grammarBuilder.zeroOrMore(COMMA, LPAREN, QUERY_EXPRESSION, RPAREN));
    }

    /**
     * It is responsible for setting the rule for all type of Aggregate Methods.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void aggregateMethod(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(AGGREGATE_EXPR).is(
        		grammarBuilder.firstOf(COUNT, AVG, MAX, MIN, SUM, COUNT_DISTINCT),
                LPAREN,
                grammarBuilder.optional(SOQL_NAME),
                RPAREN,
                grammarBuilder.optional(SOQL_NAME));
    }

    /**
     * It is responsible for setting the rule for All Date Methods available for SOQL Query.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void dateMethods(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DATE_METHOD_EXPR).is(
        		grammarBuilder.firstOf(
        				CALENDAR_MONTH,
        				CALENDAR_QUARTER,
        				CALENDAR_YEAR,
        				DAY_IN_MONTH,
        				DAY_IN_WEEK,
        				DAY_IN_YEAR,
        				DAY_ONLY,
        				FISCAL_MONTH,
        				FISCAL_QUARTER,
        				FISCAL_YEAR,
        				HOUR_IN_DAY,
        				WEEK_IN_MONTH,
        				WEEK_IN_YEAR),
                LPAREN,
                grammarBuilder.optional(SOQL_NAME, STRING),
                RPAREN,
                grammarBuilder.optional(SOQL_NAME));
    }

    /**
     * It is responsible for setting the rule for All Date Literals with integer values available for SOQL Query.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void dateLiteralsWithInteger(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DATE_LITERALS_WITH_NUMBERS_EXPR).is(
        		grammarBuilder.firstOf(
        				NEXT_N_FISCAL_YEARS,
        				LAST_N_FISCAL_YEARS,
        				NEXT_N_FISCAL_QUARTERS,
        				LAST_N_FISCAL_QUARTERS,
        				LAST_N_DAYS,
        				NEXT_N_DAYS,
        				NEXT_N_WEEKS,
        				LAST_N_WEEKS,
        				NEXT_N_MONTHS,
        				LAST_N_MONTHS,
        				NEXT_N_QUARTERS,
        				LAST_N_QUARTERS,
        				NEXT_N_YEARS,
        				LAST_N_YEARS),
                COLON,
                grammarBuilder.firstOf(SOQL_NAME, INTEGER_LITERAL));
    }

    /**
     * It is responsible for setting the rule for All Date Literals available for SOQL Query.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void dateLiterals(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DATE_LITERALS_EXPR).is(
        		grammarBuilder.firstOf(
        				YESTERDAY,
        				TODAY,
        				TOMORROW,
        				LAST_WEEK,
        				THIS_WEEK,
        				NEXT_WEEK,
        				LAST_MONTH,
        				THIS_MONTH,
        				NEXT_MONTH,
        				THIS_QUARTER,
        				LAST_QUARTER,
        				NEXT_QUARTER,
        				THIS_YEAR,
        				LAST_YEAR,
        				NEXT_YEAR,
        				THIS_FISCAL_QUARTER,
        				NEXT_FISCAL_QUARTER,
        				LAST_FISCAL_QUARTER,
        				LAST_90_DAYS,
        				NEXT_90_DAYS,
        				THIS_FISCAL_YEAR,
        				LAST_FISCAL_YEAR,
        				NEXT_FISCAL_YEAR));
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
        		grammarBuilder.firstOf(
                SOQL_NAME, GROUP)
        		, grammarBuilder.optional(ALIASSTATEMENT));
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
        grammarBuilder.rule(WHERE_SENTENCE).is(
        		WHERE,
        		WHERE_SENTENCE_EXPRESSION
    		);
    }

    /**
     * It is responsible for setting the rule for where sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void whereSentenceExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(WHERE_SENTENCE_EXPRESSION).is(
        		grammarBuilder.firstOf(
	        		grammarBuilder.sequence(
	    				LPAREN,
		                SIMPLE_EXPRESSION,
		                grammarBuilder.zeroOrMore(CONDITIONAL_SOQL_EXPRESSION),
		                RPAREN,
		                grammarBuilder.zeroOrMore(CONDITIONAL_SOQL_EXPRESSION)
                	),
	        		grammarBuilder.sequence(
        				SIMPLE_EXPRESSION,
                        grammarBuilder.zeroOrMore(CONDITIONAL_SOQL_EXPRESSION)
    				)
        		)
    		);
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
                        OR_SOQL_EXPRESSION
                        ));
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
                        NOTEQUALS,
                        CLS_NOTEQUALS,
                        IN
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
        		//grammarBuilder.optional(NOT_SOQL),
        		grammarBuilder.firstOf(SOQL_NAME, DATE_METHOD_EXPR, AGGREGATE_EXPR),
                grammarBuilder.optional(NOT_SOQL),
                OPERATORS,
                grammarBuilder.firstOf(
                        STRING,
                        INTEGER_LITERAL,
                        SOQL_EXTERNAL_VARIABLE,
                        BOOLEAN_LITERAL,
                        NULL,
                        DATE_LITERALS_EXPR,
                        DATE_LITERALS_WITH_NUMBERS_EXPR));
    }

    /**
     * It is responsible for setting the rule for SOQL External Variable in where
     * sentence. External Variable can be any variable, method like:
     * Where id =:userId
     * Where id =:getId()
     * Where id =: userInfo.GetUserId()
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void soqlExternalVariable(LexerfulGrammarBuilder grammarBuilder) {
    	grammarBuilder.rule(SOQL_EXTERNAL_VARIABLE).is(
                COLON,
                grammarBuilder.firstOf(NAME, SOQL_NAME),
                grammarBuilder.optional(LPAREN, grammarBuilder.optional(grammarBuilder.firstOf(STRING, INTEGER_LITERAL)), RPAREN),
                grammarBuilder.optional(LBRACKET, grammarBuilder.firstOf(SOQL_NAME, INTEGER_LITERAL), RBRACKET),
                grammarBuilder.zeroOrMore(
                        DOT,
                        SOQL_NAME,
                        grammarBuilder.optional(LPAREN, grammarBuilder.optional(grammarBuilder.firstOf(STRING, INTEGER_LITERAL)), RPAREN)
                        )
                );
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
                grammarBuilder.firstOf(SOQL_NAME, QUERY_EXPRESSION, STRING),
                grammarBuilder.zeroOrMore(COMMA,
                		grammarBuilder.firstOf(SOQL_NAME, STRING)),
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
                AND_SOQL, WHERE_SENTENCE_EXPRESSION);
    }

    /**
     * It is responsible for setting the rule for OR expression in where
     * sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void orSOQLExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(OR_SOQL_EXPRESSION).is(
                OR_SOQL, WHERE_SENTENCE_EXPRESSION);
    }

    /**
     * It is responsible for setting the rule for limit sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void limitSentence(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(LIMIT_SENTENCE).is(
                LIMIT,
                grammarBuilder.optional(COLON),
                grammarBuilder.firstOf(SOQL_NAME, INTEGER_LITERAL),
                grammarBuilder.optional(LPAREN, RPAREN),
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
                //SOQL_NAME,
                grammarBuilder.firstOf(
                		SOQL_NAME,
                		AGGREGATE_EXPR),
                grammarBuilder.optional(
                        grammarBuilder.firstOf(ASC, DES),
                        grammarBuilder.optional(
                                NULLS,
                                grammarBuilder.firstOf(FIRST, LAST))),
                grammarBuilder.zeroOrMore(
                        COMMA,
                        //SOQL_NAME,
                        grammarBuilder.firstOf(
                        		SOQL_NAME,
                        		AGGREGATE_EXPR),
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
                        GROUP_BY_TYPES,
                        DATE_METHOD_EXPR,
                        AGGREGATE_EXPR
                ),
                grammarBuilder.zeroOrMore(COMMA,
                		grammarBuilder.firstOf(
                                SOQL_NAME,
                                GROUP_BY_TYPES,
                                DATE_METHOD_EXPR,
                                AGGREGATE_EXPR
                        ))
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

    /**
     * It is responsible for setting the rule for where sentence.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void havingSentence(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(HAVING_SENTENCE).is(HAVING,
        		//grammarBuilder.optional(LPAREN),
                SIMPLE_EXPRESSION,
                grammarBuilder.zeroOrMore(CONDITIONAL_SOQL_EXPRESSION)//,
                //grammarBuilder.optional(RPAREN)
                );
    }
}
