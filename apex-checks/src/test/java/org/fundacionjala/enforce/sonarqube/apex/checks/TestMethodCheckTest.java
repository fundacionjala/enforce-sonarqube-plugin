/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class TestMethodCheckTest {

    private TestMethodCheck testMethodCheck;
    private SourceFile sourceFile;

    @Before
    public void setup() {
        testMethodCheck = new TestMethodCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/testMethod.cls"), testMethodCheck);
    }

    @Test
    public void testEmptyFile() {
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(3).withMessage("The \"TEstType\" method corresponds to a test class.")
                .noMore();
    }
}
