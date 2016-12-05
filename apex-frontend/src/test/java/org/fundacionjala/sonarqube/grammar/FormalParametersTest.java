package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Before;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class FormalParametersTest {

    private LexerlessGrammarBuilder b;

    @Before
    public void setUp() {
        b = ApexLexer.createGrammarBuilder();
    }

    @Test
    public void testFormalParameters() {
        assertThat(b, ApexLexer.FORMAL_PARAMETERS)
                .matches("( int someparameter )")
                .matches("( String value, int anotherparameter )");
    }
}
