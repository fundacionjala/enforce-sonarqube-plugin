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

public class AssertMessageCheckTest {

    private AssertMessageCheck check;
    private SourceFile sourceFile;

    @Before
    public void setUp() {
        check = new AssertMessageCheck();
    }

    @Test
    public void testLiteralBooleanInAssert() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/assertWithMessageTestClass.cls"), check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(11).withMessage("Test method \"anotherTestMethod\" is calling an assertion,"
                        + " it is recommendable to pass a message in case of test failure.")
                .next().atLine(12).withMessage("Test method \"anotherTestMethod\" is calling an assertion,"
                        + " it is recommendable to pass a message in case of test failure.")
                .next().atLine(13).withMessage("Test method \"anotherTestMethod\" is calling an assertion,"
                        + " it is recommendable to pass a message in case of test failure.")
                .noMore();
    }

}
