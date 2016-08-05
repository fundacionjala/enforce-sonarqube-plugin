/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.MethodNameCheck;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class MethodNameCheckTest {

    private MethodNameCheck methodNameCheck;
    private SourceFile sourceFile;

    @Test
    public void testCorrectMethodName() throws Exception {
        methodNameCheck = new MethodNameCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), methodNameCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testErrorMethodName() throws Exception {
        methodNameCheck = new MethodNameCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzError.cls"), methodNameCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(3).withMessage(
                "Rename method \"MYmeThod\" to match the regular expression ^[a-z][a-zA-Z0-9]+$.");
    }
}
