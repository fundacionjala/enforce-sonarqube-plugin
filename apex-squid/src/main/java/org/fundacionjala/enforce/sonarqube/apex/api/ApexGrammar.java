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
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.AURA_ENABLED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BOOLEAN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BYTE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CATCH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CHAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CLASS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DELETE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DEPRECATED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DOUBLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ELSE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.EXTENDS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FLOAT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FUTURE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IF;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IMPLEMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INSERT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INTERFACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INVOCABLE_METHOD;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INVOCABLE_VARIABLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IS_TEST;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LONG;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.MERGE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NATIVE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NEW;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NULL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PRIVATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PROTECTED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PUBLIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.READ_ONLY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.REMOTE_ACTION;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.RETURN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHARING;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHORT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STATIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STRICTFP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SUPER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SYNCHRONIZED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TEST_SETUP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TEST_VISIBLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.THIS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRANSIENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UNDELETE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UPDATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UPSERT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VOID;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VOLATILE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WHILE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITHOUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.ASSIGN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.AT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COMMA;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.DIV;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.DOT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.GT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACKET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.MINUS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.MOD;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.PLUS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACKET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.SEMICOLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.STAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.NUMERIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType.STRING;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.ANNOTATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.APEX_GRAMMAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.ASSIGN_VARIABLE_INITILIZER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.BRACKETS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CASTING_EXPRESSION;
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
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CONSTRUCTOR_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CREATING_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.FIELD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.LITERAL_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_SPECIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.VARIABLE_DECLARATOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.NUMERIC_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.NUMERIC_EXPRESSION_OPERATIONS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.NUMERIC_EXPRESSION_OPERATIONS_SIMPLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.INC;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.DEC;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.DML_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EQUAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXPRESSION_FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.FOR_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.PARAMETER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.PARAMETER_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.RETURN_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT_BLOCK;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT_ELSE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT_IF;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TERMINAL_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TERMINAL_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TESTING_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TRY_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.VARIABLE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WHILE_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.INVOKE_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.ARGUMENTS;

/**
 * This class unites all the rules you need a class.
 */
public class ApexGrammar {

    /**
     * It is the main method of grammar. Here all other grammars are constructed.
     *
     * @return The grammar of a class.
     */
    public static Grammar create() {
        return create(Boolean.TRUE);
    }

