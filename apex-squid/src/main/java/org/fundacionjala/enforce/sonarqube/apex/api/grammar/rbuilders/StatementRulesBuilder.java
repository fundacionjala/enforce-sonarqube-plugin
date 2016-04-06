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
package org.fundacionjala.enforce.sonarqube.apex.api.grammar.rbuilders;

import org.sonar.sslr.grammar.LexerfulGrammarBuilder;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CATCH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DELETE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ELSE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IF;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INSERT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.MERGE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.RETURN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRY;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UNDELETE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UPDATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.UPSERT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WHILE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DML_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPRESSION_FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.PARAMETER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.RETURN_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT_BLOCK;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT_ELSE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT_IF;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TERMINAL_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TRY_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATOR_ID;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.WHILE_STATEMENT;

/**
 *
 * @author kevin_titichoca
 */
public class StatementRulesBuilder {
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
}
