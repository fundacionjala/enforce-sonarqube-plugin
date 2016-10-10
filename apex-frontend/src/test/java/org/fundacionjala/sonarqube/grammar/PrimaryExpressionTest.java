package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class PrimaryExpressionTest {

    @Test
    public void testPrimaryExpression() {
        assertThat(ApexLexer.PRIMARY_EXPRESSION)
                .matches("expression.methodCalling()")
                .matches("expression()");
    }
}
