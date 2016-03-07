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

import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;

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
