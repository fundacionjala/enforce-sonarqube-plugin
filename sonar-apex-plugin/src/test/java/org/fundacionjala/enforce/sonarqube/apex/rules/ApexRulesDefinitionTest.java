/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.rules;

import org.fundacionjala.enforce.sonarqube.apex.checks.CheckList;
import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApexRulesDefinitionTest {

    @Test
    public void test() {
        ApexRulesDefinition rulesDefinition = new ApexRulesDefinition();
        RulesDefinition.Context context = new RulesDefinition.Context();
        rulesDefinition.define(context);
        RulesDefinition.Repository repository = context.repository("apex");

        assertThat(repository.name(), equalTo("SonarQube"));
        assertThat(repository.language(), equalTo("apex"));
        assertThat(repository.rules().size(), is(CheckList.getChecks().size()));
    }
}
