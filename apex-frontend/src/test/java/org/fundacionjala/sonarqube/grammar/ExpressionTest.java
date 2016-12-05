package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class ExpressionTest {

    @Test
    public void testExpressionsValid() {
        assertThat(ApexLexer.EXPRESSION)
                .matches("something ? someResult : anotherResult")
                .matches("something || something")
                .matches("something && something")
                .matches("variable.isValid() == booleanVariable")
                .matches("this(argument1, argument2, argument3)")
                .matches("this.this");
    }

}
