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

import com.google.common.collect.Lists;
import com.sonar.sslr.api.Grammar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.fundacionjala.enforce.sonarqube.apex.checks.CheckList;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexMetric;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.issue.Issuable;
import org.sonar.api.issue.Issue;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.PersistenceMode;
import org.sonar.api.measures.RangeDistributionBuilder;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.api.CheckMessage;
import org.sonar.squidbridge.api.SourceCode;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.api.SourceFunction;
import org.sonar.squidbridge.indexer.QueryByParent;
import org.sonar.squidbridge.indexer.QueryByType;

/**
 *
 */
public class ApexSquidSensor implements Sensor {

    private static final Number[] FUNCTIONS_DISTRIB_BOTTOM_LIMITS = {1, 2, 4, 6, 8, 10, 12, 20, 30};
    private static final Number[] FILES_DISTRIB_BOTTOM_LIMITS = {0, 5, 10, 20, 30, 60, 90};

    private final Checks<SquidAstVisitor<Grammar>> checks;

    private final ResourcePerspectives resourcePerspectives;
    private final FileSystem fileSystem;
    private AstScanner<Grammar> scanner;
    private SensorContext context;

    public ApexSquidSensor(FileSystem fileSystem, ResourcePerspectives perspectives, CheckFactory checkFactory) {
        this.checks = checkFactory
                .<SquidAstVisitor<Grammar>>create(CheckList.REPOSITORY_KEY)
                .addAnnotatedChecks(CheckList.getChecks());
        this.fileSystem = fileSystem;
        this.resourcePerspectives = perspectives;
    }

    @Override
    public boolean shouldExecuteOnProject(Project project) {
        FilePredicates p = fileSystem.predicates();
        return fileSystem.hasFiles(p.and(p.hasType(InputFile.Type.MAIN), p.hasLanguage(Apex.KEY)));
    }

    @Override
    public void analyse(Project project, SensorContext context) {
        this.context = context;

        List<SquidAstVisitor<Grammar>> visitors = Lists.newArrayList(checks.all());
        this.scanner = ApexAstScanner.create(createConfiguration(), visitors.toArray(new SquidAstVisitor[visitors.size()]));
        FilePredicates p = fileSystem.predicates();
        scanner.scanFiles(Lists.newArrayList(fileSystem.files(p.and(p.hasType(InputFile.Type.MAIN), p.hasLanguage(Apex.KEY)))));

        Collection<SourceCode> squidSourceFiles = scanner.getIndex().search(new QueryByType(SourceFile.class));
        save(squidSourceFiles);
    }

    private ApexConfiguration createConfiguration() {
        return new ApexConfiguration(fileSystem.encoding());
    }

    private void save(Collection<SourceCode> squidSourceFiles) {
        for (SourceCode squidSourceFile : squidSourceFiles) {
            SourceFile squidFile = (SourceFile) squidSourceFile;

            InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().is(new java.io.File(squidFile.getKey())));

            saveFilesComplexityDistribution(inputFile, squidFile);
            saveFunctionsComplexityDistribution(inputFile, squidFile);
            saveMeasures(inputFile, squidFile);
            saveIssues(inputFile, squidFile);
        }
    }

    private void saveMeasures(InputFile sonarFile, SourceFile squidFile) {
        context.saveMeasure(sonarFile, CoreMetrics.FILES, squidFile.getDouble(ApexMetric.FILES));
        context.saveMeasure(sonarFile, CoreMetrics.LINES, squidFile.getDouble(ApexMetric.LINES));
        context.saveMeasure(sonarFile, CoreMetrics.NCLOC, squidFile.getDouble(ApexMetric.LINES_OF_CODE));
        context.saveMeasure(sonarFile, CoreMetrics.STATEMENTS, squidFile.getDouble(ApexMetric.STATEMENTS));
        context.saveMeasure(sonarFile, CoreMetrics.FUNCTIONS, squidFile.getDouble(ApexMetric.FUNCTIONS));
        context.saveMeasure(sonarFile, CoreMetrics.CLASSES, squidFile.getDouble(ApexMetric.CLASSES));
        context.saveMeasure(sonarFile, CoreMetrics.COMPLEXITY, squidFile.getDouble(ApexMetric.COMPLEXITY));
        context.saveMeasure(sonarFile, CoreMetrics.COMMENT_LINES, squidFile.getDouble(ApexMetric.COMMENT_LINES));
    }

    private void saveFunctionsComplexityDistribution(InputFile sonarFile, SourceFile squidFile) {
        Collection<SourceCode> squidFunctionsInFile = scanner.getIndex().search(new QueryByParent(squidFile), new QueryByType(SourceFunction.class));
        RangeDistributionBuilder complexityDistribution = new RangeDistributionBuilder(CoreMetrics.FUNCTION_COMPLEXITY_DISTRIBUTION, FUNCTIONS_DISTRIB_BOTTOM_LIMITS);
        for (SourceCode squidFunction : squidFunctionsInFile) {
            complexityDistribution.add(squidFunction.getDouble(ApexMetric.COMPLEXITY));
        }
        context.saveMeasure(sonarFile, complexityDistribution.build().setPersistenceMode(PersistenceMode.MEMORY));
    }

    private void saveFilesComplexityDistribution(InputFile sonarFile, SourceFile squidFile) {
        RangeDistributionBuilder complexityDistribution = new RangeDistributionBuilder(CoreMetrics.FILE_COMPLEXITY_DISTRIBUTION, FILES_DISTRIB_BOTTOM_LIMITS);
        complexityDistribution.add(squidFile.getDouble(ApexMetric.COMPLEXITY));
        context.saveMeasure(sonarFile, complexityDistribution.build().setPersistenceMode(PersistenceMode.MEMORY));
    }

    private void saveIssues(InputFile sonarFile, SourceFile squidFile) {
        Collection<CheckMessage> messages = squidFile.getCheckMessages();
        for (CheckMessage message : messages) {
            RuleKey ruleKey = checks.ruleKey((SquidAstVisitor<Grammar>) message.getCheck());
            Issuable issuable = resourcePerspectives.as(Issuable.class, sonarFile);

            if (issuable != null) {
                Issue issue = issuable.newIssueBuilder()
                        .ruleKey(ruleKey)
                        .line(message.getLine())
                        .message(message.getText(Locale.ENGLISH))
                        .build();
                issuable.addIssue(issue);
            }
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
