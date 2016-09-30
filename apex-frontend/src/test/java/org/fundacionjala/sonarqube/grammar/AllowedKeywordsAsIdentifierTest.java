package org.fundacionjala.sonarqube.grammar;

import static org.fundacionjala.sonarqube.Assertions.*;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

public class AllowedKeywordsAsIdentifierTest {

    @Test
    public void testAllowedKeywordsAsIdentifier() {
        LexerlessGrammarBuilder b = ApexLexer.createGrammarBuilder();
        assertThat(b, ApexLexer.ALLOWED_KEYWORDS_AS_IDENTIFIER)
                .matches("something")
                .matches("transient")
                .matches("returning")
                .matches("search")
                .matches("stat")
                .matches("convertcurrency")
                .matches("savepoint")
                .matches("tolabel")
                .matches("sharing")
                .matches("get")
                .matches("after")
                .matches("before")
                .matches("first")
                .matches("last")
                .matches("category")
                .matches("network")
                .matches("iterator");
    }

    @Test
    public void testAllowedKeywordsAsIdentifierNegativeResult() {
        assertThat(ApexLexer.ALLOWED_KEYWORDS_AS_IDENTIFIER)
                .notMatches("two words")
                .notMatches("$trangeCharacters");
    }

}
