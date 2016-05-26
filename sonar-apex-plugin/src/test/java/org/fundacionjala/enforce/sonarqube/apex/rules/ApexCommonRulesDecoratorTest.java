/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.rules;

import org.fundacionjala.enforce.sonarqube.apex.Apex;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.component.ResourcePerspectives;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ApexCommonRulesDecoratorTest {

    private ApexCommonRulesDecorator decorator;

    @Before
    public void setup() {
        decorator = new ApexCommonRulesDecorator(mock(FileSystem.class),
                mock(CheckFactory.class),
                mock(ResourcePerspectives.class));
    }

    @Test
    public void testRulesDecoratorProperties() throws Exception {
        assertThat(decorator.language(), equalTo(Apex.KEY));
    }
}
