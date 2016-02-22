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
package org.fundacionjala.enforce.sonarqube.apex.api;

import com.sonar.sslr.api.Grammar;

import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ABSTRACT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ANOTATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BOOLEAN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BYTE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CATCH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CHAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CLASS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DOUBLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ELSE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.EXTENDS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FLOAT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IF;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IMPLEMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INTERFACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LONG;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NATIVE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NULL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PRIVATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PROTECTED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PUBLIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHARING;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHORT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STATIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STRICTFP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SUPER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SYNCHRONIZED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.THIS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRANSIENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VOLATILE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WHILE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITHOUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.ASSIGN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COMMA;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.DIV;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.GT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACKET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.MINUS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.MOD;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.PLUS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.QUOTE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.QUOTES;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACKET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.SEMICOLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.STAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.CHARACTER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.NUMERIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.APEX_GRAMMAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.ASSIGN_VARIABLE_INITILIZER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.BRACKETS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CASTING_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CHARACTER_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXTENDS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.IMPLEMENTS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.KEYWORD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.METHOD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.METHOD_NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_CLASS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.VARIABLE_DECLARATOR_ID;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.MODIFIER_KEYWORD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.MODIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.FIELD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.INTEGER_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.LITERAL_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STRING_LITERAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_SPECIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.VARIABLE_DECLARATOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.VARIABLE_INITILIZER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.NUMERIC_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.NUMERIC_EXPRESSION_OPERATIONS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.NUMERIC_EXPRESSION_OPERATIONS_SIMPLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.INC;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.DEC;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EQUAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXPRESSION_FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.FOR_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.FOR_STATEMENT_INITIALIZER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.PARAMETER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.PARAMETER_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.PARAMETER_LIST_OPTIONAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT_BLOCK;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT_ELSE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT_IF;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STRING_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TESTING_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TRY_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.VARIABLE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WHILE_STATEMENT;

/**
 * This class unites all the rules you need a class.
 */
public class ApexGrammar {

    /**
     * It is the main method of grammar. Here all other grammars are
     * constructed.
     *
     * @return The grammar of a class.
     */
    public static Grammar create() {
        return create(Boolean.TRUE);
    }

    /**
     * Creates a grammar from {@link ApexGrammarBuilder}. It's required a
     * boolean to indicate the type of grammar builder. Only available for unit
     * test.
     *
     * @param isFulGrammar represents the type of grammar builder required.
     * @return the grammar
     */
    static Grammar create(boolean isFulGrammar) {
        ApexGrammarBuilder grammarBuilder = ApexGrammarBuilder.create(isFulGrammar);
        expression(grammarBuilder);
        expressionFinal(grammarBuilder);
        statement(grammarBuilder);
        whileStatement(grammarBuilder);
        forStatementInitializer(grammarBuilder);
        tryStatement(grammarBuilder);
        forStatement(grammarBuilder);
        statementBlock(grammarBuilder);
        statementIf(grammarBuilder);
        statamentElse(grammarBuilder);
        variableDeclaration(grammarBuilder);
        stringExpression(grammarBuilder);
        testingExpressionEqual(grammarBuilder);
        testingExpression(grammarBuilder);
        castingExpression(grammarBuilder);
        brackets(grammarBuilder);
        characterLiteral(grammarBuilder);
        stringLiteral(grammarBuilder);
        integerLiteral(grammarBuilder);
        literalExpression(grammarBuilder);
        numericExpressionOperationsSimpleInc(grammarBuilder);
        numericExpressionOperationsSimpleDec(grammarBuilder);
        numericExpressionOperations(grammarBuilder);
        numericExpressionOperationsSimple(grammarBuilder);
        numericExpression(grammarBuilder);
        assignVariableInitializer(grammarBuilder);
        variableInitializer(grammarBuilder);
        variableDeclaratorId(grammarBuilder);
        variableDeclarator(grammarBuilder);
        typeSpecifier(grammarBuilder);
        type(grammarBuilder);
        parameter(grammarBuilder);
        parameterListOptional(grammarBuilder);
        parameterList(grammarBuilder);
        methodName(grammarBuilder);
        methodDeclaration(grammarBuilder);
        typeClass(grammarBuilder);
        extendsList(grammarBuilder);
        implementsList(grammarBuilder);
        className(grammarBuilder);
        classDeclaration(grammarBuilder);
        keyword(grammarBuilder);
        modifier(grammarBuilder);
        fieldDeclaration(grammarBuilder);
        modifierKeyWord(grammarBuilder);
        typeDeclaration(grammarBuilder);

        grammarBuilder.rule(APEX_GRAMMAR).is(TYPE_DECLARATION);
        grammarBuilder.setRootRule(APEX_GRAMMAR);
        Grammar grammar = grammarBuilder.build();
        return grammar;
    }

