/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARGUMENTS;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarArgumentsTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(ARGUMENTS);
    }

    @Test
    public void testValidArguments() {
        assertThat(parser)
                .matches("(argument)")
                .matches("(argument, null)")
                .matches("()")
                .matches("(UPPERcaseArgument)");

    }

    @Test
    public void testInvalidArguments() {
        assertThat(parser)
                .notMatches("")
                .notMatches(")(")
                .notMatches("argumentOutOfParenthesis()");

    }

}
