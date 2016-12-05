package org.fundacionjala.sonarqube.grammar;

import com.thoughtworks.xstream.persistence.AbstractFilePersistenceStrategy;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Before;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class ClassOrInterfaceMemberTest {

    @Test
    public void testClassOrInterfaceMember() {
        assertThat(ApexLexer.MEMBER_DECLARATION)
        .matches("public string member(){}")
        .matches(";");
    }


}
