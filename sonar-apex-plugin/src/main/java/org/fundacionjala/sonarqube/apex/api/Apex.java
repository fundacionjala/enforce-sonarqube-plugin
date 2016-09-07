package org.fundacionjala.sonarqube.apex.api;

import org.fundacionjala.sonarqube.apex.ApexPlugin;
import org.sonar.api.internal.apachecommons.lang.StringUtils;
import org.sonar.api.internal.google.common.collect.Lists;
import org.sonar.api.resources.AbstractLanguage;
import org.sonar.api.config.Settings;

import java.util.List;

public final class Apex extends AbstractLanguage {

    public static final String NAME = "Apex";
    public static final String KEY = "Apex";

    public static final String[] DEFAULT_FILE_SUFFIXES = {"cls"};

    private Settings settings;

    public Apex(Settings settings) {
        super(KEY,NAME);
        this.settings = settings;
    }

    /**
     * For example ["jav", "java"].
     * If empty, then all files in source directories are considered as sources.
     */

    public String[] getFileSuffixes() {
        String[] suffixes = filterEmptyStrings(settings.getStringArray(ApexPlugin.FILE_SUFFIXES_KEY));
        return suffixes.length == 0 ? DEFAULT_FILE_SUFFIXES : suffixes;
    }

    private static String[] filterEmptyStrings(String[] stringArray) {
        List<String> nonEmptyStrings = Lists.newArrayList();
        for (String string : stringArray) {
            if (StringUtils.isNotBlank(string.trim())) {
                nonEmptyStrings.add(string.trim());
            }
        }
        return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
    }
}
