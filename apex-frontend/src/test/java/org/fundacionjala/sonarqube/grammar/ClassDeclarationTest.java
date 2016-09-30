package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.fundacionjala.sonarqube.Assertions.assertThat;


public class ClassDeclarationTest {

    @Test
    public void testClassDeclarationGooResult(){
        LexerlessGrammarBuilder b = ApexLexer.createGrammarBuilder();
        assertThat(b, ApexLexer.CLASS_DECLARATION)
        .matches("class SomeIdentifier {}")
        .matches("class Alphanumeric_123_identitifier{}")
        .matches("class someClass_123 extends anotherClass {}");
    }
}
