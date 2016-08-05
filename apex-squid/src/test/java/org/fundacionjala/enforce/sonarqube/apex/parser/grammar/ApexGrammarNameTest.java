/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NAME;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarNameTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(NAME);
    }

    @Test
    public void validName() {
        assertThat(parser)
                .matches("anyName.anyMethodIdentifier")
                .matches("allowedKeyword")
                .matches("method.anotherOne.otherMethod")
                .matches("trigger.isBefore")
                .matches("ChartSettingBars.XAxis");
    }
}
