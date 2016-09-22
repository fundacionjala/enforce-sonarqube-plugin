package org.fundacionjala.sonarqube.apex;

import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.typed.ActionParser;
import org.fundacionjala.sonarqube.parser.ApexParser;
import org.fundacionjala.sonarqube.tree.Tree;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

public class ApexSquid {

    private SensorContext sensorContext;
    private InputFile sonarFile;
    private ActionParser<Tree> parser;
    private ApexAstScanner scanner;

    public ApexSquid(@Nullable SonarComponents sonarComponents, SensorContext context, @Nullable List<ApexCheck> checks) {
        this.sensorContext = sensorContext;
        scanner = new ApexAstScanner(ApexParser.createParser(Charset.defaultCharset()));
    }

    public void scanFiles(List<File> filesToScan) {
        scanner.scan(filesToScan);
    }

}