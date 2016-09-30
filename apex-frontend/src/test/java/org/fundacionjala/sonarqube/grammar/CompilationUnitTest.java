package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class CompilationUnitTest {

    @Test
    public void testCompilationUnit() {
        LexerlessGrammarBuilder b = ApexLexer.createGrammarBuilder();
        assertThat(ApexLexer.COMPILATION_UNIT)
                .matches("public class something_123 extends anotherClass{}")
                .matches("public with sharing class something_223 {}");
    }
}
