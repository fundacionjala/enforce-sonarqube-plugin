/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class TestMethodInTestClassCheckTest {

    private TestMethodInTestClassCheck testMethodInTestClassCheck;
    private SourceFile sourceFile;

    @Before
    public void setUp() {
        testMethodInTestClassCheck = new TestMethodInTestClassCheck();
    }

    @After
    public void tearDown() {
        testMethodInTestClassCheck = null;
        sourceFile = null;
    }

    @Test
    public void testCorrectTestClass() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/testMethodInTestClassCorrect.cls"),
                testMethodInTestClassCheck
        );
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testFailingTestClassEnum() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/testMethodInTestClassIncorrect.cls"),
                testMethodInTestClassCheck
        );
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(3).withMessage("The method \"aTestMethod\" is marked as a testMethod but it"
                        + " is not in a test class, move it to a proper class or add the \"@isTest\" annotation"
                        + " to the class \"SomeClass\".")
                .next().atLine(7).withMessage("The method \"anotherTestMethod\" is marked as a testMethod but it"
                        + " is not in a test class, move it to a proper class or add the \"@isTest\" annotation"
                        + " to the class \"SomeClass\".")
                .noMore();
    }
}
