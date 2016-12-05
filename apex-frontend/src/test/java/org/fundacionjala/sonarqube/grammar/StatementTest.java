package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class StatementTest {

    @Test
    public void testStatementPositiveCase() {
        assertThat(ApexLexer.STATEMENT)
            .matches("{}")
            .matches("if(value == value){}")
            .matches("if(number != number){}else{}")
            .matches(";")
            .matches("something.something();");
    }

}
