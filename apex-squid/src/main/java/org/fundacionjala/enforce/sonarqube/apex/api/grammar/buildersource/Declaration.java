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
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CLASS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ENUM;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.EXCEPTION;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.EXTENDS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.GET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IMPLEMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INTERFACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STATIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHARING;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SUPER;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.THIS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VOID;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITHOUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.ASSIGN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.COMMA;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.SEMICOLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ACCESSOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ACCESSOR_BODY;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ACCESSOR_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ACCESSOR_DECLARATIONS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BREAK_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_BODY;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_MEMBER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXTENDS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FORMAL_PARAMETER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FORMAL_PARAMETERS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.IMPLEMENTS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.PROPERTY_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.RESULT_TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE_CLASS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONTINUE_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.DO_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EMPTY_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ENUM_BODY;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ENUM_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.IF_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INITIALIZER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INITIALIZER_BLOCK;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INITIALIZER_BLOCK_MEMBER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.GET_SHARING_RULES;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.INITIALIZER_BLOCK_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LOCAL_VARIABLE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.SPECIAL_KEYWORDS_AS_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIERS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.THROW_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_INITIALIZER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARGUMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_ERASURE_TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.COMMON_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONSTRUCTOR_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPLICIT_CONSTRUCTOR_INVOCATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FIELD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FOR_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.RETURN_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TRY_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.WHILE_STATEMENT;

/**
 * This class contains constructors for Declaration rules and its sub rules.
 *
 */
