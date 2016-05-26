/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FORMAL_PARAMETER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarFormalParameterTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(FORMAL_PARAMETER);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("integer varName")
                .matches("final bool VarName")
                .matches("CALCULAtor calc")
                .matches("CLassName classname")
                .matches("Integer integer")
                .matches("Object object");
    }
}
