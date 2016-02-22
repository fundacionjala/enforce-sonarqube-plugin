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
package org.fundacionjala.enforce.sonarqube.apex.api.grammar;

import org.sonar.sslr.grammar.GrammarRuleKey;

/**
 * Contains enum all the rules used in the grammar.
 */
public enum RuleKey implements GrammarRuleKey {

    APEX_GRAMMAR,
    ASSIGN_VARIABLE_INITILIZER,
    BODY_IDENTIFIER,
    BRACKETS,
    CASTING_EXPRESSION,
    CHAR,
    CHARACTER_LITERAL,
    EQUAL,
    FIELD_DECLARATION,
    FOR_STATEMENT,
    FOR_STATEMENT_INITIALIZER,
    CLASS_DECLARATION,
    CLASS_NAME,
    DELETE_STATEMENT_DML,
    EXPRESSION,
    EXPRESSION_FINAL,
    EXTENDS_LIST,
    IDENTIFIER,
    IMPLEMENTS_LIST,
    INSERT_STATEMENT_DML,
    INIT_IDENTIFIER,
    INC,
    INTEGER_LITERAL,
    KEYWORD,
    LITERAL_EXPRESSION,
    DEC,
    MERGE_STATEMENT_DML,
    MODIFIER,
    METHOD_DECLARATION,
    METHOD_NAME,
    MODIFIER_KEYWORD,
    MODIFIERS,
    NUMERIC_EXPRESSION,
    NUMERIC_EXPRESSION_OPERATIONS,
    NUMERIC_EXPRESSION_OPERATIONS_SIMPLE,
    STATEMENT,
    STATEMENT_BLOCK,
    STATEMENT_ELSE,
    STATEMENT_IF,
    STRING_EXPRESSION,
    STRING_LITERAL,
    PARAMETER,
    PARAMETER_LIST,
    PARAMETER_LIST_OPTIONAL,
    TESTING_EXPRESSION,
    TERMINAL_EXPRESSION,
    TRY_STATEMENT,
    TYPE_SPECIFIER,
    TYPE,
    TYPE_CLASS,
    TYPE_DECLARATION,
    UNDELETE_STATEMENT_DML,
    UPDATE_STATEMENT_DML,
    UPSERT_STATEMENT_DML,
    VARIABLE_DECLARATION,
    VARIABLE_DECLARATOR,
    VARIABLE_DECLARATOR_ID,
    VARIABLE_INITILIZER,
    WHILE_STATEMENT;
}
