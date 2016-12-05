package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class EmptyStatementTest {

    @Test
    public void testEmptyStatement() {
        assertThat(ApexLexer.EMPTY_STATEMENT)
                .matches(";");
    }

}
