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


public class AssertLiteralBooleanCheckTest {

    private AssertLiteralBooleanCheck check;
    private SourceFile sourceFile;

    @Before
    public void setUp() {
        check = new AssertLiteralBooleanCheck();
    }

    @Test
    public void testLiteralBooleanInAssert() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/literalBooleanAssertTestClass.cls"), check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(7).withMessage("The method \"someTestMethod\" is passing an assertion a literal "
                        + "boolean value, this is a bad practice.")
                .next().atLine(13).withMessage("The method \"otherTestMethod\" is passing an assertion a literal "
                        + "boolean value, this is a bad practice.")
                .noMore();
    }

}
