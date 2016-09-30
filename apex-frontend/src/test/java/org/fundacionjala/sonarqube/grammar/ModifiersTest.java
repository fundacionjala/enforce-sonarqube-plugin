package org.fundacionjala.sonarqube.grammar;

import static org.fundacionjala.sonarqube.Assertions.*;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

public class ModifiersTest {

    @Test
    public void testModifiers() {
        LexerlessGrammarBuilder b = ApexLexer.createGrammarBuilder();
        assertThat(b, ApexLexer.MODIFIERS)
                .matches("public")
                .matches("protected")
                .matches("private")
                .matches("public static");
    }

    @Test
    public void testModifiersNegetiveResults() {
        assertThat(ApexLexer.MODIFIERS)
                .notMatches("class")
                .notMatches("somethingElse");
    }

}
