/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.ClassLengthCheck;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class ClassLengthCheckTest {

    private ClassLengthCheck classLengthCheck;
    private SourceFile sourceFile;

    @Test
    public void testCorrectClassLength() throws Exception {
    	classLengthCheck = new ClassLengthCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), classLengthCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testErrorClassLength() throws Exception {
    	classLengthCheck = new ClassLengthCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/ClassLengthError.cls"), classLengthCheck);
        CheckMessagesVerifier chkMsgVerify = CheckMessagesVerifier.verify(sourceFile.getCheckMessages());
        System.out.println("chkMsgVerify: "+ sourceFile.getCheckMessages());
        // For Class
        chkMsgVerify = chkMsgVerify.next();
        chkMsgVerify.atLine(2).withMessage("The maximum number of statements in class is: "+classLengthCheck.DEFAULT_CLASS_LENGTH+".");
        
    }
}
