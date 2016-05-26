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

public class TestClassCheckTest {
    
    private TestClassCheck testClassCheck;
    private SourceFile sourceFile;
    
    @Before
    public void setUp() {
        testClassCheck = new TestClassCheck();
    }

    @After
    public void tearDown() {
        testClassCheck = null;
        sourceFile = null;
    }
    
    @Test
    public void testCorrectTestClass() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/testClassCheck.cls"), testClassCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }
    
    @Test
    public void testFailingTestClassEnum() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/testClassCheckEnum.cls"), testClassCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(2).withMessage("The \"@isTest\" annotation should be used only for classes, remove it from the declaration of \"SomeEnum\".");
    }
    
    @Test
    public void testFailingTestClassInterface() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/testClassCheckInterface.cls"), testClassCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(2).withMessage("The \"@isTest\" annotation should be used only for classes, remove it from the declaration of \"SomeInterface\".");
    }
    
    @Test
    public void testFailingTestClassName() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/testClassCheckBadName.cls"), testClassCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(2).withMessage("The name of the class \"SomeClassTest\" suggests this is a test class, either add an @isTest annotation"
                                + "or change the name of the class.");
    }
}
