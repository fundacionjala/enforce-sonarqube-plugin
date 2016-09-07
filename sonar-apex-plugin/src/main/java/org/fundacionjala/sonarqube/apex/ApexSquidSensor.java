package org.fundacionjala.sonarqube.apex;

import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
import org.fundacionjala.sonarqube.apex.api.Apex;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.config.Settings;
import org.sonar.api.internal.google.common.collect.Lists;
import org.sonar.api.issue.NoSonarFilter;

import java.util.ArrayList;

public class ApexSquidSensor implements Sensor {

    private final Apex apex;
    private FilePredicate filePredicate;
    private final ApexChecks checks;

    public ApexSquidSensor(Apex apex, CheckFactory checkFactory, NoSonarFilter noSonarFilter) {
        this.apex = apex;
        this.checks = ApexChecks.createApexCheck(checkFactory);
    }

    /**
     * Populate {@link SensorDescriptor} of this sensor.
     *
     * @param descriptor Describe what a Sensor is doing. Information may be used
     *                   by the platform to log interesting information or
     *                   perform some optimization.
     */
    public void describe(SensorDescriptor descriptor) {
        descriptor.onlyOnLanguage(Apex.KEY).name("ApexSquidSensor");
    }

    /**
     * The actual sensor code.
     *
     * @param context
     */
    public void execute(SensorContext context) {
        Settings settings = context.settings();
        FileSystem fileSystem = context.fileSystem();
        filePredicate = context.fileSystem().predicates().and(
                fileSystem.predicates().hasType(InputFile.Type.MAIN),
                fileSystem.predicates().hasLanguage(Apex.KEY));
        ArrayList<InputFile> inputFile = Lists.newArrayList(fileSystem.inputFiles(filePredicate));

    }
}
