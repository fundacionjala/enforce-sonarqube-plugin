/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPLICIT_CONSTRUCTOR_INVOCATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarExplicitConstructorInvocationTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(EXPLICIT_CONSTRUCTOR_INVOCATION);
    }

    @Test
    public void testValidExplicitConstrutorInvocations() {
        assertThat(parser)
                .matches("this(something);")
                .matches("super(something);")
                .matches("super(k);")
                .matches("This(something);");
    }

    @Test
    public void testInvalidExplicitConstructorInvocations() {
        assertThat(parser)
                .notMatches("super(wrong parameter);")
                .notMatches("super(explicitWithoutSemicolon)")
                .notMatches("super(two parameters);")
                .notMatches("this(four parameters for this)");
    }
}
