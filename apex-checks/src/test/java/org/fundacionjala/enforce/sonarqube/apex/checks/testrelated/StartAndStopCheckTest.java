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

public class StartAndStopCheckTest {

    private StartAndStopCheck check;
    private SourceFile sourceFile;

    @Before
    public void setUp() {
        check = new StartAndStopCheck();
    }
    
    @Test
    public void testStartAndStopClass() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/startAndStopClass.cls"), check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(4).withMessage("The test method \"anotherTestMethod\" is "
                        + "calling \"Test.testStart\" and/or \"Test.testStop\" more than once.")
                .next().atLine(11).withMessage("The test method \"anootherTestMethod\" is "
                        + "calling \"Test.testStart\" and/or \"Test.testStop\" more than once.")
                .next().atLine(21).withMessage("The test method \"anoootherTestMethod\" is "
                        + "calling \"Test.testStart\" and/or \"Test.testStop\" more than once.")
                .noMore();
    }
}
