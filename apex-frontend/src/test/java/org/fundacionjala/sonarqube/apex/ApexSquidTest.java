package org.fundacionjala.sonarqube.apex;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.MockChecks.MethodInvocationCheck;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitorCheck;
import org.fundacionjala.sonarqube.visitors.Issue;
import org.fundacionjala.sonarqube.visitors.TreeVisitor;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.FileMetadata;
import org.sonar.api.batch.sensor.internal.SensorContextTester;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ApexSquidTest {

    private ApexSquid apexSquid;
    private File baseDir;
    private SensorContextTester context;
    private List<InputFile> filesToScan;

    @Before
    public void setUp() {
        baseDir = new File("src/test/resources");
        apexSquid = new ApexSquid(null, SensorContextTester.create(baseDir), null, null);
        context = SensorContextTester.create(baseDir);
        DefaultInputFile apexClass = new DefaultInputFile("moduleKey","classes/apexClass.cls");
        apexClass.setModuleBaseDir(baseDir.toPath());
        DefaultInputFile display = new DefaultInputFile("moduleKey","classes/Display.cls");
        display.setModuleBaseDir(baseDir.toPath());
        context.fileSystem().add(apexClass);
        context.fileSystem().add(display);
        apexClass.initMetadata(new FileMetadata().readMetadata(apexClass.file(), Charsets.UTF_8));
        display.initMetadata(new FileMetadata().readMetadata(display.file(), Charsets.UTF_8));
        filesToScan = Lists.newArrayList();
        filesToScan.add(apexClass);
        filesToScan.add(display);
    }

    @Test
    public void testScanFiles() {
        apexSquid.scanFiles(filesToScan, Lists.newArrayList());
    }

    //TODO: Delete this test after checks implementation.
    @Test
    public void testSimulatedCheck() {
       /* List<TreeVisitor> visitors = Lists.newArrayList();
        visitors.add(new MethodInvocationCheck());
        apexSquid.scanFiles(filesToScan, visitors);
        List<Issue> uniqueIssue = BaseTreeVisitorCheck.getIssues();
        if (!uniqueIssue.isEmpty()) {
            String message = uniqueIssue.get(0).getMessage();
            assertEquals(message, "There is a literal return from methodInvocation.");
        }*/
    }
}
