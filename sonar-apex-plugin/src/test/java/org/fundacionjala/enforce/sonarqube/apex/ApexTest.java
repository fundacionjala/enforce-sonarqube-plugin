/*
 * The MIT License
 *
 * Copyright 2016 Fundacion Jala.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.fundacionjala.enforce.sonarqube.apex;

import java.util.Map;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.config.Settings;

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
