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

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.Grammar;
import java.io.File;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexMetric;
import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.api.SourceProject;
import org.sonar.squidbridge.indexer.QueryByType;

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
                new File("src/test/resources/metrics/functions.cls"),
                new File("src/test/resources/metrics/lines.cls")));
        SourceProject project = buildProject(scanner);
        assertThat(project.getInt(ApexMetric.FILES)).isEqualTo(2);
    }

    @Test
    public void testTheNumberOfScannedLines() {
        sourceFile = ApexAstScanner.scanFile(new File("src/test/resources/metrics/lines.cls"));
        assertThat(sourceFile.getInt(ApexMetric.LINES_OF_CODE)).isEqualTo(9);
    }

    @Test
    public void testTheNumberOfScannedFunctions() {
        sourceFile = ApexAstScanner.scanFile(new File("src/test/resources/metrics/functions.cls"));
        assertThat(sourceFile.getInt(ApexMetric.FUNCTIONS)).isEqualTo(2);
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

    private SourceProject buildProject(AstScanner<Grammar> scanner) {
        return (SourceProject) scanner.getIndex().search(new QueryByType(SourceProject.class)).iterator().next();
    }
}
