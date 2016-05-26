/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import com.sonar.sslr.api.Grammar;
import org.fundacionjala.enforce.sonarqube.apex.checks.Tags;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.AbstractLineLengthCheck;

/**
 * This class defines the maximum number of the lines.
 */
@Rule(
        key = LineLengthCheck.CHECK_KEY,
        priority = Priority.MINOR,
        name = "Lines should not be too long",
        tags = Tags.CONVENTION
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("1min")
@ActivatedByDefault
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
