package org.fundacionjala.sonarqube.parser;

import com.sonar.sslr.api.GenericTokenType;
import org.apache.commons.lang.ArrayUtils;
import org.fundacionjala.sonarqube.api.ApexKeyword;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import java.util.Arrays;

public enum ApexLexer implements GrammarRuleKey{

    COMPILATION_UNIT,
    MODIFIERS,
    SPACING,
    KEYWORD,
    TYPE_DECLARATION,
    EOF;

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
}
