/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.Extension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApexPluginTest {

    private ApexPlugin apexPlugin;
    
    @Before
    public void setup() {
        apexPlugin = new ApexPlugin();
    }
    @Test
    public void testNumberOfExtensions() {
        List<Extension> extensions = apexPlugin.getExtensions();
        assertThat(extensions.size(), is(7));
    }
}
