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

import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class StartAndStopCheckTest {

    private StartAndStopCheck check;
    private SourceFile sourceFile;

    @Before
    public void setUp() {
        check = new StartAndStopCheck();
    }
    
    @Test
    public void testStartAndStopClass() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/startAndStopClass.cls"), check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(4).withMessage("The test method \"anotherTestMethod\" is "
                        + "calling \"Test.testStart\" and/or \"Test.testStop\" more than once.")
                .next().atLine(11).withMessage("The test method \"anootherTestMethod\" is "
                        + "calling \"Test.testStart\" and/or \"Test.testStop\" more than once.")
                .next().atLine(21).withMessage("The test method \"anoootherTestMethod\" is "
                        + "calling \"Test.testStart\" and/or \"Test.testStop\" more than once.")
                .noMore();
    }
}
