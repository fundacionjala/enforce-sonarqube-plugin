package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Before;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class BlockTest {

    private LexerlessGrammarBuilder b;

    @Before
    public void setUp(){
        b = ApexLexer.createGrammarBuilder();
    }

    @Test
    public void testBlockTestPositive() {
        assertThat(b, ApexLexer.BLOCK)
        .matches("{" +
                "if(value && value){}" +
                "}")
        .matches("{if(expression != anotherExpression){}else{}}");
    }


}
