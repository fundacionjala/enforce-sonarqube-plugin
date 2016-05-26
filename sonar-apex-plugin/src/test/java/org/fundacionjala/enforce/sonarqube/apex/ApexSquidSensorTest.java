/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex;

import org.fundacionjala.enforce.sonarqube.apex.checks.CheckList;
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

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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
