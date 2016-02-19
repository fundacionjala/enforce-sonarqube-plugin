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
package org.fundacionjala.enforce.sonarqube.apex.cpd;

import java.nio.charset.Charset;

import net.sourceforge.pmd.cpd.Tokenizer;
import org.fundacionjala.enforce.sonarqube.apex.Apex;
import org.sonar.api.batch.AbstractCpdMapping;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.resources.Language;

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
     * @return
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
