/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.config.Settings;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApexTest {

    private Apex apexLanguage;
    
    @Before
    public void setup() {
        apexLanguage = new Apex(new Settings());
    }
    @Test
    public void testApexProperties() {
        assertThat(apexLanguage.getKey(), is("apex"));
        assertThat(apexLanguage.getName(), is("Apex"));
        assertThat(apexLanguage.getFileSuffixes(), is(new String[]{"cls"}));
    }

    @Test
    public void testCustomFileSuffixes() {
        Map<String, String> props = Maps.newHashMap();
        props.put(Apex.FILE_SUFFIXES_KEY, "cls,apex");

        Settings settings = new Settings();
        settings.addProperties(props);

        assertThat(apexLanguage.getFileSuffixes(), is(new String[]{"cls"}));
    }
}
