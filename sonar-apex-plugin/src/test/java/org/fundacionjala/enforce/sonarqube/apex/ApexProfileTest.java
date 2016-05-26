/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex;

import org.fundacionjala.enforce.sonarqube.apex.checks.CheckList;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;

import static java.lang.Boolean.FALSE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApexProfileTest {

    @Test
    public void testCreateSonarWayProfile() {
        ValidationMessages validation = ValidationMessages.create();

        RuleFinder ruleFinder = buildRuleFinder();
        ApexProfile definition = new ApexProfile(ruleFinder);
        RulesProfile profile = definition.createProfile(validation);

        assertThat(profile.getLanguage(), equalTo(Apex.KEY));
        assertThat(profile.getName(), equalTo(CheckList.SONAR_WAY_PROFILE));
        assertThat(validation.hasErrors(), is(FALSE));
    }

    static RuleFinder buildRuleFinder() {
        Rule rule = mock(RuleFinder.class).findByKey(anyString(), anyString());
        return when(rule).thenAnswer((InvocationOnMock invocation) -> {
            Object[] arguments = invocation.getArguments();
            return Rule.create(String.valueOf(arguments[0]),
                    String.valueOf(arguments[1]),
                    String.valueOf(arguments[1]));
        }).getMock();
    }
}
