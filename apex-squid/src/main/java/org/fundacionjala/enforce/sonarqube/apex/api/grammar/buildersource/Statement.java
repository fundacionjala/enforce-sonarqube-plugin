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

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BREAK;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CATCH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CONTINUE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DELETE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DO;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ELSE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FINALLY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IF;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INSERT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.MERGE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.RETURN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.THIS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.THROW;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UNDELETE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UPDATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UPSERT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WHILE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COMMA;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.SEMICOLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BREAK_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONTINUE_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DML_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DO_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EMPTY_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPRESSION_FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FORMAL_PARAMETER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_EACH_LOOP;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_INIT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_LOOP;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_STATEMENT_PI;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.IF_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LOCAL_VARIABLE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LOCAL_VARIABLE_DECLARATION_SEMICOLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.PARAMETER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.RETURN_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.RETURN_STATEMENT_PI;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT_BLOCK;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT_ELSE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT_IF;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT_PI;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TERMINAL_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.THROW_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TRY_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TRY_STATEMENT_PI;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE_PI;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATORS_PI;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATOR_ID;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATOR_PI;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.WHILE_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.WHILE_STATEMENT_PI;

/**
 * This class contains constructors for Statement rules and its sub rules.
 *
 */
public class Statement {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        statement(grammarBuilder);
        whileStatement(grammarBuilder);
        tryStatement(grammarBuilder);
        forStatement(grammarBuilder);
        returnStatement(grammarBuilder);
        dmlStatement(grammarBuilder);
        statementBlock(grammarBuilder);
        statementIf(grammarBuilder);
        statamentElse(grammarBuilder);
        blockStatement(grammarBuilder);
        statementPi(grammarBuilder);
        emptyStatement(grammarBuilder);
        ifStatement(grammarBuilder);
        whileStatementPi(grammarBuilder);
        doStatement(grammarBuilder);
        breakStatement(grammarBuilder);
        continueStatement(grammarBuilder);
        returnStatementPi(grammarBuilder);
        throwStatement(grammarBuilder);
        forEachLoop(grammarBuilder);
        forInit(grammarBuilder);
        forLoop(grammarBuilder);
        forStatementPi(grammarBuilder);
        tryStatementPi(grammarBuilder);
    }

    /**
     * It is responsible for setting the rules for the else.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void statamentElse(LexerfulGrammarBuilder grammarBuilder) {
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
    private static void statementIf(LexerfulGrammarBuilder grammarBuilder) {
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
    private static void statementBlock(LexerfulGrammarBuilder grammarBuilder) {
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
    private static void whileStatement(LexerfulGrammarBuilder grammarBuilder) {
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
    private static void forStatement(LexerfulGrammarBuilder grammarBuilder) {
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
    private static void tryStatement(LexerfulGrammarBuilder grammarBuilder) {
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
    private static void dmlStatement(LexerfulGrammarBuilder grammarBuilder) {
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
    private static void returnStatement(LexerfulGrammarBuilder grammarBuilder) {
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
    private static void statement(LexerfulGrammarBuilder grammarBuilder) {
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
     * Grammar to define block statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void blockStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(BLOCK_STATEMENT).is(
                grammarBuilder.firstOf(
                        LOCAL_VARIABLE_DECLARATION_SEMICOLON,
                        STATEMENT
                )
        );

        grammarBuilder.rule(LOCAL_VARIABLE_DECLARATION_SEMICOLON).is(
                LOCAL_VARIABLE_DECLARATION,
                SEMICOLON
        );
    }

    /**
     * Defines the statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void statementPi(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STATEMENT_PI).is(
                grammarBuilder.firstOf(
                        BLOCK,
                        EMPTY_STATEMENT,
                        IF_STATEMENT,
                        WHILE_STATEMENT_PI,
                        DO_STATEMENT,
                        FOR_STATEMENT_PI,
                        BREAK_STATEMENT,
                        CONTINUE_STATEMENT,
                        RETURN_STATEMENT_PI,
                        THROW_STATEMENT,
                        TRY_STATEMENT_PI
                )
        );
    }

    /**
     * Defines the empty statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void emptyStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EMPTY_STATEMENT).is(
                SEMICOLON
        );
    }

    /**
     * Defines the if statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void ifStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(IF_STATEMENT).is(
                IF,
                LPAREN,
                EXPRESSION,
                RPAREN,
                STATEMENT_PI,
                SEMICOLON,
                grammarBuilder.optional(
                        ELSE,
                        STATEMENT_PI
                )
        );
    }

    /**
     * Defines the while loop rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void whileStatementPi(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(WHILE_STATEMENT_PI).is(
                WHILE,
                LPAREN,
                EXPRESSION,
                RPAREN,
                STATEMENT_PI
        );
    }

    /**
     * Defines the do statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter
     */
    private static void doStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DO_STATEMENT).is(
                DO,
                STATEMENT_PI,
                WHILE,
                LPAREN,
                EXPRESSION,
                RPAREN,
                SEMICOLON
        );
    }

    /**
     * Defines the break statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void breakStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(BREAK_STATEMENT).is(
                BREAK,
                SEMICOLON
        );
    }

    /**
     * Defines the continue statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void continueStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CONTINUE_STATEMENT).is(
                CONTINUE,
                SEMICOLON
        );
    }

    /**
     * Defines the return statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void returnStatementPi(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(RETURN_STATEMENT_PI).is(
                RETURN,
                grammarBuilder.firstOf(EXPRESSION, THIS),
                SEMICOLON
        );
    }

    /**
     * Defines the throw statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void throwStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(THROW_STATEMENT).is(
                THROW,
                EXPRESSION,
                SEMICOLON
        );
    }

    /**
     * Defines the for each loop rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void forEachLoop(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FOR_EACH_LOOP).is(
                LPAREN,
                TYPE,
                ALLOWED_KEYWORDS_AS_IDENTIFIER,
                COLON,
                EXPRESSION,
                RPAREN,
                STATEMENT_PI
        );
    }

    /**
     * Defines the for initialization rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void forInit(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FOR_INIT).is(
                grammarBuilder.firstOf(
                        LOCAL_VARIABLE_DECLARATION,
                        VARIABLE_DECLARATORS_PI
                )
        );

        grammarBuilder.rule(VARIABLE_DECLARATORS_PI).is(
                VARIABLE_DECLARATOR_PI,
                grammarBuilder.zeroOrMore(
                        COMMA,
                        VARIABLE_DECLARATOR_PI
                )
        );

    }

    /**
     * Defines the for loop rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void forLoop(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FOR_LOOP).is(
                LPAREN,
                grammarBuilder.optional(FOR_INIT),
                SEMICOLON,
                grammarBuilder.optional(EXPRESSION),
                SEMICOLON,
                grammarBuilder.optional(EXPRESSION),
                RPAREN,
                STATEMENT_PI
        );
    }

    /**
     * Defines the for statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void forStatementPi(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FOR_STATEMENT_PI).is(
                FOR,
                grammarBuilder.firstOf(
                        FOR_EACH_LOOP,
                        FOR_LOOP
                )
        );
    }

    /**
     * Defines the try statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void tryStatementPi(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TRY_STATEMENT_PI).is(
                TRY,
                BLOCK,
                grammarBuilder.oneOrMore(
                        CATCH,
                        LPAREN,
                        FORMAL_PARAMETER,
                        RPAREN,
                        BLOCK
                ),
                grammarBuilder.optional(
                        FINALLY,
                        BLOCK
                )
        );
    }
}
