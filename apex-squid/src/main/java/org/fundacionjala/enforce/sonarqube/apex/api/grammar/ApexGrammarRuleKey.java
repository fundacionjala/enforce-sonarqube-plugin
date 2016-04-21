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
public enum ApexGrammarRuleKey implements GrammarRuleKey {

    ACCESSOR,
    ACCESSOR_DECLARATION,
    ACCESSOR_DECLARATIONS,
    ACCESSOR_BODY,
    ADDITIVE_EXPRESSION,
    ALLOWED_KEYWORDS_AS_IDENTIFIER,
    ARGUMENTS,
    ARGUMENTSPI,
    ARGUMENTS_LIST,
    AND_EXPRESSION,
    ANNOTATION,
    APEX_GRAMMAR,
    ASSIGNMENT_OPERATOR,
    ASSIGN_VARIABLE_INITILIZER,
    BLOCK,
    BLOCK_STATEMENT,
    BODY_IDENTIFIER,
    BRACKETS,
    BREAK_STATEMENT,
    INVOKE_EXPRESSION,
    CASTING_EXPRESSION,
    CLASS_DECLARATION,
    CLASS_NAME,
    CLASS_OR_INTERFACE_BODY,
    CLASS_OR_INTERFACE_DECLARATION,
    CLASS_OR_INTERFACE_MEMBER,
    CLASS_OR_INTERFACE_TYPE,
    CREATING_EXPRESSION,
    CONDITIONAL_AND_EXPRESSION,
    CONDITIONAL_EXPRESSION,
    CONDITIONAL_OR_EXPRESSION,
    CONSTRUCTOR_DECLARATION,
    COLLECTIONS_TYPE,
    CONSTRUCTOR_DECLARATION_PI,
    CONTINUE_STATEMENT,
    DML_MERGE,
    DML_OPERATION,
    DML_OPERATIONS,
    DML_OPERATIONS_SEMICOLON,
    DML_STATEMENT,
    DML_UPSERT,
    DECLARATIONS_WITH_MODIFIERS,
    DO_STATEMENT,
    EQUAL,
    ENUM_DECLARATION,
    ENUM_BODY,
    EQUALITY_EXPRESSION,
    EMPTY_STATEMENT,
    EXPLICIT_CONSTRUCTOR_INVOCATION_PI,
    EXPRESSION,
    EXPRESSION_FINAL,
    EXPRESSION_PI,
    EXTENDS_LIST,
    FIELD_DECLARATION,
    FIELD_DECLARATION_PI,
    FOR_EACH_LOOP,
    FOR_INIT,
    FOR_LOOP,
    FOR_STATEMENT,
    FOR_STATEMENT_PI,
    FORMAL_PARAMETERS,
    FORMAL_PARAMETER,
    GET_SHARING_RULES,
    IF_STATEMENT,
    IMPLEMENTS_LIST,
    INC,
    EXCLUSIVE_OR_EXPRESSION,
    INCLUSIVE_OR_EXPRESSION,
    INIT_IDENTIFIER,
    INITIALIZER,
    INITIALIZER_BLOCK,
    INITIALIZER_BLOCK_MEMBER,
    INSTANCE_OF_EXPRESSION,
    KEYWORD,
    LITERAL_EXPRESSION,
    DEC,
    LOCAL_VARIABLE_DECLARATION,
    LOCAL_VARIABLE_DECLARATION_SEMICOLON,
    MAP_TYPE,
    MODIFIER,
    METHOD_DECLARATION,
    METHOD_DECLARATION_PI,
    METHOD_IDENTIFIER,
    MODIFIER_KEYWORD,
    MULTIPLICATIVE_EXPRESSION,
    NUMERIC_EXPRESSION,
    NUMERIC_EXPRESSION_OPERATIONS,
    NUMERIC_EXPRESSION_OPERATIONS_SIMPLE,
    PARAMETER,
    PARAMETER_LIST,
    PROPERTY_DECLARATION,
    REFERENCE_TYPE,
    RELATIONAL_EXPRESSION,
    RESULT_TYPE,
    RETURN_STATEMENT,
    SHIFT_EXPRESSION,
    SIMPLE_TYPE,
    RETURN_STATEMENT_PI,
    SPECIAL_KEYWORDS_AS_IDENTIFIER,
    STATEMENT,
    STATEMENT_PI,
    STATEMENT_BLOCK,
    STATEMENT_ELSE,
    STATEMENT_IF,
    STRING_EXPRESSION,
    TESTING_EXPRESSION,
    TERMINAL_EXPRESSION,
    TERMINAL_STATEMENT,
    THROW_STATEMENT,
    TRY_STATEMENT,
    TRY_STATEMENT_PI,
    TYPE_SPECIFIER,
    TYPE,
    TYPE_PI,
    TYPE_CLASS,
    TYPE_DECLARATION,
    VARIABLE_DECLARATION,
    VARIABLE_DECLARATOR,
    VARIABLE_DECLARATOR_PI,
    VARIABLE_DECLARATORS_PI,
    VARIABLE_DECLARATOR_ID,
    WHILE_STATEMENT,
    WHILE_STATEMENT_PI;
}
