/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.api;

import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.fest.assertions.Assertions.assertThat;

public class ApexMetricTest {

    private Set<ApexMetric> metrics;

    @Before
    public void setup() {
        metrics = EnumSet.allOf(ApexMetric.class);
    }

    @Test
    public void testNumberOfApexMetricTypes() {
        assertThat(metrics).hasSize(8);
    }

    @Test
    public void testDefaultValuesOfApexMetrics() {
        Stream<ApexMetric> stream = metrics.stream();
        stream.forEach(metric -> {
            assertThat(metric.getName()).isEqualTo(metric.name());
            assertThat(metric.isCalculatedMetric()).isFalse();
            assertThat(metric.aggregateIfThereIsAlreadyAValue()).isTrue();
            assertThat(metric.isThereAggregationFormula()).isTrue();
            assertThat(metric.getCalculatedMetricFormula()).isNull();
        });
    }
}
