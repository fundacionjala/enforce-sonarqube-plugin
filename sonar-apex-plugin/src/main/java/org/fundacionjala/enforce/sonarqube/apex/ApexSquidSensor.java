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

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

/**
 *
 */
public class ApexSquidSensor implements Sensor {

    private static final Logger LOG = Loggers.get(ApexSquidSensor.class);

    private final Settings settings;
    private final FileSystem fs;

    public ApexSquidSensor(Settings settings, FileSystem fs) {
        this.settings = settings;
        this.fs = fs;
    }

    @Override
    public void analyse(Project project, SensorContext sensorContext) {
        LOG.info("ApexSquidSensor.analyse(...) method called for Project: " + project.getName());

        String value = settings.getString("org.sonar.fundacionjala.property");
        LOG.info("{}={}", "org.sonar.fundacionjala.property", value);
        for (InputFile inputFile : fs.inputFiles(fs.predicates().hasLanguage("java"))) {
            sensorContext.saveMeasure(inputFile, new Measure<String>(ApexMetric.MESSAGE, value));
        }
    }

    @Override
    public boolean shouldExecuteOnProject(Project project) {
        LOG.info("ProjectName: " + project.getName());
        LOG.info("ProjectScope: " + project.getScope());
        LOG.info("Project isRoot ?" + project.isRoot());
        LOG.info("Project isModule ?" + project.isModule());

        return fs.hasFiles(fs.predicates().hasLanguage("apex"));
    }
}
