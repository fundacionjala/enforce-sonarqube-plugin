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

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.Grammar;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.issue.Issuable;
import org.sonar.api.issue.Issue;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureBuilder;
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

import org.fundacionjala.enforce.sonarqube.apex.checks.CheckList;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexMetric;

/**
 * Parses a flat file, connect to a web server and save measures on the whole tree of resources.
 */
public class ApexSquidSensor implements Sensor {

    /**
     * Stores an array with a limits of the function.
     */
    private static final Number[] FUNCTIONS_DISTRIB_BOTTOM_LIMITS = {1, 2, 4, 6, 8, 10, 12, 20, 30};

    /**
     * Stores and array with a limits of the file.
     */
    private static final Number[] FILES_DISTRIB_BOTTOM_LIMITS = {0, 5, 10, 20, 30, 60, 90};

    /**
     * Stores a {@link Checks} of the visitors.
     */
    private final Checks<SquidAstVisitor<Grammar>> checks;

    /**
     * Stores a perspective from resources.
     */
    private final ResourcePerspectives resourcePerspectives;

    /**
     * Stores a predicate associated with Apex language.
     */
    private final FilePredicate filePredicate;

    /**
     * Stores all source files to be analyzed.
     */
    private final FileSystem fileSystem;

    /**
     * Stores an abstract static tree scanner.
     */
    private AstScanner<Grammar> scanner;

    /**
     * Stores a sensor context.
     */
    private SensorContext context;

    /**
     * Default construct to initialize the variables.
     *
     * @param fileSystem source files.
     * @param perspectives perspective from resources.
     * @param checkFactory factory to create a check.
     */
    public ApexSquidSensor(FileSystem fileSystem, ResourcePerspectives perspectives, CheckFactory checkFactory) {
        this.checks = checkFactory
                .<SquidAstVisitor<Grammar>>create(CheckList.REPOSITORY_KEY)
                .addAnnotatedChecks(CheckList.getChecks());
        this.fileSystem = fileSystem;
        this.resourcePerspectives = perspectives;

        FilePredicates predicates = fileSystem.predicates();
        filePredicate = predicates.and(
                predicates.hasType(InputFile.Type.MAIN),
                predicates.hasLanguage(Apex.KEY));
    }

    /**
     * Analyzes if can execute in the project.
     *
     * @param project current project.
     * @return test result.
     */
    @Override
    public boolean shouldExecuteOnProject(Project project) {
        return fileSystem.hasFiles(filePredicate);
    }

    /**
     * Analyzes files for a given project.
     *
     * @param project current project.
     * @param context sensor context.
     */
    @Override
    public void analyse(Project project, SensorContext context) {
        this.context = context;

        List<SquidAstVisitor<Grammar>> visitors = Lists.newArrayList(checks.all());
        scanner = ApexAstScanner.create(createConfiguration(),
                visitors.toArray(new SquidAstVisitor[visitors.size()]));
        scanner.scanFiles(Lists.newArrayList(fileSystem.files(filePredicate)));

        Collection<SourceCode> squidSourceFiles = scanner.getIndex()
                .search(new QueryByType(SourceFile.class));
        save(squidSourceFiles);
    }

    /**
     * Returns the simple name of the scanner.
     *
     * @return the name.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    /**
     * Returns the apex configuration.
     *
     * @return the configuration.
     */
    private ApexConfiguration createConfiguration() {
        return new ApexConfiguration(fileSystem.encoding());
    }

    /**
     * Saves issues form collection of the source code.
     *
     * @param squidSourceFiles collection of the source code.
     */
    private void save(Collection<SourceCode> squidSourceFiles) {
        squidSourceFiles.forEach(squidSourceFile -> {
            SourceFile squidFile = (SourceFile) squidSourceFile;

            File file = new File(squidFile.getKey());
            InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().is(file));

            saveFilesComplexityDistribution(inputFile, squidFile);
            saveFunctionsComplexityDistribution(inputFile, squidFile);
            saveMeasures(inputFile, squidFile);
            saveIssues(inputFile, squidFile);
        });
    }

    /**
     * Saves measures from an input file and source file.
     *
     * @param sonarFile input file.
     * @param squidFile source file.
     */
    private void saveMeasures(InputFile sonarFile, SourceFile squidFile) {
        context.saveMeasure(sonarFile, CoreMetrics.FILES, squidFile.getDouble(ApexMetric.FILES));
        context.saveMeasure(sonarFile, CoreMetrics.LINES, squidFile.getDouble(ApexMetric.LINES));
        context.saveMeasure(sonarFile, CoreMetrics.NCLOC, squidFile.getDouble(ApexMetric.LINES_OF_CODE));
        context.saveMeasure(sonarFile, CoreMetrics.STATEMENTS, squidFile.getDouble(ApexMetric.STATEMENTS));
        context.saveMeasure(sonarFile, CoreMetrics.FUNCTIONS, squidFile.getDouble(ApexMetric.METHODS));
        context.saveMeasure(sonarFile, CoreMetrics.CLASSES, squidFile.getDouble(ApexMetric.CLASSES));
        context.saveMeasure(sonarFile, CoreMetrics.COMPLEXITY, squidFile.getDouble(ApexMetric.COMPLEXITY));
        context.saveMeasure(sonarFile, CoreMetrics.COMMENT_LINES, squidFile.getDouble(ApexMetric.COMMENT_LINES));
    }

    /**
     * Saves a measure with the limits of the function.
     *
     * @param sonarFile input file.
     * @param squidFile source file.
     */
    private void saveFunctionsComplexityDistribution(InputFile sonarFile, SourceFile squidFile) {
        Collection<SourceCode> squidFunctionsInFile = scanner.getIndex().search(
                new QueryByParent(squidFile),
                new QueryByType(SourceFunction.class));
        RangeDistributionBuilder complexityDistribution = new RangeDistributionBuilder(
                CoreMetrics.FUNCTION_COMPLEXITY_DISTRIBUTION,
                FUNCTIONS_DISTRIB_BOTTOM_LIMITS);
        squidFunctionsInFile.forEach(squidFunction ->
            complexityDistribution.add(squidFunction.getDouble(ApexMetric.COMPLEXITY))
        );
        context.saveMeasure(sonarFile, buildMeasure(complexityDistribution));
    }

    /**
     * Saves a measure with the limits of the file.
     *
     * @param sonarFile input file.
     * @param squidFile source file.
     */
    private void saveFilesComplexityDistribution(InputFile sonarFile, SourceFile squidFile) {
        RangeDistributionBuilder complexityDistribution = new RangeDistributionBuilder(
                CoreMetrics.FILE_COMPLEXITY_DISTRIBUTION,
                FILES_DISTRIB_BOTTOM_LIMITS);
        complexityDistribution.add(squidFile.getDouble(ApexMetric.COMPLEXITY));
        context.saveMeasure(sonarFile, buildMeasure(complexityDistribution));
    }

    /**
     * Saves issues form input file and source file.
     *
     * @param sonarFile input file.
     * @param squidFile source file.
     */
    private void saveIssues(InputFile sonarFile, SourceFile squidFile) {
        Collection<CheckMessage> messages = squidFile.getCheckMessages();
        messages.forEach(message -> {
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
        });
    }

    /**
     * Builds and returns a measure.
     *
     * @param measueBuilder builder.
     * @return the measure.
     */
    private Measure buildMeasure(MeasureBuilder measueBuilder) {
        return measueBuilder.build().setPersistenceMode(PersistenceMode.MEMORY);
    }
}
