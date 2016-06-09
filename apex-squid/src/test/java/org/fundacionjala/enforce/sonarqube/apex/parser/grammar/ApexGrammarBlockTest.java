/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.BLOCK;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarBlockTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(BLOCK);
    }

    @Test
    public void testValidBlock() {
        assertThat(parser)
                .matches("{ integer variable;}")
                .matches("{iNTEger variable;}")
                .matches("{boolean variable;}")
                .matches("{DOUBLe value;}")
                .matches("{string variableString;}")
                .matches("{string variableString;"
                        + "string OtherString;}");
    }

    @Test
    public void testInValidBlock() {
        assertThat(parser)
                .notMatches("")
                .notMatches("{}integer variable");
    }

    @Test
    public void testValidBlockWithRecovery() {
        assertThat(parser)
                .matches("{ integer variable; ? }")
                .matches("{ ! iNTEger variable;}")
                .matches("{bool ean no variable;}")
                .matches("{DOUBLe value; "
                        + "integer wrong = 5 x ;"
                        + "string variableString;}")
                .matches("{doSomething();\n"
                        + "        integer wrong = 5%;\n"
                        + "        doOtherThing(x);}");
    }
}