public class Declaration {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        typeClass(grammarBuilder);
        extendsList(grammarBuilder);
        implementsList(grammarBuilder);
        typeDeclaration(grammarBuilder);
        methodName(grammarBuilder);
        propertyDeclaration(grammarBuilder);
        accessor(grammarBuilder);
        accessorBody(grammarBuilder);
        accessorDeclaration(grammarBuilder);
        accessorDeclarations(grammarBuilder);
        methodDeclaration(grammarBuilder);
        resultType(grammarBuilder);
        formalParameters(grammarBuilder);
        formalParameter(grammarBuilder);
        constructorDeclaration(grammarBuilder);
        explicitConstructorInvocation(grammarBuilder);
        classOrInterfaceDeclaration(grammarBuilder);
        getSharingRules(grammarBuilder);
        enumDeclaration(grammarBuilder);
        enumBody(grammarBuilder);
        initializerBlockStatement(grammarBuilder);
        fieldDeclaration(grammarBuilder);
        variableDeclarator(grammarBuilder);
        initializer(grammarBuilder);
        initializerBlock(grammarBuilder);
        initializerBlockMember(grammarBuilder);
        localVariableDeclaration(grammarBuilder);
        classOrInterfaceMember(grammarBuilder);
        classOrInterfaceBody(grammarBuilder);
    }

    private static void typeDeclaration(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE_DECLARATION).is(
                MODIFIERS,
                grammarBuilder.firstOf(
                        CLASS_OR_INTERFACE_DECLARATION,
                        ENUM_DECLARATION)
        );
    }

    /**
     * Grammar is created to implement another class or not.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void implementsList(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(IMPLEMENTS_LIST).is(
                IMPLEMENTS,
                CLASS_OR_INTERFACE_ERASURE_TYPE,
                grammarBuilder.zeroOrMore(COMMA, CLASS_OR_INTERFACE_ERASURE_TYPE)
        );
    }

    /**
     * Grammar is created to extend another class or just skip.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void extendsList(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXTENDS_LIST).is(
                EXTENDS,
                grammarBuilder.firstOf(EXCEPTION, CLASS_OR_INTERFACE_TYPE)
        );
    }

    /**
     * Grammar is created to identify if a class or interface.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void typeClass(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE_CLASS).is(
                grammarBuilder.firstOf(
                        CLASS,
                        INTERFACE)
        );
    }

    /**
     * It is responsible for managing the method name.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void methodName(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(METHOD_IDENTIFIER).is(
                grammarBuilder.firstOf(
                        ALLOWED_KEYWORDS_AS_IDENTIFIER,
                        SPECIAL_KEYWORDS_AS_IDENTIFIER));
    }

    /**
     * Creates rules for method's last line and completion.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void constructorDeclaration(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CONSTRUCTOR_DECLARATION).is(grammarBuilder.firstOf(
                ALLOWED_KEYWORDS_AS_IDENTIFIER,
                SPECIAL_KEYWORDS_AS_IDENTIFIER),
                FORMAL_PARAMETERS,
                LBRACE,
                grammarBuilder.optional(EXPLICIT_CONSTRUCTOR_INVOCATION),
                grammarBuilder.zeroOrMore(BLOCK_STATEMENT),
                RBRACE
        );
    }

    /**
     * Creates rules for method's last line and completion.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void explicitConstructorInvocation(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXPLICIT_CONSTRUCTOR_INVOCATION).is(grammarBuilder.firstOf(THIS, SUPER),
                ARGUMENTS,
                SEMICOLON
        );
    }

    /**
     * Creates the rule for Property Declaration within a class.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void propertyDeclaration(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PROPERTY_DECLARATION).is(TYPE,
                grammarBuilder.firstOf(ALLOWED_KEYWORDS_AS_IDENTIFIER, SPECIAL_KEYWORDS_AS_IDENTIFIER),
                LBRACE,
                ACCESSOR_DECLARATIONS,
                RBRACE);
    }

    /**
     * Creates the rule for accessor within a class.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void accessor(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ACCESSOR).is(
                grammarBuilder.firstOf(GET, SET));
    }

    /**
     * Creates the rule for accessor body.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void accessorBody(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ACCESSOR_BODY).is(BLOCK);
    }

    /**
     * Creates the rule for accessor declarations within a class.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void accessorDeclarations(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ACCESSOR_DECLARATIONS).is(
                grammarBuilder.oneOrMore(
                        ACCESSOR_DECLARATION));
    }

    /**
     * Creates the rule for accessor declaration within a class.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void accessorDeclaration(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ACCESSOR_DECLARATION).is(
                MODIFIERS, ACCESSOR,
                grammarBuilder.firstOf(ACCESSOR_BODY, SEMICOLON));
    }

    /**
     * Creates rules to the last line of the method and the completion of the
     * method.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void methodDeclaration(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(METHOD_DECLARATION).is(
                RESULT_TYPE,
                METHOD_IDENTIFIER,
                FORMAL_PARAMETERS,
                grammarBuilder.firstOf(BLOCK, SEMICOLON)
        );
    }

    /**
     * Creates the rule that defines the return type a method can have.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void resultType(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(RESULT_TYPE).is(grammarBuilder.firstOf(VOID, TYPE)
        );
    }

    /**
     * Creates the rule that defines a set of formal parameters for a method.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void formalParameters(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FORMAL_PARAMETERS).is(
                LPAREN, grammarBuilder.optional(FORMAL_PARAMETER,
                        grammarBuilder.zeroOrMore(COMMA, FORMAL_PARAMETER)), RPAREN
        );
    }

    /**
     * Creates the rule that defines a formal parameter for a method.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void formalParameter(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FORMAL_PARAMETER).is(
                grammarBuilder.optional(FINAL),
                TYPE,
                grammarBuilder.firstOf(ALLOWED_KEYWORDS_AS_IDENTIFIER,
                        SPECIAL_KEYWORDS_AS_IDENTIFIER)
        );
    }

    /**
     * Creates the rule that defines an enum declaration.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void enumDeclaration(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ENUM_DECLARATION).is(
                ENUM,
                grammarBuilder.firstOf(ALLOWED_KEYWORDS_AS_IDENTIFIER,
                        SPECIAL_KEYWORDS_AS_IDENTIFIER),
                LBRACE,
                ENUM_BODY,
                RBRACE
        );
    }

    /**
     * Creates the rule that defines an enum body.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void enumBody(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(ENUM_BODY).is(
                grammarBuilder.firstOf(ALLOWED_KEYWORDS_AS_IDENTIFIER,
                        SPECIAL_KEYWORDS_AS_IDENTIFIER),
                grammarBuilder.zeroOrMore(COMMA,
                        grammarBuilder.firstOf(
                                ALLOWED_KEYWORDS_AS_IDENTIFIER,
                                SPECIAL_KEYWORDS_AS_IDENTIFIER
                        )
                )
        );
    }

    /**
     * Creates the rule that defines field declaration.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void fieldDeclaration(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(FIELD_DECLARATION).is(TYPE,
                VARIABLE_DECLARATOR,
                grammarBuilder.zeroOrMore(COMMA,
                        VARIABLE_DECLARATOR
                ),
                SEMICOLON
        );
    }

    /**
     * Creates the rule that defines a variable declarator.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void variableDeclarator(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(VARIABLE_DECLARATOR).is(
                grammarBuilder.firstOf(
                        ALLOWED_KEYWORDS_AS_IDENTIFIER,
                        SPECIAL_KEYWORDS_AS_IDENTIFIER
                ),
                grammarBuilder.optional(
                        grammarBuilder.sequence(
                                ASSIGN,
                                VARIABLE_INITIALIZER)
                )
        );
    }

    /**
     * Grammar for the declaration of a class or interface is constructed.
     * Composed of the rules of a class type, its identified, extends, and
     * implements.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void classOrInterfaceDeclaration(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CLASS_OR_INTERFACE_DECLARATION).is(
                GET_SHARING_RULES,
                MODIFIERS,
                TYPE_CLASS,
                COMMON_IDENTIFIER,
                grammarBuilder.optional(EXTENDS_LIST),
                grammarBuilder.optional(IMPLEMENTS_LIST),
                LBRACE,
                CLASS_OR_INTERFACE_BODY,
                RBRACE
        );
    }

    /**
     * Grammar for the declaration of a class or interface body.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void classOrInterfaceBody(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CLASS_OR_INTERFACE_BODY).is(
                grammarBuilder.zeroOrMore(CLASS_OR_INTERFACE_MEMBER)
        );
    }

    /**
     * Defines the possible sharing rules a class declaration can have.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void getSharingRules(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(GET_SHARING_RULES).is(
                grammarBuilder.optional(
                        grammarBuilder.firstOf(
                                WITHOUT,
                                WITH),
                        SHARING
                )
        );
    }

    /**
     * Creates the rule that defines an initializer.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void initializer(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(INITIALIZER).is(
                grammarBuilder.optional(STATIC),
                INITIALIZER_BLOCK
        );
    }

    /**
     * Creates the rule that defines an initializer block .
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void initializerBlock(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(INITIALIZER_BLOCK).is(
                LBRACE,
                grammarBuilder.zeroOrMore(
                        INITIALIZER_BLOCK_MEMBER),
                RBRACE
        );
    }

    /**
     * Creates the rule that defines an initializerBlockMember.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void initializerBlockMember(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(INITIALIZER_BLOCK_MEMBER).is(grammarBuilder.firstOf(INITIALIZER_BLOCK,
                FIELD_DECLARATION,
                INITIALIZER_BLOCK_STATEMENT,
                SEMICOLON
        )
        );
    }

    private static void initializerBlockStatement(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(INITIALIZER_BLOCK_STATEMENT).is(grammarBuilder.firstOf(EMPTY_STATEMENT,
                grammarBuilder.sequence(STATEMENT_EXPRESSION, SEMICOLON),
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
     * Creates the rule for all the possible class or interface members.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void classOrInterfaceMember(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CLASS_OR_INTERFACE_MEMBER).is(grammarBuilder.firstOf(INITIALIZER,
                grammarBuilder.sequence(MODIFIERS,
                        grammarBuilder.firstOf(METHOD_DECLARATION,
                                PROPERTY_DECLARATION,
                                CLASS_OR_INTERFACE_DECLARATION,
                                ENUM_DECLARATION,
                                CONSTRUCTOR_DECLARATION,
                                FIELD_DECLARATION
                        ))
        )
        );
    }

    /**
     * Creates the rule for localVariableDeclaration.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void localVariableDeclaration(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(LOCAL_VARIABLE_DECLARATION).is(grammarBuilder.optional(FINAL),
                TYPE,
                VARIABLE_DECLARATOR,
                grammarBuilder.zeroOrMore(COMMA, VARIABLE_DECLARATOR)
        );
    }
}
