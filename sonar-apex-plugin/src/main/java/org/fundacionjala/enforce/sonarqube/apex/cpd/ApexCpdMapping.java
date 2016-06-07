/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.cpd;

import net.sourceforge.pmd.cpd.Tokenizer;
import org.fundacionjala.enforce.sonarqube.apex.Apex;
import org.sonar.api.batch.AbstractCpdMapping;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.resources.Language;

import java.nio.charset.Charset;

/**
 * Implements the CpdMapping extension point.
 */
public class ApexCpdMapping extends AbstractCpdMapping {

    /**
     * Stores a charset.
     */
    private final Charset charset;

    /**
     * Stores the Apex language.
     */
    private final Apex language;

    /**
     * Default constructor to initialize the variables.
     *
     * @param language
     * @param fileSystem
     */
    public ApexCpdMapping(Apex language, FileSystem fileSystem) {
        this.language = language;
        this.charset = fileSystem.encoding();
    }

    /**
     * Builds and returns an {@link ApexTokenizer}.
     *
     * @return a tokenizer.
     */
    @Override
    public Tokenizer getTokenizer() {
        return new ApexTokenizer(charset);
    }

    /**
     * Returns the Apex language.
     *
     * @return the language.
     */
    @Override
    public Language getLanguage() {
        return language;
    }
}
