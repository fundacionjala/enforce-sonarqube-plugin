/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.VariableCountCheck;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class VariableCountCheckTest {

    private VariableCountCheck variableCountCheck;
    private SourceFile sourceFile;

    @Test
    public void testCorrectVariableCount() throws Exception {
    	variableCountCheck = new VariableCountCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), variableCountCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testErrorVariableCount() throws Exception {
    	variableCountCheck = new VariableCountCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/VariableCountCheckErrorClass.cls"), variableCountCheck);
        CheckMessagesVerifier chkMsgVerify = CheckMessagesVerifier.verify(sourceFile.getCheckMessages());
        
        chkMsgVerify = chkMsgVerify.next();
        chkMsgVerify.atLine(1).withMessage("Classes that have too many fields/variables can become unwieldy and could be redesigned to have fewer,possibly through grouping related fields/variables in new objects. The maximum number of fields/variables in class is: "+variableCountCheck.DEFAULT_VARIABLE_COUNT+".");
    }
}
