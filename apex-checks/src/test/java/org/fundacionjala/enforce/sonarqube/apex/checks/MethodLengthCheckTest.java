/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.MethodLengthCheck;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class MethodLengthCheckTest {

    private MethodLengthCheck methodLengthCheck;
    private SourceFile sourceFile;

    /*@Test
    public void testCorrectMethodLength() throws Exception {
    	methodLengthCheck = new MethodLengthCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), methodLengthCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }*/

    @Test
    public void testErrorMethodLength() throws Exception {
    	methodLengthCheck = new MethodLengthCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/ClassLengthError.cls"), methodLengthCheck);
        CheckMessagesVerifier chkMsgVerify = CheckMessagesVerifier.verify(sourceFile.getCheckMessages());
        
        chkMsgVerify.next().withMessage("The maximum number of statements in method is: "+ methodLengthCheck.DEFAULT_METHOD_LENGTH+".");
        
    }
    
   /* @Test
    public void testErrorMethodLengthWithSOQL() throws Exception {
    	methodLengthCheck = new MethodLengthCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/WebToCaseController.cls"), methodLengthCheck);
        CheckMessagesVerifier chkMsgVerify = CheckMessagesVerifier.verify(sourceFile.getCheckMessages());
        
        //chkMsgVerify.next().withMessage("The maximum number of statements in method is: "+ methodLengthCheck.DEFAULT_METHOD_LENGTH+".");
        
    }*/
}

