/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.api;

import org.sonar.squidbridge.measures.CalculatedMetricFormula;
import org.sonar.squidbridge.measures.MetricDef;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * This class defines the type of metrics.
 */
public enum ApexMetric implements MetricDef {
    FILES,
    LINES,
    LINES_OF_CODE,
    STATEMENTS,
    METHODS,
    CLASSES,
    COMPLEXITY,
    COMMENT_LINES;

    /**
     * Returns the name of metric.
     *
     * @return the name.
     */
    @Override
    public String getName() {
        return name();
    }

    /**
     * Indicates if is calculated metric.
     *
     * @return false by default.
     */
    @Override
    public boolean isCalculatedMetric() {
        return FALSE;
    }

    /**
     * Indicates if can aggregate a value.
     *
     * @return true by default.
     */
    @Override
    public boolean aggregateIfThereIsAlreadyAValue() {
        return TRUE;
    }

    /**
     * Indicates if can aggregate a formula.
     *
     * @return true by default.
     */
    @Override
    public boolean isThereAggregationFormula() {
        return TRUE;
    }

    /**
     * Returns a calculated metric formula.
     *
     * @return null by default.
     */
    @Override
    public CalculatedMetricFormula getCalculatedMetricFormula() {
        return null;
    }
}
