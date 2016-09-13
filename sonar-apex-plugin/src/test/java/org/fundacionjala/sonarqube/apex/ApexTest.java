package org.fundacionjala.sonarqube.apex;

import org.fundacionjala.sonarqube.apex.api.Apex;
import org.junit.Test;
import org.sonar.api.config.Settings;

import static org.fest.assertions.Assertions.assertThat;

public class ApexTest {

    @Test
    public void testGetApexFileSuffixes() {
        Settings settings = new Settings();
        Apex language = new Apex(settings);
        assertThat(language.getFileSuffixes()).containsOnly(".cls");

        settings.setProperty(ApexPlugin.FILE_SUFFIXES_KEY, "");
        assertThat(language.getFileSuffixes()).containsOnly(".cls");
    }

}
