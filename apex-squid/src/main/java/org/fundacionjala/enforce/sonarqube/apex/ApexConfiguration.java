/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex;

import org.sonar.squidbridge.api.SquidConfiguration;

import java.nio.charset.Charset;

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
