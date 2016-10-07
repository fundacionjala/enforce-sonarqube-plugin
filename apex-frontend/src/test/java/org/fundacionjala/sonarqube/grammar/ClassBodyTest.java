package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Before;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class ClassBodyTest {

    private LexerlessGrammarBuilder b;

    @Before
    public void setUp() {
        b = ApexLexer.createGrammarBuilder();
    }

    @Test
    public void testClassBody() {
        assertThat(b, ApexLexer.CLASS_BODY)
                .matches("{;}")
                .matches("{public string otherMethod(){}}");
    }
}
