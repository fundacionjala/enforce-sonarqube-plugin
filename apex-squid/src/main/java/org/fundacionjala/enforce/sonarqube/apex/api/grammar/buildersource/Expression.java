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
 * This class contains constructors for Expression rules and its sub rules.
 *
 */
public class Expression {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        argumentsPI(grammarBuilder);
        argumentsList(grammarBuilder);
        brackets(grammarBuilder);
        expressionPi(grammarBuilder);
        assignmentOperator(grammarBuilder);
        conditionalExpression(grammarBuilder);
        conditionalOrExpression(grammarBuilder);
        conditionalAndExpression(grammarBuilder);
        inclusiveOrExpression(grammarBuilder);
        exclusiveOrExpression(grammarBuilder);
        andExpression(grammarBuilder);
        equalityExpression(grammarBuilder);
        instanceOfExpression(grammarBuilder);
        relationalExpression(grammarBuilder);
        shiftExpression(grammarBuilder);
        additiveExpression(grammarBuilder);
        multiplicativeExpression(grammarBuilder);
        unaryExpression(grammarBuilder);
        preIncrementExpression(grammarBuilder);
        preDecrementExpression(grammarBuilder);
        unaryExpressionNotPlusMinus(grammarBuilder);
        castExpression(grammarBuilder);
        primaryExpression(grammarBuilder);
        primarySuffix(grammarBuilder);
        primaryPrefix(grammarBuilder);
        postfixExpression(grammarBuilder);
        allocationExpression(grammarBuilder);
        arrayDimsAndInits(grammarBuilder);
        arrayInitializer(grammarBuilder);
        variableInitializer(grammarBuilder);
        mapValues(grammarBuilder);
        compoundStatementExpression(grammarBuilder);
        statementExpression(grammarBuilder);
    }

    /**
     * It is responsible for setting the rules for arguments.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void argumentsPI(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ARGUMENTS).is(
                LPAREN,
                grammarBuilder.optional(ARGUMENTS_LIST),
                RPAREN
        );
    }

    /**
     * Creates a grammar rule for arguments list.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void argumentsList(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ARGUMENTS_LIST).is(grammarBuilder.firstOf(EXPRESSION, THIS),
                grammarBuilder.zeroOrMore(COMMA,
                        grammarBuilder.firstOf(EXPRESSION, THIS))
        );
    }

    /**
     * It is responsible for creating the rule for the brackets.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void brackets(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(BRACKETS).is(LBRACKET, RBRACKET);
    }

    public static void expressionPi(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXPRESSION).is(CONDITIONAL_EXPRESSION,
                grammarBuilder.optional(grammarBuilder.sequence(ASSIGNMENT_OPERATOR, EXPRESSION))
        );
    }

    public static void assignmentOperator(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ASSIGNMENT_OPERATOR).is(
                grammarBuilder.firstOf(
                        ASSIGN,
                        DIVEQU,
                        MINUSEQU,
                        PLUSEQU,
                        MODEQU,
                        STAREQU,
                        LTLTEQU,
                        GTGTEQU,
                        GTGTGTEQU,
                        ANDEQU,
                        OREQU,
                        EXCOREQU
                )
        );
    }

    public static void conditionalExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CONDITIONAL_EXPRESSION).is(CONDITIONAL_OR_EXPRESSION,
                grammarBuilder.optional(grammarBuilder.sequence(QUESTION,
                                EXPRESSION,
                                COLON,
                                EXPRESSION))
        );
    }

    public static void conditionalOrExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CONDITIONAL_OR_EXPRESSION).is(
                CONDITIONAL_AND_EXPRESSION,
                grammarBuilder.zeroOrMore(
                        grammarBuilder.sequence(
                                OR, OR,
                                CONDITIONAL_AND_EXPRESSION))
        );
    }

    public static void conditionalAndExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CONDITIONAL_AND_EXPRESSION).is(
                INCLUSIVE_OR_EXPRESSION,
                grammarBuilder.zeroOrMore(
                        grammarBuilder.sequence(
                                AND, AND,
                                INCLUSIVE_OR_EXPRESSION))
        );
    }

    public static void inclusiveOrExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(INCLUSIVE_OR_EXPRESSION).is(EXCLUSIVE_OR_EXPRESSION,
                grammarBuilder.zeroOrMore(grammarBuilder.sequence(OR,
                        EXCLUSIVE_OR_EXPRESSION))
        );
    }

    public static void exclusiveOrExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXCLUSIVE_OR_EXPRESSION).is(
                AND_EXPRESSION,
                grammarBuilder.zeroOrMore(
                        grammarBuilder.sequence(
                                EXCOR,
                                AND_EXPRESSION))
        );
    }

    public static void andExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(AND_EXPRESSION).is(
                EQUALITY_EXPRESSION,
                grammarBuilder.zeroOrMore(
                        grammarBuilder.sequence(
                                AND,
                                EQUALITY_EXPRESSION))
        );
    }

    public static void equalityExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EQUALITY_EXPRESSION).is(
                INSTANCE_OF_EXPRESSION,
                grammarBuilder.zeroOrMore(
                        grammarBuilder.sequence(
                                grammarBuilder.firstOf(EQUALS, NOTEQUALS),
                                INSTANCE_OF_EXPRESSION))
        );
    }

    public static void instanceOfExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(INSTANCE_OF_EXPRESSION).is(RELATIONAL_EXPRESSION,
                grammarBuilder.optional(grammarBuilder.sequence(INSTANCEOF,
                                TYPE))
        );
    }

    public static void relationalExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(RELATIONAL_EXPRESSION).is(
                SHIFT_EXPRESSION,
                grammarBuilder.zeroOrMore(
                        grammarBuilder.sequence(
                                grammarBuilder.firstOf(LT, GT, LEQUT, GEQUT),
                                SHIFT_EXPRESSION))
        );
    }

    public static void shiftExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(SHIFT_EXPRESSION).is(
                ADDITIVE_EXPRESSION,
                grammarBuilder.zeroOrMore(
                        grammarBuilder.sequence(
                                grammarBuilder.firstOf(MLT, MGTEQU),
                                ADDITIVE_EXPRESSION)
                )
        );
    }

    public static void additiveExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ADDITIVE_EXPRESSION).is(
                MULTIPLICATIVE_EXPRESSION,
                grammarBuilder.zeroOrMore(
                        grammarBuilder.firstOf(PLUS, MINUS),
                        MULTIPLICATIVE_EXPRESSION)
        );
    }

    public static void multiplicativeExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(MULTIPLICATIVE_EXPRESSION).is(
                UNARY_EXPRESSION,
                grammarBuilder.zeroOrMore(
                        grammarBuilder.firstOf(STAR, DIV, MOD),
                        UNARY_EXPRESSION)
        );
    }

    public static void unaryExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(UNARY_EXPRESSION).is(
                grammarBuilder.firstOf(
                        grammarBuilder.sequence(grammarBuilder.firstOf(PLUS, MINUS),
                                UNARY_EXPRESSION),
                        PRE_INCREMENT_EXPRESSION,
                        PRE_DECREMENT_EXPRESSION,
                        UNARY_EXPRESSION_NOT_PLUS_MINUS)
        );
    }

    public static void preIncrementExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PRE_INCREMENT_EXPRESSION).is(
                PLUS, PLUS, PRIMARY_EXPRESSION
        );
    }

    public static void preDecrementExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PRE_DECREMENT_EXPRESSION).is(
                MINUS, MINUS, PRIMARY_EXPRESSION
        );
    }

    public static void unaryExpressionNotPlusMinus(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(UNARY_EXPRESSION_NOT_PLUS_MINUS).is(
                grammarBuilder.firstOf(
                        grammarBuilder.sequence(NOT, UNARY_EXPRESSION),
                        CAST_EXPRESSION,
                        grammarBuilder.sequence(PRIMARY_EXPRESSION,
                                grammarBuilder.optional(POST_FIX_EXPRESSION)))
        );
    }

    public static void castExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CAST_EXPRESSION).is(grammarBuilder.firstOf(grammarBuilder.sequence(LPAREN, TYPE, RPAREN, UNARY_EXPRESSION),
                        grammarBuilder.sequence(LPAREN, TYPE, RPAREN, UNARY_EXPRESSION_NOT_PLUS_MINUS)
                )
        );
    }

    public static void primaryExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PRIMARY_EXPRESSION).is(
                PRIMARY_PREFIX,
                grammarBuilder.zeroOrMore(PRIMARY_SUFFIX)
        );
    }

    public static void primarySuffix(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PRIMARY_SUFFIX).is(grammarBuilder.firstOf(grammarBuilder.sequence(LBRACKET, EXPRESSION, RBRACKET),
                        grammarBuilder.sequence(DOT,
                                ALLOWED_KEYWORDS_AS_IDENTIFIER_FOR_METHODS),
                        ARGUMENTS)
        );
    }

    public static void primaryPrefix(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PRIMARY_PREFIX).is(grammarBuilder.firstOf(LITERAL,
                        grammarBuilder.sequence(RESULT_TYPE, DOT, CLASS),
                        NAME,
                        grammarBuilder.sequence(
                                grammarBuilder.firstOf(SUPER, THIS, SIMPLE_TYPE),
                                DOT,
                                NAME
                        ),
                        grammarBuilder.sequence(LPAREN, EXPRESSION, RPAREN),
                        ALLOCATION_EXPRESSION,
                        grammarBuilder.sequence(LBRACKET, QUERY_EXPRESSION, RBRACKET)
                )
        );
    }

    public static void postfixExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(POST_FIX_EXPRESSION).is(
                grammarBuilder.firstOf(
                        grammarBuilder.sequence(PLUS, PLUS),
                        grammarBuilder.sequence(MINUS, MINUS)
                )
        );
    }

    public static void allocationExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ALLOCATION_EXPRESSION).is(NEW, grammarBuilder.firstOf(grammarBuilder.sequence(CLASS_OR_INTERFACE_TYPE,
                                grammarBuilder.firstOf(ARRAY_DIMS_AND_INITS,
                                        grammarBuilder.sequence(ARGUMENTS,
                                                grammarBuilder.optional(LBRACE, RBRACE)))),
                        grammarBuilder.sequence(grammarBuilder.firstOf(LIST, SET, ITERATOR),
                                GENERIC_TYPE,
                                grammarBuilder.firstOf(ARGUMENTS,
                                        grammarBuilder.sequence(
                                                LBRACE,
                                                grammarBuilder.optional(ARGUMENTS_LIST),
                                                RBRACE))),
                        grammarBuilder.sequence(MAP, GENERIC_TYPE,
                                grammarBuilder.firstOf(ARGUMENTS,
                                        grammarBuilder.sequence(LBRACE,
                                                grammarBuilder.optional(MAP_VALUES),
                                                RBRACE)
                                )
                        )
                )
        );
    }

    public static void arrayDimsAndInits(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ARRAY_DIMS_AND_INITS).is(grammarBuilder.firstOf(grammarBuilder.sequence(LBRACKET, EXPRESSION, RBRACKET),
                        grammarBuilder.sequence(BRACKETS, LBRACE, ARRAY_INITIALIZER, RBRACE)
                )
        );
    }

    public static void arrayInitializer(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ARRAY_INITIALIZER).is(
                grammarBuilder.optional(
                        VARIABLE_INITIALIZER,
                        grammarBuilder.zeroOrMore(COMMA, VARIABLE_INITIALIZER)
                )
        );
    }

    public static void variableInitializer(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(VARIABLE_INITIALIZER).is(
                grammarBuilder.firstOf(
                        EXPRESSION,
                        THIS
                )
        );
    }

    public static void mapValues(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(MAP_VALUES).is(grammarBuilder.sequence(EXPRESSION,
                MAP_ASSIGN,
                EXPRESSION),
                grammarBuilder.zeroOrMore(grammarBuilder.sequence(COMMA,
                        EXPRESSION,
                        MAP_ASSIGN,
                        EXPRESSION))
        );
    }

    public static void compoundStatementExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(COMPOUND_STATEMENT_EXPRESSION).is(
                STATEMENT_EXPRESSION,
                grammarBuilder.firstOf(BLOCK, SEMICOLON)
        );
    }

    public static void statementExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STATEMENT_EXPRESSION).is(grammarBuilder.firstOf(PRE_INCREMENT_EXPRESSION,
                        PRE_DECREMENT_EXPRESSION,
                        grammarBuilder.sequence(PRIMARY_EXPRESSION,
                                grammarBuilder.zeroOrMore(grammarBuilder.firstOf(grammarBuilder.sequence(PLUS, PLUS),
                                                grammarBuilder.sequence(MINUS, MINUS),
                                                grammarBuilder.sequence(ASSIGNMENT_OPERATOR,
                                                        grammarBuilder.firstOf(EXPRESSION, THIS))
                                        )
                                )
                        )
                )
        );
    }
}
