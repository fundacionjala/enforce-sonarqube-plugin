/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.DmlInWhileCheck;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class DmlInWhileCheckTest {

    private DmlInWhileCheck dmlCheckInWhile;
    private SourceFile sourceFile;

    @Test
    public void testIncorrectDMLDeclarationCorrect() throws Exception {
        dmlCheckInWhile = new DmlInWhileCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), dmlCheckInWhile);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testIncorrectDMLDeclarationIncorrectInsert() throws Exception {
        dmlCheckInWhile = new DmlInWhileCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzError.cls"), dmlCheckInWhile);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).
                next().atLine(4).withMessage(
                "The DML statement \"insert\", can not be inside a while loop.");
    }
}
