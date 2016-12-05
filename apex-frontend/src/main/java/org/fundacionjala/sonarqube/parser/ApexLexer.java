package org.fundacionjala.sonarqube.parser;

import com.sonar.sslr.api.GenericTokenType;
import org.apache.commons.lang.ArrayUtils;
import org.fundacionjala.sonarqube.api.ApexKeyword;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import java.util.Arrays;

import static org.fundacionjala.sonarqube.api.ApexPunctuator.*;

public enum ApexLexer implements GrammarRuleKey{

    ALLOWED_KEYWORDS_AS_IDENTIFIER,
    COMPILATION_UNIT,
    TYPE_DECLARATION,
    CLASS_BODY,
    MODIFIERS,
    CLASS_DECLARATION,
    SPACING,
    SPECIAL_KEYWORDS_AS_IDENTIFIER,
    KEYWORD,
    IDENTIFIER,
    EOF,
    EXTENDS_DECLARATION,
    MEMBER_DECLARATION,
    BLOCK, BLOCK_STATEMENTS,
    STATEMENT,
    EMPTY_STATEMENT,
    IF_STATEMENT,
    EXPRESSION_STATEMENT,
    EXPRESSION,
    CONDITIONAL_EXPRESSION,
    PRIMARY_SUFFIX,
    FORMAL_PARAMETERS,
    TYPE_PARAMETERS,
    TYPE,
    IDENTIFIER_OR_METHOD_INVOCATION,
    TYPE_ARGUMENTS,
    ARGUMENTS,
    FORMAL_PARAMETERS_DECLS_REST,
    FORMAL_PARAMETER,
    VARIABLE_DECLARATOR_ID,
    BLOCK_STATEMENT,
    CONDITIONAL_OR_EXPRESSION,
    CONDITIONAL_AND_EXPRESSION,
    EQUALITY_EXPRESSION,
    PRIMARY_EXPRESSION,
    PRIMARY_PREFIX,
    LOCAL_VARIABLE_DECLARATION;

    public static LexerlessGrammarBuilder  createGrammarBuilder() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
        punctuators(b);
        keywords(b);
        literals(b);
        b.setRootRule(COMPILATION_UNIT);
        return b;
    }

    private static void punctuator(LexerlessGrammarBuilder b, GrammarRuleKey ruleKey, String value) {
        b.rule(ruleKey).is(value, SPACING);
    }

    private static void punctuator(LexerlessGrammarBuilder b, GrammarRuleKey ruleKey, String value, Object element) {
        b.rule(ruleKey).is(value, element, SPACING);
    }

    private static void punctuators(LexerlessGrammarBuilder b) {
        punctuator(b, LBRACE, "{");
        punctuator(b, RBRACE, "}");
        punctuator(b, DOT, ".");
        punctuator(b, COMMA, ",");
        punctuator(b, COLON, ":");
        punctuator(b, SEMICOLON, ";");
        punctuator(b, EQUAL, "==");
        punctuator(b, NOTEQUAL, "!=");
        punctuator(b, QUERY, "?");
        punctuator(b, OROR, "||");
        punctuator(b, LPAREN, "(");
        punctuator(b, RPAREN, ")");
        punctuator(b, AND, "&", b.nextNot(b.firstOf("=", "&")));
        punctuator(b, ANDAND, "&&");
    }

    private static void keywords(LexerlessGrammarBuilder b) {
        for (ApexKeyword tokenType : ApexKeyword.values()) {
            b.rule(tokenType).is(tokenType.getValue(), SPACING);
        }
        String[] keywords = ApexKeyword.keywordValues();
        Arrays.sort(keywords);
        ArrayUtils.reverse(keywords);
        b.rule(KEYWORD).is(
                b.firstOf(
                        keywords[0],
                        keywords[1],
                        ArrayUtils.subarray(keywords, 2, keywords.length)));
    }

    private static void literals(LexerlessGrammarBuilder b) {
        b.rule(SPACING).is(
                b.skippedTrivia(whitespace(b)),
                b.zeroOrMore(
                        b.commentTrivia(b.firstOf(inlineComment(b), multilineComment(b))),
                        b.skippedTrivia(whitespace(b))));

        b.rule(EOF).is(b.token(GenericTokenType.EOF, b.endOfInput()));
        b.rule(IDENTIFIER).is(
                b.nextNot(KEYWORD),
                apexIdentifier(b),
                SPACING
        );
    }

    private static Object whitespace(LexerlessGrammarBuilder b) {
        return b.regexp("\\s*+");
    }

    private static Object inlineComment(LexerlessGrammarBuilder b) {
        return b.regexp("//[^\\n\\r]*+");
    }

    private static Object multilineComment(LexerlessGrammarBuilder b) {
        return b.regexp("/\\*[\\s\\S]*?\\*\\/");
    }

    private static Object apexIdentifier(LexerlessGrammarBuilder b) {
        return b.regexp("\\p{Alpha}(\\p{Alpha}|\\p{Digit}|\\_)*");
    }
}
