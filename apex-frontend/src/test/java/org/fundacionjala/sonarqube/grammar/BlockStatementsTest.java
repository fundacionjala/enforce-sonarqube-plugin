package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class BlockStatementsTest {

    @Test
    public void testBlockStatementPositive() {
        assertThat(ApexLexer.BLOCK_STATEMENTS)
            .matches("{}" +
                    "{}" +
                    "if(value == value){}" +
                    "else{}");
    }
}
