/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.api.grammar.buildersource;

import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.*;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.*;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.*;

import static com.sonar.sslr.api.GenericTokenType.EOF;

/**
 * This class contains constructors for Declaration rules and its sub rules.
 *
 */
public class Declaration {

    public static void create(LexerfulGrammarBuilder grammarBuilder) {
        typeDeclaration(grammarBuilder);
        typeClass(grammarBuilder);
        extendsList(grammarBuilder);
        implementsList(grammarBuilder);
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
        grammarBuilder.rule(RECOVERED_MEMBER).is(
                grammarBuilder.anyTokenButNot(
                        grammarBuilder.firstOf(
                                CLASS_OR_INTERFACE_MEMBER, RBRACE, EOF))
        );
        grammarBuilder.rule(CLASS_OR_INTERFACE_BODY).is(
                grammarBuilder.zeroOrMore(
                        grammarBuilder.firstOf(CLASS_OR_INTERFACE_MEMBER, RECOVERED_MEMBER)
                )
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
        grammarBuilder.rule(CLASS_OR_INTERFACE_MEMBER).is(
                grammarBuilder.firstOf(
                        INITIALIZER,
                        grammarBuilder.sequence(
                                MODIFIERS,
                                grammarBuilder.firstOf(METHOD_DECLARATION,
                                        PROPERTY_DECLARATION,
                                        CLASS_OR_INTERFACE_DECLARATION,
                                        ENUM_DECLARATION,
                                        CONSTRUCTOR_DECLARATION,
                                        FIELD_DECLARATION
                                )
                        )
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
