/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import com.sonar.sslr.api.Grammar;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.AbstractLineLengthCheck;

/**
 * This class defines the maximum number of the lines.
 */
@Rule(key = LineLengthCheck.CHECK_KEY)
public class LineLengthCheck extends AbstractLineLengthCheck<Grammar> {

    /**
     * Identifier key of the class.
     */
    public static final String CHECK_KEY = "LineLength";

    /**
     * Maximum number of line length.
     */
    public static final int MAXIMAL_LINE_LENGTH = 80;

    /**
     * Returns the default number line length.
     *
     * @return the line number.
     */
    @Override
    public int getMaximumLineLength() {
        return MAXIMAL_LINE_LENGTH;
    }
}
