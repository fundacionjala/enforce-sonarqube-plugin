/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class SeeAllDataTestCheckTest {
    
    private SeeAllDataTestCheck check;
    private SourceFile sourceFile;
    
    @Before
    public void setUp() {
        check = new SeeAllDataTestCheck();
    }
    
    @Test
    public void testCorrectSeeAllDataCheck() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/testClassCheck.cls"), check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testFailingSeeAllDataCheck() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/seeAllDataTestClass.cls"), check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(2).withMessage("Class \"SeeAllDataTestClass\" is using the annotation "
                        + "\"@isTest\" with the \"(seeAllData = true)\" parameter, this is not recommendable;"
                        + " you can leave the annotation, but you should remove the parameter.")
                .noMore();
    }
}
