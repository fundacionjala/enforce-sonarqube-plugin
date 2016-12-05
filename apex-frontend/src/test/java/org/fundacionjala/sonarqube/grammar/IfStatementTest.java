package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class IfStatementTest {

    @Test
    public void testIfStatementCorrect() {
        LexerlessGrammarBuilder b = ApexLexer.createGrammarBuilder();
        assertThat(b, ApexLexer.IF_STATEMENT)
              .matches("if(value == value){}")
              .matches("if(number && number){}else{}");
    }

}
