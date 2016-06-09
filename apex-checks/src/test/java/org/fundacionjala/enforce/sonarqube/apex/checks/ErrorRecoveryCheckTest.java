/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class ErrorRecoveryCheckTest {
    
    private ErrorRecoveryCheck check;
    private SourceFile sourceFile;

    @Before
    public void setUp() {
        check = new ErrorRecoveryCheck();
    }

    @Test
    public void testFileWithErrors() {
        sourceFile = scanFile(new File("src/test/resources/checks/recoveryClass.cls"), check);
        Assert.assertFalse(sourceFile.getCheckMessages().isEmpty());
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(8).withMessage("Parsing error found, the block was skipped during the analysis.")
                .next().atLine(16).withMessage("Parsing error found, the block was skipped during the analysis.")
                .noMore();
    }
}
