package org.fundacionjala.sonarqube.grammar;


import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketEndPointInfo;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Before;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class FormalParameterTest {

    private LexerlessGrammarBuilder b;

    @Before
    public void setUp() {
        b = ApexLexer.createGrammarBuilder();
    }

    @Test
    public void testPositiveFormalParameterTest() {
        assertThat(ApexLexer.FORMAL_PARAMETER)
                .matches("integer someVariable");
    }



}
