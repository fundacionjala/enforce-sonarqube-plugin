/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ACCESSOR_DECLARATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarAccessorDeclarationTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(ACCESSOR_DECLARATION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("public get;")
                .matches("get;")
                .matches("private static set;")
                .matches("public set;")
                .matches("private set;")
                .matches("public static get;")
                .matches("PuBLIC sTatic SET;")
                .matches("public STATIc gEt;");
    }

}