    /**
     * Grammar is created for the head of a class with the switch and whether it
     * will extend or implement otherwise.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void typeDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE_DECLARATION).is(
                CLASS_DECLARATION
        );
    }

    /**
     * Grammar is created for a class modifier (lookahead or keyword).
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void modifierKeyWord(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(MODIFIER_KEYWORD).is(
                grammarBuilder.optional(MODIFIER),
                grammarBuilder.optional(KEYWORD)
        );
    }

    /**
     * Grammar for different access modifiers of a class is created.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void modifier(ApexGrammarBuilder grammarBuilder) {
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
    private static void keyword(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(KEYWORD).is(
                grammarBuilder.firstOf(
                        WITHOUT,
                        WITH),
                SHARING
        );
    }

    /**
     * It is responsible for managing the class name.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void className(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CLASS_NAME).is(IDENTIFIER);
    }

    /**
     * Grammar for the declaration of a class or interface is constructed.
     * Composed of the rules of a class type, its identified, extends, and
     * implements.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void classDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CLASS_DECLARATION).is(MODIFIER_KEYWORD,
                TYPE_CLASS,
                CLASS_NAME,
                grammarBuilder.optional(IMPLEMENTS_LIST),
                grammarBuilder.optional(EXTENDS_LIST),
                LBRACE,
                grammarBuilder.optional(FIELD_DECLARATION),
                RBRACE
        );
    }

    /**
     * Grammar is created to implement another class or not.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void implementsList(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(IMPLEMENTS_LIST).is(
                IMPLEMENTS,
                CLASS_NAME
        );
    }

    /**
     * Grammar is created to extend another class or just skip.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void extendsList(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXTENDS_LIST).is(
                EXTENDS,
                CLASS_NAME
        );
    }

    /**
     * Grammar is created to identify if a class or interface.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void typeClass(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE_CLASS).is(
                grammarBuilder.firstOf(CLASS,
                        INTERFACE)
        );
    }

    /**
     * The grammar of the empty body of a class is built.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void fieldDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FIELD_DECLARATION).is(
                grammarBuilder.optional(VARIABLE_DECLARATION),
                grammarBuilder.optional(METHOD_DECLARATION)
        );
    }

    /**
     * It is responsible for setting the rules for parameters.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void parameter(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PARAMETER).is(
                TYPE,
                IDENTIFIER,
                grammarBuilder.optional(BRACKETS)
        );

    }

    /**
     * It is responsible for setting the rules for the optional argument list.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void parameterListOptional(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PARAMETER_LIST_OPTIONAL).is(COMMA, PARAMETER);
    }

    /**
     * It is responsible for setting the rules for the argument list.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void parameterList(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PARAMETER_LIST).is(
                PARAMETER,
                grammarBuilder.optional(PARAMETER_LIST_OPTIONAL)
        );
    }

    /**
     * It is responsible for managing the method name.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void methodName(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(METHOD_NAME).is(IDENTIFIER);
    }

    /**
     * Creates rules to the last line of the method and the completion of the
     * method.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void methodDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(METHOD_DECLARATION).is(
                MODIFIER,
                TYPE,
                METHOD_NAME,
                LPAREN,
                grammarBuilder.optional(PARAMETER_LIST),
                RPAREN
        );
    }

    /**
     * Creates rules for the primitive values of a class.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void typeSpecifier(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE_SPECIFIER).is(
                grammarBuilder.firstOf(BOOLEAN,
                        BOOLEAN,
                        CHAR,
                        BYTE,
                        SHORT,
                        INT,
                        LONG,
                        FLOAT,
                        DOUBLE,
                        CLASS_NAME
                )
        );
    }

    /**
     * It is responsible for creating the rule for merge with symbol '=' and an
     * expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void assignVariableInitializer(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ASSIGN_VARIABLE_INITILIZER).is(
                ASSIGN,
                EXPRESSION
        );
    }

    /**
     * It is responsible for creating the rule for initializing a variable
     * worth.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void variableInitializer(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(VARIABLE_INITILIZER).is(EXPRESSION);
    }

    /**
     * It is responsible for managing the rule of integers.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void integerLiteral(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(INTEGER_LITERAL).is(NUMERIC);
    }

    /**
     * It is responsible for managing the rule of characters.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void characterLiteral(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CHARACTER_LITERAL).is(QUOTE, CHARACTER, QUOTE);
    }

    /**
     * It is responsible for managing the rule of string.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void stringLiteral(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STRING_LITERAL).is(
                QUOTES,
                CHARACTER,
                grammarBuilder.zeroOrMore(CHARACTER),
                QUOTES
        );
    }

    /**
     * It is responsible for managing the rule of integers, strings, characters.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void literalExpression(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(LITERAL_EXPRESSION).is(
                grammarBuilder.firstOf(
                        INTEGER_LITERAL,
                        CHARACTER_LITERAL,
                        STRING_LITERAL)
        );
    }

    /**
     * It is responsible for creating the rules to make the casting of an
     * expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void castingExpression(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CASTING_EXPRESSION).is(
                LPAREN,
                TYPE,
                RPAREN,
                EXPRESSION
        );
    }

    /**
     * It is responsible for setting the rules for equal comparison.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    public static void testingExpressionEqual(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EQUAL).is(ASSIGN, ASSIGN);
    }

    /**
     * It is responsible for creating the rules to make the testing of an
     * expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void testingExpression(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TESTING_EXPRESSION).is(
                EXPRESSION,
                grammarBuilder.firstOf(EQUAL, GT, LT),
                EXPRESSION
        );
    }

    /**
     * It is responsible for setting the rules for simple mathematical
     * operations.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpressionOperations(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(NUMERIC_EXPRESSION_OPERATIONS).is(
                EXPRESSION,
                grammarBuilder.firstOf(
                        PLUS,
                        MINUS,
                        STAR,
                        DIV,
                        MOD
                ),
                EXPRESSION
        );
    }

    /**
     * It is responsible for creating the rules to make one increment.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpressionOperationsSimpleInc(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(INC).is(PLUS, PLUS);
    }

    /**
     * It is responsible for creating the rules to make one decrement.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpressionOperationsSimpleDec(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DEC).is(MINUS, MINUS);
    }

    /**
     * It is responsible for creating the rules to make one increment or
     * decrement.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpressionOperationsSimple(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(NUMERIC_EXPRESSION_OPERATIONS_SIMPLE).is(EXPRESSION,
                grammarBuilder.firstOf(INC, DEC)
        );
    }

    /**
     * It is responsible for creating the rules for numeric operations.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpression(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(NUMERIC_EXPRESSION).is(
                grammarBuilder.firstOf(
                        NUMERIC_EXPRESSION_OPERATIONS_SIMPLE,
                        NUMERIC_EXPRESSION_OPERATIONS
                )
        );
    }

    /**
     * It is responsible for setting the rules for strings.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void stringExpression(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STRING_EXPRESSION).is(EXPRESSION);
    }

    /**
     * It is responsible for creating a rule expression language.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void expression(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXPRESSION).is(
                grammarBuilder.firstOf(
                        IDENTIFIER,
                        LITERAL_EXPRESSION,
                        CASTING_EXPRESSION,
                        NUMERIC_EXPRESSION,
                        NULL,
                        SUPER,
                        THIS,
                        TESTING_EXPRESSION,
                        STRING_EXPRESSION
                )
        );
    }

    /**
     * It is responsible for setting the rules for final expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void expressionFinal(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXPRESSION_FINAL).is(EXPRESSION, SEMICOLON);
    }

    /**
     * It is responsible for setting the rules for the else.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void statamentElse(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STATEMENT_ELSE).is(ELSE, STATEMENT);
    }

    /**
     * It is responsible for setting the rules for the if else.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void statementIf(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STATEMENT_IF).is(
                IF,
                LPAREN,
                EXPRESSION,
                RPAREN,
                STATEMENT,
                grammarBuilder.optional(STATEMENT_ELSE)
        );
    }

    /**
     * It is responsible for setting the rules for a block of statements.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void statementBlock(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STATEMENT_BLOCK).is(
                LBRACE,
                grammarBuilder.zeroOrMore(STATEMENT),
                RBRACE);
    }

    /**
     * It is responsible for creating the rules for a while.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void whileStatement(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(WHILE_STATEMENT).is(
                WHILE,
                LPAREN,
                EXPRESSION,
                RPAREN,
                STATEMENT
        );
    }

    /**
     * It is responsible for setting the rules for initializing a for.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void forStatementInitializer(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FOR_STATEMENT_INITIALIZER).is(
                grammarBuilder.firstOf(
                        VARIABLE_DECLARATION,
                        EXPRESSION_FINAL,
                        SEMICOLON
                )
        );
    }

    /**
     * It is responsible for creating the rules for a while.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void forStatement(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FOR_STATEMENT).is(
                FOR,
                LPAREN,
                FOR_STATEMENT_INITIALIZER,
                grammarBuilder.optional(EXPRESSION),
                SEMICOLON,
                EXPRESSION,
                SEMICOLON,
                RPAREN,
                STATEMENT
        );
    }

    /**
     * It is responsible for creating the rules for a try catch.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void tryStatement(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TRY_STATEMENT).is(
                TRY,
                STATEMENT,
                CATCH,
                LPAREN,
                PARAMETER_LIST,
                RPAREN,
                STATEMENT
        );
    }

    /**
     * It is responsible for setting the rules for the all statements.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void statement(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STATEMENT).is(
                grammarBuilder.optional(STATEMENT_IF),
                grammarBuilder.optional(EXPRESSION_FINAL),
                grammarBuilder.optional(STATEMENT_BLOCK),
                grammarBuilder.optional(VARIABLE_DECLARATION),
                grammarBuilder.optional(WHILE_STATEMENT),
                grammarBuilder.optional(FOR_STATEMENT),
                grammarBuilder.optional(TRY_STATEMENT)
        );
    }

    /**
     * It is responsible for setting the rules for the declaration of a
     * variable.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void variableDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(VARIABLE_DECLARATION).is(
                grammarBuilder.optional(MODIFIER),
                TYPE,
                VARIABLE_DECLARATOR,
                SEMICOLON
        );
    }

    /**
     * It is responsible for creating the rule for declaring a variable.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void variableDeclarator(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(VARIABLE_DECLARATOR).is(
                VARIABLE_DECLARATOR_ID,
                grammarBuilder.optional(ASSIGN_VARIABLE_INITILIZER)
        );
    }

    /* It is responsible for creating the rule for identifying a variable.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void variableDeclaratorId(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(VARIABLE_DECLARATOR_ID).is(
                IDENTIFIER,
                grammarBuilder.optional(BRACKETS)
        );
    }

    /**
     * It is responsible for creating the rule for the brackets.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void brackets(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(BRACKETS).is(LBRACKET, RBRACKET);
    }

    /**
     * It is responsible for building the rules for the different types of
     * return of a method.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void type(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE).is(TYPE_SPECIFIER);
    }
}
