package org.fundacionjala.sonarqube.apex;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.issue.NoSonarFilter;

import java.util.List;

public class Measurer {

    private FileSystem fileSystem;
    private SensorContext context;
    private NoSonarFilter noSonarFilter;
    private int methods;
    private int complexityInMethods;
    private int classes;


    public Measurer(FileSystem fileSystem, SensorContext context, NoSonarFilter noSonarFilter) {
        this.fileSystem = fileSystem;
        this.context = context;
        this.noSonarFilter = noSonarFilter;
    }

    public void scanfile(SensorContext context) {
        fileSystem = context.fileSystem();
        methods = 0;
        complexityInMethods = 0;
        classes = 0;
        PublicApiChecker publicApiChecker = new PublicApiChecker();
    }

}
