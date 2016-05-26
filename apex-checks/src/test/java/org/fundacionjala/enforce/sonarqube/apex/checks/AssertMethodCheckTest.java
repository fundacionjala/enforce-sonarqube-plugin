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

public class AssertMethodCheckTest {
    
    private AssertMethodCheck assertMethodCheck;
    private SourceFile sourceFile;

    @Before
    public void setup() {
        assertMethodCheck = new AssertMethodCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/testMethod.cls"), assertMethodCheck);
    }

    @Test
    public void testEmptyFile() {
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(4).withMessage("It's bad practice to use assert(true).")
                .next().atLine(5).withMessage("It's bad practice to use assert(value, value).")
                .noMore();
    }
}
