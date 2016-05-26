/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class DmlInForCheckTest {

    private DmlInForCheck dmlCheckInFor;
    private SourceFile sourceFile;

    @Test
    public void testCorrectDMLDeclaration() throws Exception {
        dmlCheckInFor = new DmlInForCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), dmlCheckInFor);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).
                noMore();
    }

    @Test
    public void testIncorrectDMLDeclaration() throws Exception {
        dmlCheckInFor = new DmlInForCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzError.cls"), dmlCheckInFor);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).
                next().atLine(8).withMessage(
                "The DML statement \"merge\", can not be inside a for loop");
    }
}
