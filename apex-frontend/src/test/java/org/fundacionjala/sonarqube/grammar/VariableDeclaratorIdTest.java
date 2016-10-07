package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class VariableDeclaratorIdTest {

    @Test
    public void testVariableDeclaratorIdPositive() {
        assertThat(ApexLexer.VARIABLE_DECLARATOR_ID)
                .matches("identifier")
                .matches("string")
                .matches("variableName");
    }
}
