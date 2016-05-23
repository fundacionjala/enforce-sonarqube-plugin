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

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.DmlInConstructorCheck;
import java.io.File;
import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class DmlInConstructorCheckTest {
    
    private DmlInConstructorCheck dmlCheckInConstructor;
    private SourceFile sourceFile;

    @Test
    public void testIncorrectDMLDeclarationCorrect() throws Exception {
        dmlCheckInConstructor = new DmlInConstructorCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), dmlCheckInConstructor);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testIncorrectDMLDeclarationIncorrectInsertAndDelete() throws Exception {
        dmlCheckInConstructor = new DmlInConstructorCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzError.cls"), dmlCheckInConstructor);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).
                next().atLine(13).withMessage(
                "The DML statement \"insert\", can not be inside a constructor")
                .next().atLine(22).withMessage(
                "The DML statement \"DELETE\", can not be inside a constructor");
    }
}
