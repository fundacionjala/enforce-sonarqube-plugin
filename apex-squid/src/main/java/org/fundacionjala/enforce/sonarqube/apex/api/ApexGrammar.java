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
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CHAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CLASS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.DOUBLE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.EXTENDS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FLOAT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.IMPLEMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INTERFACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.LONG;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NATIVE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NULL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PRIVATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PROTECTED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PUBLIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.RETURN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHARING;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHORT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STATIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STRICTFP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SYNCHRONIZED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRANSIENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VOID;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VOLATILE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITHOUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACKET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACKET;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RPAREN;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.SEMICOLON;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.APEX_GRAMMAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.BRACKETS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_OR_INTERFACE_BODY_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_OR_INTERFACE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXTENDES_OR_IMPLEMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXTENDS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.IMPLEMENTS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.KEYWORD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.LOOKAHEAD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.LOOKAHEAD_KEYWORD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.MERGE_TYPE_EXTENDS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.MERGE_TYPE_IMPLEMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.METHOD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.METHOD_NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.MODIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.MODIFIERS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.PRIMITIVE_TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.RESULT_TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_CLASS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_METHOD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.VARIABLE_DECLARATOR_ID;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WITHOUT_SHARING;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WITH_SHARING;

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
        brackets(grammarBuilder);
        variableDeclaratorId(grammarBuilder);
        primitiveType(grammarBuilder);
        type(grammarBuilder);
        resultType(grammarBuilder);
        methodDeclaration(grammarBuilder);
        typeClass(grammarBuilder);
        extendsList(grammarBuilder);
        implementsList(grammarBuilder);
        classOrInterfaceDeclaration(grammarBuilder);
        keyword(grammarBuilder);
        lookahead(grammarBuilder);
        modifiers(grammarBuilder);
        classOrInterfaceBodyDeclaration(grammarBuilder);
        modifier(grammarBuilder);
        typeDeclaration(grammarBuilder);

        grammarBuilder.rule(APEX_GRAMMAR).is(TYPE_DECLARATION,
                grammarBuilder.optional(CLASS_OR_INTERFACE_BODY_DECLARATION),
                RBRACE
        );
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
                MODIFIER,
                CLASS_OR_INTERFACE_DECLARATION,
                LBRACE
        );
    }

    /**
     * Grammar is created for a class modifier (lookahead or keyword).
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void modifier(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(LOOKAHEAD_KEYWORD).is(
                LOOKAHEAD,
                KEYWORD
        );
        grammarBuilder.rule(MODIFIER).is(
                grammarBuilder.firstOf(
                        LOOKAHEAD_KEYWORD,
                        LOOKAHEAD
                )
        );
    }

    /**
     * Grammar for different access modifiers of a class is created.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void lookahead(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(LOOKAHEAD).is(grammarBuilder.firstOf(
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
                ANOTATION));
    }

    /**
     * Grammar to identify a sharing rule keyword.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void keyword(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(WITH_SHARING).is(WITH, SHARING);
        grammarBuilder.rule(WITHOUT_SHARING).is(WITHOUT, SHARING);
        grammarBuilder.rule(KEYWORD).is(
                grammarBuilder.firstOf(
                        WITHOUT_SHARING,
                        WITH_SHARING)
        );
    }

    /**
     * Grammar for the declaration of a class or interface is constructed.
     * Composed of the rules of a class type, its identified, extends, and
     * implements.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void classOrInterfaceDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXTENDES_OR_IMPLEMENTS).is(
                grammarBuilder.optional(IMPLEMENTS_LIST),
                grammarBuilder.optional(EXTENDS_LIST)
        );
        grammarBuilder.rule(CLASS_NAME).is(IDENTIFIER);
        grammarBuilder.rule(CLASS_OR_INTERFACE_DECLARATION).is(
                TYPE_CLASS,
                CLASS_NAME,
                EXTENDES_OR_IMPLEMENTS);
    }

    /**
     * Grammar is created to implement another class or not.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void implementsList(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(MERGE_TYPE_IMPLEMENTS).is(
                IMPLEMENTS,
                IDENTIFIER
        );
        grammarBuilder.rule(IMPLEMENTS_LIST).is(
                MERGE_TYPE_IMPLEMENTS
        );
    }

    /**
     * Grammar is created to extend another class or just skip.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void extendsList(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(MERGE_TYPE_EXTENDS).is(
                EXTENDS,
                IDENTIFIER
        );
        grammarBuilder.rule(EXTENDS_LIST).is(
                MERGE_TYPE_EXTENDS
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
    private static void classOrInterfaceBodyDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CLASS_OR_INTERFACE_BODY_DECLARATION).is(
                MODIFIERS,
                METHOD_DECLARATION
        );
    }

    /**
     * Creates rules to the last line of the method and the completion of the
     * method.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void methodDeclaration(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(METHOD_DECLARATION).is(
                RESULT_TYPE,
                RBRACE
        );
    }

    /**
     * Creates rules for the header of a method.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void modifiers(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(METHOD_NAME).is(IDENTIFIER);
        grammarBuilder.rule(MODIFIERS).is(
                LOOKAHEAD,
                TYPE_METHOD,
                METHOD_NAME,
                LPAREN,
                RPAREN,
                LBRACE
        );
    }

    /**
     * Creates rules for the primitive values of a class.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void primitiveType(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PRIMITIVE_TYPE).is(grammarBuilder.firstOf(BOOLEAN,
                CHAR,
                BYTE,
                SHORT,
                INT,
                LONG,
                FLOAT,
                DOUBLE)
        );
    }

    /**
     * Creates rules for the return of a method and its value.
     *
     * @param grammarBuilder ApexGrammarBuilder parameter.
     */
    private static void resultType(ApexGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(TYPE_METHOD).is(TYPE);
        grammarBuilder.rule(RESULT_TYPE).is(
                RETURN,
                grammarBuilder.firstOf(
                        VOID,
                        TYPE_METHOD,
                        NULL),
                SEMICOLON
        );
    }

    /* It is responsible for creating the rule for identifying a variable.
     *
     * @param grammarBuilder
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
        grammarBuilder.rule(TYPE).is(PRIMITIVE_TYPE);
    }
}
