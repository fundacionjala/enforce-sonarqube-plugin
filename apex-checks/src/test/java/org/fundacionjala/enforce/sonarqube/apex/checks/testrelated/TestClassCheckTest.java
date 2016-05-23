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
package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import java.io.File;
import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class TestClassCheckTest {
    
    private TestClassCheck testClassCheck;
    private SourceFile sourceFile;
    
    @Before
    public void setUp() {
        testClassCheck = new TestClassCheck();
    }

    @After
    public void tearDown() {
        testClassCheck = null;
        sourceFile = null;
    }
    
    @Test
    public void testCorrectTestClass() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/testClassCheck.cls"), testClassCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }
    
    @Test
    public void testFailingTestClassEnum() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/testClassCheckEnum.cls"), testClassCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(2).withMessage("The \"@isTest\" annotation should be used only for classes, remove it from the declaration of \"SomeEnum\".");
    }
    
    @Test
    public void testFailingTestClassInterface() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/testClassCheckInterface.cls"), testClassCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(2).withMessage("The \"@isTest\" annotation should be used only for classes, remove it from the declaration of \"SomeInterface\".");
    }
    
    @Test
    public void testFailingTestClassName() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/testClassCheckBadName.cls"), testClassCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(2).withMessage("The name of the class \"SomeClassTest\" suggests this is a test class, either add an @isTest annotation"
                                + "or change the name of the class.");
    }
}