    /**
     * Creates a grammar from {@link ApexGrammarBuilder}. It's required a boolean to indicate the
     * type of grammar builder. Only available for unit test.
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
        tryStatement(grammarBuilder);
        forStatement(grammarBuilder);
        returnStatement(grammarBuilder);
        dmlStatement(grammarBuilder);
        statementBlock(grammarBuilder);
        statementIf(grammarBuilder);
        statamentElse(grammarBuilder);
        variableDeclaration(grammarBuilder);
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
        assignVariableInitializer(grammarBuilder);
        variableDeclaratorId(grammarBuilder);
        variableDeclarator(grammarBuilder);
        typeSpecifier(grammarBuilder);
        type(grammarBuilder);
        annotation(grammarBuilder);
        arguments(grammarBuilder);
        parameter(grammarBuilder);
        parameterList(grammarBuilder);
        methodName(grammarBuilder);
        methodDeclaration(grammarBuilder);
        constructorDeclaration(grammarBuilder);
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
     * Grammar is created for the head of a class with the switch and whether it will extend or
     * implement otherwise.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void typeDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE_DECLARATION).is(
                grammarBuilder.oneOrMore(CLASS_DECLARATION)
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
     * Grammar to identify a sharing rule keyword.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void annotation(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ANNOTATION).is(
                AT,
                grammarBuilder.firstOf(
                        AURA_ENABLED,
                        DEPRECATED,
                        FUTURE,
                        INVOCABLE_METHOD,
                        INVOCABLE_VARIABLE,
                        IS_TEST,
                        READ_ONLY,
                        REMOTE_ACTION,
                        TEST_SETUP,
                        TEST_VISIBLE
                )
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
     * Grammar for the declaration of a class or interface is constructed. Composed of the rules of
     * a class type, its identified, extends, and implements.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void classDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CLASS_DECLARATION).is(
                grammarBuilder.zeroOrMore(ANNOTATION),
                MODIFIER_KEYWORD,
                TYPE_CLASS,
                CLASS_NAME,
                grammarBuilder.optional(IMPLEMENTS_LIST),
                grammarBuilder.optional(EXTENDS_LIST),
                LBRACE,
                grammarBuilder.zeroOrMore(FIELD_DECLARATION),
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
                grammarBuilder.firstOf(
                        CLASS,
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
                grammarBuilder.firstOf(
                        METHOD_DECLARATION,
                        CONSTRUCTOR_DECLARATION,
                        VARIABLE_DECLARATION)
        );
    }

    /**
     * It is responsible for setting the rules for arguments.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void arguments(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ARGUMENTS).is(
                TERMINAL_EXPRESSION,
                grammarBuilder.zeroOrMore(
                        COMMA,
                        TERMINAL_EXPRESSION
                )
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
     * It is responsible for setting the rules for the parameter list.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void parameterList(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PARAMETER_LIST).is(
                PARAMETER,
                grammarBuilder.zeroOrMore(COMMA, PARAMETER)
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
     * Creates rules to the last line of the method and the completion of the method.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void methodDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(METHOD_DECLARATION).is(
                grammarBuilder.zeroOrMore(ANNOTATION),
                MODIFIER,
                TYPE,
                METHOD_NAME,
                LPAREN,
                grammarBuilder.optional(PARAMETER_LIST),
                RPAREN,
                STATEMENT_BLOCK
        );
    }

    /**
     * Creates rules for method's last line and completion.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void constructorDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CONSTRUCTOR_DECLARATION).is(
                grammarBuilder.zeroOrMore(ANNOTATION),
                MODIFIER,
                CLASS_NAME,
                LPAREN,
                grammarBuilder.optional(PARAMETER_LIST),
                RPAREN,
                STATEMENT_BLOCK
        );
    }

    /**
     * Creates rules for the primitive values of a class.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void typeSpecifier(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE_SPECIFIER).is(
                grammarBuilder.firstOf(
                        BOOLEAN,
                        CHAR,
                        BYTE,
                        SHORT,
                        INT,
                        LONG,
                        FLOAT,
                        VOID,
                        DOUBLE,
                        CLASS_NAME
                )
        );
    }

    /**
     * It is responsible for creating the rule for merge with symbol '=' and an expression.
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
     * It is responsible for managing the rule of integers, strings, characters.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void literalExpression(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(LITERAL_EXPRESSION).is(
                grammarBuilder.firstOf(
                        STRING,
                        NUMERIC)
        );
    }

    /**
     * It is responsible for creating the rules to make the casting of an expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void castingExpression(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CASTING_EXPRESSION).is(
                LPAREN,
                TYPE,
                RPAREN,
                TERMINAL_EXPRESSION
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
     * It is responsible for creating the rules to make the testing of an expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void testingExpression(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TESTING_EXPRESSION).is(
                TERMINAL_EXPRESSION,
                grammarBuilder.firstOf(EQUAL, GT, LT),
                TERMINAL_EXPRESSION
        );
    }

    /**
     * It is responsible for creating the rules to make the creating of an expression.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void creatingExpression(ApexGrammarBuilder grammarBuilder) {
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
     * It is responsible for setting the rules for simple mathematical operations.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpressionOperations(ApexGrammarBuilder grammarBuilder) {
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
     * It is responsible for creating the rules to make one increment or decrement.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void numericExpressionOperationsSimple(ApexGrammarBuilder grammarBuilder) {
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
    private static void numericExpression(ApexGrammarBuilder grammarBuilder) {
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
    private static void invokeExpression(ApexGrammarBuilder grammarBuilder) {
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
    private static void terminalExpression(ApexGrammarBuilder grammarBuilder) {
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
    private static void expression(ApexGrammarBuilder grammarBuilder) {
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
    private static void expressionFinal(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXPRESSION_FINAL).is(EXPRESSION, SEMICOLON);
    }

    /**
     * It is responsible for setting the rules for the else.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void statamentElse(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STATEMENT_ELSE).is(
                ELSE,
                grammarBuilder.firstOf(
                        TERMINAL_STATEMENT,
                        STATEMENT_BLOCK)
        );
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
                grammarBuilder.firstOf(
                        TERMINAL_STATEMENT,
                        STATEMENT_BLOCK),
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
                grammarBuilder.firstOf(
                        TERMINAL_STATEMENT,
                        STATEMENT_BLOCK)
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
                TYPE,
                VARIABLE_DECLARATOR_ID,
                COLON,
                VARIABLE_DECLARATOR_ID,
                RPAREN,
                grammarBuilder.firstOf(
                        TERMINAL_STATEMENT,
                        STATEMENT_BLOCK)
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
                grammarBuilder.firstOf(
                        TERMINAL_STATEMENT,
                        STATEMENT_BLOCK),
                CATCH,
                LPAREN,
                PARAMETER,
                RPAREN,
                grammarBuilder.firstOf(
                        TERMINAL_STATEMENT,
                        STATEMENT_BLOCK)
        );
    }

    /**
     * It is responsible for creating the rules for a DML statement.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void dmlStatement(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DML_STATEMENT).is(
                grammarBuilder.firstOf(
                        INSERT,
                        UPDATE,
                        UPSERT,
                        DELETE,
                        UNDELETE,
                        MERGE),
                EXPRESSION_FINAL
        );
    }

    /**
     * It is responsible for creating the rules for a return statement.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void returnStatement(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(RETURN_STATEMENT).is(
                RETURN,
                EXPRESSION_FINAL
        );
    }

    /**
     * It is responsible for setting the rules for the all statements.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void statement(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STATEMENT).is(
                grammarBuilder.firstOf(
                        TERMINAL_STATEMENT,
                        STATEMENT_IF,
                        WHILE_STATEMENT,
                        FOR_STATEMENT,
                        TRY_STATEMENT,
                        RETURN_STATEMENT)
        );
        grammarBuilder.rule(TERMINAL_STATEMENT).is(
                grammarBuilder.firstOf(
                        EXPRESSION_FINAL,
                        VARIABLE_DECLARATION,
                        DML_STATEMENT)
        );
    }

    /**
     * It is responsible for setting the rules for the declaration of a variable.
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
     * It is responsible for building the rules for the different types of return of a method.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void type(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE).is(TYPE_SPECIFIER);
    }
}
