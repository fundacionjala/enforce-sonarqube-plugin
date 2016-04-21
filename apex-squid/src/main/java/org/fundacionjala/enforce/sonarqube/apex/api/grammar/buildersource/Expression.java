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
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INSTANCEOF;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NEW;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NULL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SUPER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.THIS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.AND;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.ANDEQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.ASSIGN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COMMA;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.DIV;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.DIVEQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.DOT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.EQUALS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.EXCOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.EXCOREQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.GEQUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.GT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.GTGTEQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.GTGTGTEQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACKET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LEQUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LTLTEQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.MGTEQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.MINUS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.MINUSEQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.MLT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.MOD;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.MODEQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.NOTEQUALS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.OR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.OREQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.PLUS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.PLUSEQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.QUESTION;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACKET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.SEMICOLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.STAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.STAREQU;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.NUMERIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.STRING;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ADDITIVE_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.AND_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARGUMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARGUMENTSPI;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARGUMENTS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ASSIGNMENT_OPERATOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BRACKETS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CASTING_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONDITIONAL_AND_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONDITIONAL_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONDITIONAL_OR_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CREATING_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DEC;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EQUAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EQUALITY_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPRESSION_FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPRESSION_PI;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INC;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INCLUSIVE_OR_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INVOKE_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LITERAL_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NUMERIC_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NUMERIC_EXPRESSION_OPERATIONS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NUMERIC_EXPRESSION_OPERATIONS_SIMPLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TERMINAL_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TESTING_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXCLUSIVE_OR_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INSTANCE_OF_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MULTIPLICATIVE_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.PRE_DECREMENT_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.PRE_INCREMENT_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.RELATIONAL_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SHIFT_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE_PI;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.UNARY_EXPRESSION;

/**
 * This class contains constructors for Expression rules and its sub rules.
 *
 */
