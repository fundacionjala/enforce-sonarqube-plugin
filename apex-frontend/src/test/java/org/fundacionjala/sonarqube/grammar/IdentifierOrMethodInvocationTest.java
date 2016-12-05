package org.fundacionjala.sonarqube.grammar;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.junit.Test;

import static org.fundacionjala.sonarqube.Assertions.assertThat;

public class IdentifierOrMethodInvocationTest {

    @Test
    public void testIdentifierOrMethodInvocation() {
        assertThat(ApexLexer.IDENTIFIER_OR_METHOD_INVOCATION)
                .matches("anIdentifier(andItsArguments, andItsOtherArguments)")
                .matches("this()")
                .matches("super");
    }


}
