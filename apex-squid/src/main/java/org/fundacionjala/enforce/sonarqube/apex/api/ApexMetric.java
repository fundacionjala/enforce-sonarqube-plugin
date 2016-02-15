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
    FUNCTIONS,
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
