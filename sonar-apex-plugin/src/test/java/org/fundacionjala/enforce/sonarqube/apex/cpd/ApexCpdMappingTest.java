/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.cpd;

import org.fundacionjala.enforce.sonarqube.apex.Apex;
import org.junit.Test;
import org.sonar.api.batch.fs.FileSystem;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ApexCpdMappingTest {

    @Test
    public void testApexCpdMappingProperties() {
        Apex language = mock(Apex.class);
        FileSystem fileSystem = mock(FileSystem.class);
        ApexCpdMapping mapping = new ApexCpdMapping(language, fileSystem);
        assertThat(mapping.getLanguage(), equalTo(language));
        assertThat(mapping.getTokenizer(), instanceOf(ApexTokenizer.class));
    }
}
