/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;

import java.util.List;

/**
 * Defines an Apex language.
 */
public class Apex extends AbstractLanguage {

    /**
     * Stores a default suffix.
     */
    public static final String FILE_SUFFIXES_KEY = "sonar.apex.file.suffixes";

    /**
     * Stores an array of the Apex's suffixes.
     */
    public static final String[] DEFAULT_FILE_SUFFIXES = {"cls"};

    /**
     * Stores the Apex name.
     */
    public static final String NAME = "Apex";

    /**
     * Stores the Apex suffix.
     */
    public static final String KEY = "apex";

    /**
     * Stores the settings of the project.
     */
    private final Settings settings;

    /**
     * Default constructor to build an Apex language.
     *
     * @param settings settings of the project.
     */
    public Apex(Settings settings) {
        super(KEY, NAME);
        this.settings = settings;
    }

    /**
     * Returns an array with the key and name of the language. For example ["cls", "apex"].
     *
     * @return the array of the file suffixes.
     */
    @Override
    public String[] getFileSuffixes() {
        String[] suffixes = filterEmptyStrings(settings.getStringArray(FILE_SUFFIXES_KEY));
        return suffixes.length == 0 ? DEFAULT_FILE_SUFFIXES : suffixes;
    }

    /**
     * Returns an array that exclude the empty strings.
     *
     * @param stringArray string array to be filtered.
     * @return the array without empty strings.
     */
    private String[] filterEmptyStrings(String[] stringArray) {
        List<String> nonEmptyStrings = Lists.newArrayList();
        for (String string : stringArray) {
            if (StringUtils.isNotBlank(string.trim())) {
                nonEmptyStrings.add(string.trim());
            }
        }
        return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
    }
}
