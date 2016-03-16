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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.issue.Issuable;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;

import org.fundacionjala.enforce.sonarqube.apex.checks.CheckList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApexSquidSensorTest {

    private ApexSquidSensor squidSensor;
    private DefaultFileSystem fileSystem;
    ResourcePerspectives perspectives;

    @Before
    public void setUp() {
        ActiveRules activeRules = (new ActiveRulesBuilder())
                .create(RuleKey.of(CheckList.REPOSITORY_KEY, "PrintStatementUsage"))
                .setName("Print Statement Usage")
                .activate()
                .build();
        CheckFactory checkFactory = new CheckFactory(activeRules);
        perspectives = mock(ResourcePerspectives.class);
        fileSystem = new DefaultFileSystem(new File("."));
        squidSensor = new ApexSquidSensor(fileSystem, perspectives, checkFactory);
    }

    @Test
    public void testExecuteOnApexProject() {
        Project project = new Project("cls");
        assertThat(squidSensor.toString(), equalTo("ApexSquidSensor"));
        assertThat(squidSensor.shouldExecuteOnProject(project), is(Boolean.FALSE));
        fileSystem.add(new DefaultInputFile("Book.cls").setLanguage(Apex.KEY));
        assertThat(squidSensor.shouldExecuteOnProject(project), is(Boolean.TRUE));
    }

    @Test
    public void testAnalyse() {
        String relativePath = "src/test/resources/sensor/Book.cls";
        DefaultInputFile inputFile = new DefaultInputFile(relativePath).setLanguage(Apex.KEY);
        inputFile.setAbsolutePath((new File(relativePath)).getAbsolutePath());
        fileSystem.add(inputFile);

        Issuable issuable = mock(Issuable.class);
        Issuable.IssueBuilder issueBuilder = mock(Issuable.IssueBuilder.class);
        when(perspectives.as(Mockito.eq(Issuable.class), Mockito.any(InputFile.class))).thenReturn(issuable);
        when(issuable.newIssueBuilder()).thenReturn(issueBuilder);
        when(issueBuilder.ruleKey(Mockito.any(RuleKey.class))).thenReturn(issueBuilder);
        when(issueBuilder.line(Mockito.any(Integer.class))).thenReturn(issueBuilder);
        when(issueBuilder.message(Mockito.any(String.class))).thenReturn(issueBuilder);

        Project project = new Project("cls");
        SensorContext context = mock(SensorContext.class);
        squidSensor.analyse(project, context);

        verify(context).saveMeasure(any(InputFile.class), eq(CoreMetrics.FILES), eq(1.0));
        verify(context).saveMeasure(any(InputFile.class), eq(CoreMetrics.LINES), eq(7.0));
        verify(context).saveMeasure(any(InputFile.class), eq(CoreMetrics.NCLOC), eq(6.0));
        verify(context).saveMeasure(any(InputFile.class), eq(CoreMetrics.STATEMENTS), eq(2.0));
        verify(context).saveMeasure(any(InputFile.class), eq(CoreMetrics.FUNCTIONS), eq(1.0));
        verify(context).saveMeasure(any(InputFile.class), eq(CoreMetrics.CLASSES), eq(1.0));
        verify(context).saveMeasure(any(InputFile.class), eq(CoreMetrics.COMPLEXITY), eq(1.0));
    }
}
