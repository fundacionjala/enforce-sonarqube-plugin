package org.fundacionjala.sonarqube.apex;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.sensor.internal.SensorContextTester;

import java.io.File;
import java.util.List;

public class ApexSquidTest {

    private ApexSquid apexSquid;

    @Before
    public void setUp() {
        File baseDir = new File("src/main/resources/classes");
        apexSquid = new ApexSquid(null, SensorContextTester.create(baseDir), null);
    }

    @Test
    public void testScanFiles() {
        File someApexFile = new File("src/main/resources/classes/apexClass.cls");
        List<File> filesToScan = Lists.newArrayList();
        filesToScan.add(someApexFile);
        apexSquid.scanFiles(filesToScan);
    }
}
