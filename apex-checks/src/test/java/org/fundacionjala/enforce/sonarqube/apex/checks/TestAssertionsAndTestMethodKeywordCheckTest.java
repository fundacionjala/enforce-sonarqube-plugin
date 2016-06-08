/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

public class TestAssertionsAndTestMethodKeywordCheckTest {

    private static TestAssertionsAndTestMethodKeywordCheck check;

    public TestAssertionsAndTestMethodKeywordCheckTest() {
        check = new TestAssertionsAndTestMethodKeywordCheck();
    }

    @Test
    public void testTestShouldHaveAssertions() {
        File file = new File("src/test/resources/checks/testsClassAssertions.cls");
        SourceFile sourceFile = ApexAstScanner.scanFile(file, check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next()
                .atLine(3)
                .withMessage("Test method at line 3 should have at least one assertion.")
                .next()
                .atLine(54)
                .withMessage("Test method in line 54 must have testMethod keyword.")
                .noMore();
    }
}
