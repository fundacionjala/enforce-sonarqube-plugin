package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Before;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class MemberDeclarationTest {

    private LexerlessGrammarBuilder b;

    @Before
    public void setUp() {
        b = ApexLexer.createGrammarBuilder();
    }

    @Test
    public void testClassIrinterfaceDeclaration() {
        assertThat(b, ApexLexer.MEMBER_DECLARATION)
                .matches(";")
                .matches("public string something(){" +
                        "if(value == value){}" +
                        "}")
                .matches("public boolean isValid() {" +
                        "if(number != number) {" +
                        "}" +
                        "else{" +
                        "}" +
                        "}");
    }


}
