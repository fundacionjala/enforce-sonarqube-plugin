/*
 * The MIT License
 *
 * Copyright 2016 Jalasoft.
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

import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;
import com.sonar.sslr.api.Grammar;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ABSTRACT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ANOTATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.CLASS;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.INTERFACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NATIVE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PRIVATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PROTECTED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PUBLIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SHARING;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STATIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STRICTFP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SYNCHRONIZED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRANSIENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VOLATILE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITH;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.WITHOUT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.LBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator.RBRACE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.APEX_GRAMMAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_OR_INTERDACE_BODY_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_OR_INTERFACE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.END_CLASS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXTENDES_OR_IMPLEMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXTENDS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXTENDS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.IMPLEMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.IMPLEMENTS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.KEYWORD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.LOOKAHEAD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.LOOKAHEAD_KEYWORD;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.MERGE_TYPE_EXTENDS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.MERGE_TYPE_IMPLEMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.MODIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_CLASS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WITHOUT_SHARING;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WITH_SHARING;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.body.ApexGrammarClassOrInterfaceBodyDeclaration;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

/**
 * This class unites all the rules you need a class.
 */
public class ApexGrammar {

    private final static String RULE_IMPLEMENTS_KEYWORD = "implements";
    private final static String RULE_EMPTY_KEYWORD = "";
    private final static String RULE_EXTENDS_KEYWORD = "extends";

    /**
     * It is the main method of grammar. Here all other grammars are
     * constructed.
     *
     * @return The grammar of a class.
     */
    public static Grammar createGrammarBuilder() {
        LexerfulGrammarBuilder grammarBuilder = LexerfulGrammarBuilder.create();

        grammarBuilder.rule(END_CLASS).is(RBRACE.getValue());
        grammarBuilder.rule(APEX_GRAMMAR).is(
                TYPE_DECLARATION,
                ApexGrammarClassOrInterfaceBodyDeclaration.createGrammarBuilder()
                .build().rule(CLASS_OR_INTERDACE_BODY_DECLARATION),
                END_CLASS
        );

        typeClass(grammarBuilder);
        extendsList(grammarBuilder);
        implementsList(grammarBuilder);
        classOrInterfaceDeclaration(grammarBuilder);
        keyword(grammarBuilder);
        lookahead(grammarBuilder);
        modifier(grammarBuilder);
        typeDeclaration(grammarBuilder);

        return grammarBuilder.build();
    }

    //HEAD
    /**
     * Grammar is created for the head of a class with the switch and whether it
     * will extend or implement otherwise.
     *
     * @param grammarBuilder lexerfullGrammarBuilder parameter.
     */
    public static void typeDeclaration(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(LBRACE).is(LBRACE.getValue());
        grammarBuilder.rule(TYPE_DECLARATION).is(
                MODIFIER,
                CLASS_OR_INTERFACE_DECLARATION,
                LBRACE
        );
    }

    /**
     * Grammar is created for a class modifier (lookahead or keyword).
     *
     * @param grammarBuilder lexerfullGrammarBuilder parameter.
     */
    public static void modifier(LexerfulGrammarBuilder grammarBuilder) {
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
     * @param grammarBuilder lexerfullGrammarBuilder parameter.
     */
    public static void lookahead(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(PUBLIC).is(PUBLIC.getValue());
        grammarBuilder.rule(STATIC).is(STATIC.getValue());
        grammarBuilder.rule(PROTECTED).is(PROTECTED.getValue());
        grammarBuilder.rule(PRIVATE).is(PRIVATE.getValue());
        grammarBuilder.rule(FINAL).is(FINAL.getValue());
        grammarBuilder.rule(ABSTRACT).is(ABSTRACT.getValue());
        grammarBuilder.rule(SYNCHRONIZED).is(SYNCHRONIZED.getValue());
        grammarBuilder.rule(NATIVE).is(NATIVE.getValue());
        grammarBuilder.rule(TRANSIENT).is(TRANSIENT.getValue());
        grammarBuilder.rule(VOLATILE).is(VOLATILE.getValue());
        grammarBuilder.rule(STRICTFP).is(STRICTFP.getValue());
        grammarBuilder.rule(ANOTATION).is(ANOTATION.getValue());
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
     * The grammar for the use of rules with/without sharing.
     *
     * @param grammarBuilder lexerfullGrammarBuilder parameter.
     */
    public static void keyword(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(WITH).is(WITH.getValue());
        grammarBuilder.rule(WITHOUT).is(WITHOUT.getValue());
        grammarBuilder.rule(SHARING).is(SHARING.getValue());
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
     * @param grammarBuilder lexerfullGrammarBuilder parameter.
     */
    public static void classOrInterfaceDeclaration(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXTENDES_OR_IMPLEMENTS).is(
                IMPLEMENTS_LIST,
                EXTENDS_LIST
        );
        grammarBuilder.rule(CLASS_OR_INTERFACE_DECLARATION).is(
                TYPE_CLASS,
                IDENTIFIER,
                EXTENDES_OR_IMPLEMENTS);
    }

    /**
     * Grammar is created to implement another class or not.
     *
     * @param grammarBuilder lexerfullGrammarBuilder parameter.
     */
    public static void implementsList(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(IMPLEMENTS).is(RULE_IMPLEMENTS_KEYWORD);
        grammarBuilder.rule(MERGE_TYPE_IMPLEMENTS).is(
                IMPLEMENTS,
                IDENTIFIER
        );
        grammarBuilder.rule(IMPLEMENTS_LIST).is(
                grammarBuilder.firstOf(
                        MERGE_TYPE_IMPLEMENTS,
                        RULE_EMPTY_KEYWORD)
        );
    }

    /**
     * Grammar is created to extend another class or just skip.
     *
     * @param grammarBuilder lexerfullGrammarBuilder parameter.
     */
    public static void extendsList(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(EXTENDS).is(RULE_EXTENDS_KEYWORD);
        grammarBuilder.rule(MERGE_TYPE_EXTENDS).is(
                EXTENDS,
                IDENTIFIER
        );
        grammarBuilder.rule(EXTENDS_LIST).is(
                grammarBuilder.firstOf(
                        MERGE_TYPE_EXTENDS,
                        RULE_EMPTY_KEYWORD)
        );
    }

    /**
     * Grammar is created to identify if a class or interface.
     *
     * @param grammarBuilder lexerfullGrammarBuilder parameter.
     */
    public static void typeClass(LexerfulGrammarBuilder grammarBuilder) {
        grammarBuilder.rule(CLASS).is(CLASS.getValue());
        grammarBuilder.rule(INTERFACE).is(INTERFACE.getValue());
        grammarBuilder.rule(TYPE_CLASS).is(
                grammarBuilder.firstOf(CLASS,
                        INTERFACE)
        );
    }
}
