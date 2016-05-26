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

public class AsyncMethodsCheckTest {

    private AsyncMethodsCheck asyncMethodCheck;
    private SourceFile sourceFile;
    
    @Test
    public void testAsyncMethodCheck() throws Exception {
        asyncMethodCheck = new AsyncMethodsCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/asyncMethodTestClass.cls"), asyncMethodCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(11).withMessage(
                "Method \"execAsyncMethod\" is Async, should not be called withing a loop.")
                .next().atLine(17).withMessage(
                "Method \"execAsyncMethod\" is Async, should not be called withing a loop.")
                .next().atLine(22).withMessage(
                "Method \"execAnotherAsyncMethod\" is Async, should not be called withing a loop.")
                .next().atLine(25).withMessage(
                "Method \"execAnotherAsyncMethod\" is Async, should not be called withing a loop.");
    }

}
