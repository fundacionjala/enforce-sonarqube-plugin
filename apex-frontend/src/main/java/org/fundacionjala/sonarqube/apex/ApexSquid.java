package org.fundacionjala.sonarqube.apex;

import com.sonar.sslr.api.typed.ActionParser;
import org.fundacionjala.sonarqube.parser.ApexParser;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.visitors.TreeVisitor;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.ce.measure.Settings;

import javax.annotation.Nullable;
import java.nio.charset.Charset;
import java.util.List;

public class ApexSquid {

    private static SensorContext sensorContext;
    private InputFile sonarFile;
    private ActionParser<Tree> parser;
    private ApexAstScanner scanner;
    private Settings settings;

    public ApexSquid(@Nullable SonarComponents sonarComponents, SensorContext context, @Nullable List<ApexCheck> checks, Settings settings) {
        this.sensorContext = context;
        this.settings = settings;
        scanner = new ApexAstScanner(ApexParser.createParser(Charset.defaultCharset()), settings);
    }

    public void scanFiles(List<InputFile> filesToScan, @Nullable List<TreeVisitor> visitors) {
        scanner.scan(filesToScan, sensorContext, visitors);
    }

}