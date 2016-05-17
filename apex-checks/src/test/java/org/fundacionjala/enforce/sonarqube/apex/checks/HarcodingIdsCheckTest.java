/*
 * The MIT License
 *
 * Copyright 2016 Fundacion Jala.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import com.sonar.sslr.api.Grammar;
import java.io.File;
import org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;
import org.sonar.squidbridge.checks.SquidCheck;

public class HarcodingIdsCheckTest {

    private static final String FIRST_VARIABLE_ERROR = "The \"recordId\" variable has hard-coded lines";
    private static final String SECOND_VARIABLE_ERROR = "The \"secondRecord\" variable has hard-coded lines";
    private static final String FIRST_EXPRESSION_ERROR_MESSAGE = "Line 4 contains hard-coded value '4654134167168118'";
    private static final String SECOND_EXPRESSION_ERROR_MESSAGE = "Line 11 contains hard-coded value '65469451346546135'";
    private static final String THIRD_EXPRESSION_ERROR_MESSAGE = "Line 9 contains hard-coded value '546546455454654AWB'";
    private static final String FOURTH_EXPRESSION_ERROR_MESSAGE = "Line 15 contains hard-coded value '554546546465456'";
    
    private SourceFile sourceFile;
    
    @Test
    public void testHardcodedIdsInVariables() {
        String filePath = "src/test/resources/checks/HarcodedIdsInVariables.cls";
        scanFilesWithCheck(filePath, new HardcodingIdsCheckInVariables());

        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(3).withMessage(FIRST_VARIABLE_ERROR)
                .next().atLine(5).withMessage(SECOND_VARIABLE_ERROR)
                .noMore();
    }
    
    @Test
    public void testHardcodedIdsInMethodsAndConstructors() {
        String filePath = "src/test/resources/checks/hardcodingIdsInMethodsAndConstructors.cls";
        scanFilesWithCheck(filePath, new HardcodingIdsInMethodsAndConstructorsCheck());

        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(4).withMessage(FIRST_EXPRESSION_ERROR_MESSAGE)
                .next().atLine(9).withMessage(THIRD_EXPRESSION_ERROR_MESSAGE)
                .next().atLine(11).withMessage(SECOND_EXPRESSION_ERROR_MESSAGE)
                .next().atLine(15).withMessage(FOURTH_EXPRESSION_ERROR_MESSAGE)
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
