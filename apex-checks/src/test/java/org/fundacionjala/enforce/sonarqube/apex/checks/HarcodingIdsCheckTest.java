/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import java.io.File;

import org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;
import org.sonar.squidbridge.checks.SquidCheck;

import com.sonar.sslr.api.Grammar;

public class HarcodingIdsCheckTest {

    private static final String FIRST_VARIABLE_ERROR = "Variable \"firsRecordId\" contains hard-coded id value.";
    private static final String SECOND_VARIABLE_ERROR = "Variable \"recordId\" contains hard-coded id value.";
    private static final String THIRD_VARIABLE_ERROR = "Variable \"secondRecord\" contains hard-coded id value.";
    private static final String FIRST_EXPRESSION_ERROR_MESSAGE = "Statement contains hard-coded value '554654134167168118'.";
    private static final String SECOND_EXPRESSION_ERROR_MESSAGE = "Statement contains hard-coded value '554546546465456'.";
    
    private SourceFile sourceFile;
    
    @Test
    public void testHardcodedIdsInVariables() {
        String filePath = "src/test/resources/checks/hardcodedIdsInVariables.cls";
        scanFilesWithCheck(filePath, new HardcodingIdsCheckInVariables());

        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(3).withMessage(FIRST_VARIABLE_ERROR)
                .next().atLine(10).withMessage(SECOND_VARIABLE_ERROR)
                .next().atLine(12).withMessage(THIRD_VARIABLE_ERROR)
                .noMore();
    }
    
    @Test
    public void testHardcodedIdsInMethodsAndConstructors() {
        String filePath = "src/test/resources/checks/hardcodingIdsInMethodsAndConstructors.cls";
        scanFilesWithCheck(filePath, new HardcodingIdsInMethodsAndConstructorsCheck());

        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(4).withMessage(FIRST_EXPRESSION_ERROR_MESSAGE)
                .next().atLine(15).withMessage(SECOND_EXPRESSION_ERROR_MESSAGE)
                .noMore();
    }
    
    /**
     * Scan files given a file path and the respective check to scan file
     * @param filePath the path of the file to be scanned.
     * @param check visitor.
     */
    private void scanFilesWithCheck(String filePath, SquidCheck<Grammar> check) {
        File file = new File(filePath);
        sourceFile = ApexAstScanner.scanFile(file, check);
    }

}
