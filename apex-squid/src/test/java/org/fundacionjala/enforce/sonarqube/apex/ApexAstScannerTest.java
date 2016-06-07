/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.Grammar;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexMetric;
import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.api.SourceProject;
import org.sonar.squidbridge.indexer.QueryByType;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class ApexAstScannerTest {

    private ApexConfiguration apexConfiguration;
    private SourceFile sourceFile;

    @Before
    public void setup() {
        apexConfiguration = new ApexConfiguration(Charsets.UTF_8);
    }

    @Test
    public void testTheNumberOfScannedFiles() {
        AstScanner<Grammar> scanner = ApexAstScanner.create(apexConfiguration);
        scanner.scanFiles(ImmutableList.of(
                new File("src/test/resources/metrics/methods.cls"),
                new File("src/test/resources/metrics/lines.cls")));
        SourceProject project = buildProject(scanner);
        assertThat(project.getInt(ApexMetric.FILES)).isEqualTo(2);
    }

    @Test
    public void testTheNumberOfScannedLines() {
        sourceFile = ApexAstScanner.scanFile(new File("src/test/resources/metrics/lines.cls"));
        assertThat(sourceFile.getInt(ApexMetric.LINES)).isEqualTo(12);
    }

    @Test
    public void testTheNumberOfScannedLinesOfCode() {
        sourceFile = ApexAstScanner.scanFile(new File("src/test/resources/metrics/lines.cls"));
        assertThat(sourceFile.getInt(ApexMetric.LINES_OF_CODE)).isEqualTo(9);
    }

    @Test
    public void testTheNumberOfScannedFunctions() {
        sourceFile = ApexAstScanner.scanFile(new File("src/test/resources/metrics/methods.cls"));
        assertThat(sourceFile.getInt(ApexMetric.METHODS)).isEqualTo(2);
    }

    @Test
    public void testTheNumberOfScannedStatements() {
        sourceFile = ApexAstScanner.scanFile(new File("src/test/resources/metrics/statements.cls"));
        assertThat(sourceFile.getInt(ApexMetric.STATEMENTS)).isEqualTo(2);
    }

    @Test
    public void testTheNumberOfScannedClasses() {
        sourceFile = ApexAstScanner.scanFile(new File("src/test/resources/metrics/classes.cls"));
        assertThat(sourceFile.getInt(ApexMetric.CLASSES)).isEqualTo(2);
    }

    @Test
    public void testTheNumberOfScannedComplexity() {
        sourceFile = ApexAstScanner.scanFile(new File("src/test/resources/metrics/complexity.cls"));
        assertThat(sourceFile.getInt(ApexMetric.COMPLEXITY)).isEqualTo(3);
    }

    @Test
    public void testTheNumberOfScannedComments() {
        sourceFile = ApexAstScanner.scanFile(new File("src/test/resources/metrics/comments.cls"));
        assertThat(sourceFile.getInt(ApexMetric.CLASSES)).isEqualTo(1);
        assertThat(sourceFile.getInt(ApexMetric.COMMENT_LINES)).isEqualTo(9);
    }

    private SourceProject buildProject(AstScanner<Grammar> scanner) {
        return (SourceProject) scanner.getIndex().search(new QueryByType(SourceProject.class)).iterator().next();
    }
}
