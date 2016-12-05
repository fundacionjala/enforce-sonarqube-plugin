package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexGrammar;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class ArgumentsTest {

    @Test
    public void testArgumentsRule() {
        LexerlessGrammarBuilder b = ApexLexer.createGrammarBuilder();
        assertThat(b, ApexLexer.ARGUMENTS)
        .matches("(someArgument, anotherArgument)")
        .matches("(expression)")
        .matches("()");
    }
}
