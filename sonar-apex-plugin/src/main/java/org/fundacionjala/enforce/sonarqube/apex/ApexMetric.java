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

import java.util.Arrays;
import java.util.List;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

/**
 *
 */
public class ApexMetric implements Metrics {

    public static final Metric<String> MESSAGE = new Metric.Builder("message_key", "Message", Metric.ValueType.STRING)
            .setDescription("Apex :: This is a metric to store a well known message")
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL)
            .create();

    public static final Metric<Double> RANDOM = new Metric.Builder("random", "Random", Metric.ValueType.FLOAT)
            .setDescription("Apex :: Random value")
            .setDirection(Metric.DIRECTION_BETTER)
            .setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL)
            .create();

    @Override
    public List<Metric> getMetrics() {
        return Arrays.<Metric>asList(MESSAGE, RANDOM);
    }
}
