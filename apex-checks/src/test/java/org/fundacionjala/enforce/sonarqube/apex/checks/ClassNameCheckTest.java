/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class ClassNameCheckTest {

    private ClassNameCheck classNameCheck;
    private SourceFile sourceFile;

    @Test
    public void testCorrectClassName() throws Exception {
        classNameCheck = new ClassNameCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), classNameCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testCorrectClassName_() throws Exception {
        classNameCheck = new ClassNameCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect_.cls"), classNameCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testErrorClassName() throws Exception {
        classNameCheck = new ClassNameCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzError.cls"), classNameCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(1).withMessage(
                "Rename class \"someDraftArticle\" to match the regular expression ^[A-Z_][a-zA-Z0-9]+$.");
    }
}
