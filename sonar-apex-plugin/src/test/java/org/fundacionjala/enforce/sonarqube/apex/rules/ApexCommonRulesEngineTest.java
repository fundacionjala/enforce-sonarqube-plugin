/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.rules;

import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.commonrules.api.CommonRulesRepository;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApexCommonRulesEngineTest {

    private ApexCommonRulesEngine engine;

    @Before
    public void setup() {
        engine = new ApexCommonRulesEngine();
    }

    @Test
    public void testShouldProvideExpectedExtensions() {
        List<CommonRulesRepository> repositories = engine.provide();
        assertThat(repositories.size(), is(1));
    }

    @Test
    public void testEnableCommonRules() {
        CommonRulesRepository repository = engine.newRepository();
        Set<String> ruleKeys = repository.enabledRuleKeys();
        assertThat(ruleKeys.size(), is(4));
        assertThat(ruleKeys, hasItem("InsufficientCommentDensity"));
    }
}
