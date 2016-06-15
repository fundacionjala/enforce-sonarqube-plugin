/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.rules;

import org.fundacionjala.enforce.sonarqube.apex.checks.CheckList;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Rule;
import org.sonar.squidbridge.api.CodeVisitor;

import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApexRulesDefinitionTest {

    private ApexRulesDefinition rulesDefinition;

    @Before
    public void setUp() {
        rulesDefinition = new ApexRulesDefinition();
    }

    @Test
    public void test() {
        rulesDefinition = new ApexRulesDefinition();
        RulesDefinition.Context context = new RulesDefinition.Context();
        rulesDefinition.define(context);
        RulesDefinition.Repository repository = context.repository("apex");

        assertThat(repository.name(), equalTo("SonarQube"));
        assertThat(repository.language(), equalTo("apex"));
        assertThat(repository.rules().size(), is(CheckList.getChecks().size()));
    }

    @Test
    public void testInvalidChecks() throws Exception {
        RulesDefinition.Context context = new RulesDefinition.Context();
        RulesDefinition.NewRepository newRepository = context.createRepository("test", "java");
        newRepository.createRule("cardinality");
        newRepository.createRule("A1005");
        try {
            rulesDefinition.newRule(CheckWithNoAnnotation.class, newRepository);
        } catch (IllegalArgumentException illegaArgumentException) {
            assertThat(illegaArgumentException)
                    .hasMessage(String.format("No Rule annotation was found on class %s", CheckWithNoAnnotation.class.getName()));
        }

        try {
            rulesDefinition.newRule(EmptyRuleKey.class, newRepository);
        } catch (IllegalArgumentException illegaArgumentException) {
            assertThat(illegaArgumentException)
                    .hasMessage(String.format("No key is defined in Rule annotation of class %s", EmptyRuleKey.class.getName()));
        }

        try {
            rulesDefinition.newRule(UnregisteredRule.class, newRepository);
        } catch (IllegalStateException illegalStateException) {
            assertThat(illegalStateException)
                    .hasMessage(String.format("No rule was created for class %s in test", UnregisteredRule.class.getName()));
        }
        rulesDefinition.newRule(CorrectRule.class, newRepository);
    }

    private class CheckWithNoAnnotation implements CodeVisitor {
    }

    @Rule(key = "")
    private class EmptyRuleKey implements CodeVisitor {
    }

    @Rule(key = "A1001")
    private class UnregisteredRule implements CodeVisitor {
    }

    @Rule(key = "A1005")
    private class CorrectRule implements CodeVisitor {
    }

}
