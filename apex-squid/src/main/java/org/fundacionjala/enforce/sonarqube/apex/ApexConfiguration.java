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

import java.nio.charset.Charset;
import org.sonar.squidbridge.api.SquidConfiguration;

/**
 * This class contains the configuration to create an Abstract syntax tree.
 */
public class ApexConfiguration extends SquidConfiguration {

    /**
     * Represents a value to ignore header comments.
     */
    private boolean ignoreHeaderComments;

    /**
     * Default constructor that requires charset.
     *
     * @param charset to be set.
     */
    public ApexConfiguration(Charset charset) {
        super(charset);
    }

    /**
     * Returns ignore header comments.
     *
     * @return the ignore header value.
     */
    public boolean getIgnoreHeaderComments() {
        return ignoreHeaderComments;
    }

    /**
     * Sets ignore header comments.
     *
     * @param ignoreHeaderComments to be set.
     */
    public void setIgnoreHeaderComments(boolean ignoreHeaderComments) {
        this.ignoreHeaderComments = ignoreHeaderComments;
    }
}
