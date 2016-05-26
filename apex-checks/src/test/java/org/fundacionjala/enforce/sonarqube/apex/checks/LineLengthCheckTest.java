/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class LineLengthCheckTest {

    private LineLengthCheck lineLengthCheck;
    private SourceFile sourceFile;

    @Before
    public void setup() {
        lineLengthCheck = new LineLengthCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/lineLength.cls"), lineLengthCheck);
    }

    @Test
    public void testEmptyFile() {
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(1).withMessage("The line length is greater than 80 authorized.")
                .next().atLine(4).withMessage("The line length is greater than 80 authorized.")
                .noMore();
    }
}
