/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.ParameterCountCheck;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class ParameterCountCheckTest {

    private ParameterCountCheck parameterCountCheck;
    private SourceFile sourceFile;

    @Test
    public void testCorrectParameterCount() throws Exception {
    	parameterCountCheck = new ParameterCountCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), parameterCountCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testErrorParameterCount() throws Exception {
    	parameterCountCheck = new ParameterCountCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzError.cls"), parameterCountCheck);
        CheckMessagesVerifier chkMsgVerify = CheckMessagesVerifier.verify(sourceFile.getCheckMessages());
        
        // For Method
        chkMsgVerify = chkMsgVerify.next();
        chkMsgVerify.atLine(26).withMessage("The maximum number of parameters in method/constructor is: "+parameterCountCheck.DEFAULT_PARAM_COUNT);
        
        // For Constructor
        chkMsgVerify = chkMsgVerify.next();
        chkMsgVerify.atLine(30).withMessage("The maximum number of parameters in method/constructor is: "+parameterCountCheck.DEFAULT_PARAM_COUNT);
    }
}
