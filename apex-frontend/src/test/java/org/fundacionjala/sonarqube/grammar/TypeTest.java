package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Before;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class TypeTest {

    private LexerlessGrammarBuilder b;

    @Before
    public void setUp() {
        b = ApexLexer.createGrammarBuilder();
    }

    @Test
    public void testTypePositiveRules() {
        assertThat(b, ApexLexer.TYPE)
        .matches("transient")
        .matches("string")
        .matches("anyType");
    }

    @Test
    public void testTypeNegativeRules() {
        assertThat(b, ApexLexer.TYPE)
                .notMatches("123type");
    }

}
