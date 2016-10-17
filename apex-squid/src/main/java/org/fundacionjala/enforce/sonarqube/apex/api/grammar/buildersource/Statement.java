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
 * This class contains constructors for Statement rules and its sub rules.
 *
 */
public class Statement {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        blockStatement(grammarBuilder);
        statement(grammarBuilder);
        emptyStatement(grammarBuilder);
        ifStatement(grammarBuilder);
        whileStatement(grammarBuilder);
        doStatement(grammarBuilder);
        breakStatement(grammarBuilder);
        continueStatement(grammarBuilder);
        returnStatement(grammarBuilder);
        throwStatement(grammarBuilder);
        forEachLoop(grammarBuilder);
        forInit(grammarBuilder);
        forLoop(grammarBuilder);
        forStatement(grammarBuilder);
        tryStatement(grammarBuilder);
        catchStatement(grammarBuilder);
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
    private static void statement(LexerfulGrammarBuilder grammarBuilder) {
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
    private static void whileStatement(LexerfulGrammarBuilder grammarBuilder) {
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
    private static void returnStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(RETURN_STATEMENT).is(
                RETURN,
                grammarBuilder.optional(
                        grammarBuilder.firstOf(EXPRESSION, THIS)
                ),
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
    private static void forStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FOR_STATEMENT).is(
                FOR,
                grammarBuilder.firstOf(
                        FOR_EACH_LOOP,
                        FOR_LOOP
                )
        );
    }
    
    private static void catchStatement(LexerfulGrammarBuilder grammarBuilder){
    	grammarBuilder.rule(CATCH_STATEMENT).is(
    			CATCH,
    			LPAREN,
    			FORMAL_PARAMETER,
    			RPAREN,
    			BLOCK
		);
    }

    /**
     * Defines the try statement rule.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void tryStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TRY_STATEMENT).is(
                TRY,
                BLOCK,
                grammarBuilder.oneOrMore(
                		CATCH_STATEMENT
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