public class Expression {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        arguments(grammarBuilder);
        argumentsPI(grammarBuilder);
        argumentsList(grammarBuilder);
        testingExpressionEqual(grammarBuilder);
        testingExpression(grammarBuilder);
        creatingExpression(grammarBuilder);
        castingExpression(grammarBuilder);
        brackets(grammarBuilder);
        literalExpression(grammarBuilder);
        numericExpressionOperationsSimpleInc(grammarBuilder);
        numericExpressionOperationsSimpleDec(grammarBuilder);
        numericExpressionOperations(grammarBuilder);
        numericExpressionOperationsSimple(grammarBuilder);
        numericExpression(grammarBuilder);
        invokeExpression(grammarBuilder);
        terminalExpression(grammarBuilder);
        expression(grammarBuilder);
        expressionFinal(grammarBuilder);
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
    }

    /**
     * It is responsible for setting the rules for arguments.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void arguments(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ARGUMENTS).is(
                TERMINAL_EXPRESSION,
                grammarBuilder.zeroOrMore(
                        COMMA,
                        TERMINAL_EXPRESSION
                )
        );
    }

    /**
     * It is responsible for setting the rules for arguments.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void argumentsPI(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ARGUMENTSPI).is(
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
        grammarBuilder.rule(ARGUMENTS_LIST).is(
                grammarBuilder.firstOf(EXPRESSION, THIS),
                grammarBuilder.zeroOrMore(COMMA,
                        grammarBuilder.firstOf(EXPRESSION, THIS))
        );
    }

    /**
     * It is responsible for managing the rule of integers, strings, characters.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void literalExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(LITERAL_EXPRESSION).is(
                grammarBuilder.firstOf(
                        STRING,
                        NUMERIC)
        );
    }

    /**
     * It is responsible for creating the rules to make the casting of an
     * expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void castingExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CASTING_EXPRESSION).is(
                LPAREN,
                TYPE,
                RPAREN,
                TERMINAL_EXPRESSION
        );
    }

    /**
     * It is responsible for creating the rules to make the testing of an
     * expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void testingExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TESTING_EXPRESSION).is(
                TERMINAL_EXPRESSION,
                grammarBuilder.firstOf(EQUAL, GT, LT),
                TERMINAL_EXPRESSION
        );
    }

    /**
     * It is responsible for creating the rules to make the creating of an
     * expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void creatingExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CREATING_EXPRESSION).is(
                NEW,
                CLASS_NAME,
                LPAREN,
                grammarBuilder.optional(
                        IDENTIFIER,
                        grammarBuilder.zeroOrMore(
                                COMMA,
                                IDENTIFIER)),
                RPAREN
        );
    }

    /**
     * It is responsible for setting the rules for simple mathematical
     * operations.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpressionOperations(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(NUMERIC_EXPRESSION_OPERATIONS).is(
                TERMINAL_EXPRESSION,
                grammarBuilder.firstOf(
                        PLUS,
                        MINUS,
                        STAR,
                        DIV,
                        MOD
                ),
                TERMINAL_EXPRESSION
        );
    }

    /**
     * It is responsible for creating the rules to make one increment.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpressionOperationsSimpleInc(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(INC).is(PLUS, PLUS);
    }

    /**
     * It is responsible for creating the rules to make one decrement.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpressionOperationsSimpleDec(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DEC).is(MINUS, MINUS);
    }

    /**
     * It is responsible for creating the rules to make one increment or
     * decrement.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpressionOperationsSimple(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(NUMERIC_EXPRESSION_OPERATIONS_SIMPLE).is(
                TERMINAL_EXPRESSION,
                grammarBuilder.firstOf(INC, DEC)
        );
    }

    /**
     * It is responsible for creating the rules for numeric operations.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(NUMERIC_EXPRESSION).is(
                grammarBuilder.firstOf(
                        NUMERIC_EXPRESSION_OPERATIONS,
                        NUMERIC_EXPRESSION_OPERATIONS_SIMPLE
                )
        );
    }

    /**
     * It is responsible for setting the rules for invoke expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void invokeExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(INVOKE_EXPRESSION).is(
                IDENTIFIER,
                grammarBuilder.optional(
                        LPAREN,
                        grammarBuilder.optional(ARGUMENTS),
                        RPAREN),
                grammarBuilder.zeroOrMore(
                        DOT,
                        INVOKE_EXPRESSION)
        );
    }

    /**
     * It is responsible for setting the rules for terminal expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void terminalExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TERMINAL_EXPRESSION).is(
                grammarBuilder.firstOf(
                        INVOKE_EXPRESSION,
                        LITERAL_EXPRESSION,
                        NULL,
                        SUPER,
                        THIS
                )
        );
    }

    /**
     * It is responsible for creating a rule expression language.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void expression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXPRESSION).is(
                grammarBuilder.firstOf(
                        NUMERIC_EXPRESSION,
                        TESTING_EXPRESSION,
                        CREATING_EXPRESSION,
                        CASTING_EXPRESSION,
                        TERMINAL_EXPRESSION
                )
        );
    }

    /**
     * It is responsible for setting the rules for final expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void expressionFinal(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXPRESSION_FINAL).is(EXPRESSION, SEMICOLON);
    }

    /**
     * It is responsible for creating the rule for the brackets.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void brackets(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(BRACKETS).is(LBRACKET, RBRACKET);
    }

    /**
     * It is responsible for setting the rules for equal comparison.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    public static void testingExpressionEqual(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EQUAL).is(ASSIGN, ASSIGN);
    }

    public static void expressionPi(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXPRESSION_PI).is(
                CONDITIONAL_EXPRESSION,
                grammarBuilder.optional(
                        grammarBuilder.sequence(ASSIGNMENT_OPERATOR, EXPRESSION_PI))
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
        grammarBuilder.rule(CONDITIONAL_EXPRESSION).is(
                CONDITIONAL_OR_EXPRESSION,
                grammarBuilder.optional(
                        grammarBuilder.sequence(QUESTION,
                                EXPRESSION_PI,
                                COLON,
                                EXPRESSION_PI))
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
        grammarBuilder.rule(INSTANCE_OF_EXPRESSION).is(
                RELATIONAL_EXPRESSION,
                grammarBuilder.optional(
                        grammarBuilder.sequence(
                                INSTANCEOF,
                                TYPE_PI))
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
                                ADDITIVE_EXPRESSION))
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
                        TERMINAL_EXPRESSION)
        );
    }

    public static void preIncrementExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PRE_INCREMENT_EXPRESSION).is(
                PLUS, PLUS, TERMINAL_EXPRESSION
        );
    }

    public static void preDecrementExpression(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PRE_DECREMENT_EXPRESSION).is(
                MINUS, MINUS, TERMINAL_EXPRESSION
        );
    }
}
