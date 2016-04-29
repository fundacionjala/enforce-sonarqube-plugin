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
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.BREAK;
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
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.SEMICOLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BREAK_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.COMPOUND_STATEMENT_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONTINUE_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DML_MERGE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DML_OPERATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DML_OPERATIONS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DML_UPSERT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DO_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EMPTY_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FORMAL_PARAMETER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_EACH_LOOP;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_INIT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_LOOP;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.IF_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LOCAL_VARIABLE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.PRIMARY_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.THROW_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.RETURN_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TRY_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.WHILE_STATEMENT;

/**
 * This class contains constructors for Statement rules and its sub rules.
 *
 */
public class Statement {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
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
        dmlOperation(grammarBuilder);
        dmlUpsert(grammarBuilder);
        dmlMerge(grammarBuilder);
        dmlOperations(grammarBuilder);
    }

    /**
     * Grammar to define block statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void blockStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(BLOCK_STATEMENT).is(grammarBuilder.firstOf(grammarBuilder.sequence(
                                LOCAL_VARIABLE_DECLARATION,
                                SEMICOLON
                        ),
                        STATEMENT
                )
        );
    }

    /**
     * Defines the statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void statementPi(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(STATEMENT).is(grammarBuilder.firstOf(BLOCK,
                        grammarBuilder.sequence(DML_OPERATIONS, SEMICOLON),
                        EMPTY_STATEMENT,
                        COMPOUND_STATEMENT_EXPRESSION,
                        IF_STATEMENT,
                        WHILE_STATEMENT,
                        DO_STATEMENT,
                        FOR_STATEMENT,
                        BREAK_STATEMENT,
                        CONTINUE_STATEMENT,
                        RETURN_STATEMENT,
                        THROW_STATEMENT,
                        TRY_STATEMENT
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
        grammarBuilder.rule(IF_STATEMENT).is(IF,
                LPAREN,
                EXPRESSION,
                RPAREN,
                STATEMENT,
                grammarBuilder.optional(ELSE,
                        STATEMENT
                )
        );
    }

    /**
     * Defines the while loop rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void whileStatementPi(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(WHILE_STATEMENT).is(WHILE,
                LPAREN,
                EXPRESSION,
                RPAREN,
                STATEMENT
        );
    }

    /**
     * Defines the do statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter
     */
    private static void doStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DO_STATEMENT).is(DO,
                STATEMENT,
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
        grammarBuilder.rule(RETURN_STATEMENT).is(RETURN,
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
        grammarBuilder.rule(THROW_STATEMENT).is(THROW,
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
        grammarBuilder.rule(FOR_EACH_LOOP).is(LPAREN,
                TYPE,
                ALLOWED_KEYWORDS_AS_IDENTIFIER,
                COLON,
                EXPRESSION,
                RPAREN,
                STATEMENT
        );
    }

    /**
     * Defines the for initialization rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void forInit(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FOR_INIT).is(grammarBuilder.firstOf(LOCAL_VARIABLE_DECLARATION,
                        grammarBuilder.sequence(VARIABLE_DECLARATOR,
                                grammarBuilder.zeroOrMore(COMMA,
                                        VARIABLE_DECLARATOR
                                )
                        )
                )
        );
    }

    /**
     * Defines the for loop rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void forLoop(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FOR_LOOP).is(LPAREN,
                grammarBuilder.optional(FOR_INIT),
                SEMICOLON,
                grammarBuilder.optional(EXPRESSION),
                SEMICOLON,
                grammarBuilder.optional(EXPRESSION),
                RPAREN,
                STATEMENT
        );
    }

    /**
     * Defines the for statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void forStatementPi(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FOR_STATEMENT).is(
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
        grammarBuilder.rule(TRY_STATEMENT).is(
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

    /**
     * Defines dml operation which is insert, delete, undelete,update
     * operations.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void dmlOperation(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DML_OPERATION).is(
                grammarBuilder.firstOf(
                        INSERT,
                        DELETE,
                        UNDELETE,
                        UPDATE
                ),
                PRIMARY_EXPRESSION
        );
    }

    /**
     * Defines the rule for upsert operation.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void dmlUpsert(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DML_UPSERT).is(
                UPSERT,
                PRIMARY_EXPRESSION,
                grammarBuilder.optional(PRIMARY_EXPRESSION)
        );

    }

    /**
     * Defines the rule for merge dml operation.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void dmlMerge(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DML_MERGE).is(
                MERGE,
                PRIMARY_EXPRESSION,
                PRIMARY_EXPRESSION
        );
    }

    /**
     * Defines dml operations such as upsert merge and
     * insert,undelete,delete,update.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void dmlOperations(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(DML_OPERATIONS).is(
                grammarBuilder.firstOf(
                        DML_OPERATION,
                        DML_UPSERT,
                        DML_MERGE
                )
        );
    }
}
