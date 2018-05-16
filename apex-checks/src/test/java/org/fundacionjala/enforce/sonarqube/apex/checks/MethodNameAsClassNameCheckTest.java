/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.MethodNameAsClassNameCheck;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class MethodNameAsClassNameCheckTest {

    private MethodNameAsClassNameCheck methodNameAsClassNameCheck;
    private SourceFile sourceFile;

    @Test
    public void testCorrectMethodName() throws Exception {
    	methodNameAsClassNameCheck = new MethodNameAsClassNameCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), methodNameAsClassNameCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testErrorMethodName() throws Exception {
    	methodNameAsClassNameCheck = new MethodNameAsClassNameCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/MethodNameAsClassNameCheckErrorClass.cls"), methodNameAsClassNameCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(17).withMessage( "Non-constructor methods should not have the same name as the enclosing class.")
                .next().atLine(29).withMessage( "Non-constructor methods should not have the same name as the enclosing class.");
    }
}
