package org.fundacionjala.sonarqube.apex;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.measures.FileLinesContextFactory;

import java.io.File;
import java.util.List;

public class SonarComponents {
    private final FileLinesContextFactory fileLinesContextFactory;
    private final CheckFactory checkFactory;
    private SensorContext context;
    private List<ApexCheck> checks;

    public SonarComponents(FileLinesContextFactory fileLinesContextFactory, CheckFactory checkFactory) {
        this.fileLinesContextFactory = fileLinesContextFactory;
        this.checkFactory = checkFactory;
    }

    public void setContext(SensorContext context) {
        this.context = context;
    }

    public List<ApexCheck> getChecks() {
        return checks;
    }

    public InputFile inputFromIOFile(File file) {
        FileSystem fileSystem = context.fileSystem();
        return fileSystem.inputFile(fileSystem.predicates().is(file));
    }

}
