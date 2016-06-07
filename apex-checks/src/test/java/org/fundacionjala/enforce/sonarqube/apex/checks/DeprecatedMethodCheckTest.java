/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.DeprecatedMethodCheck;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class DeprecatedMethodCheckTest {

    private DeprecatedMethodCheck deprecatedMethodCheck;
    private SourceFile sourceFile;

    @Test
    public void testIncorrectDMLDeclarationCorrect() throws Exception {
        deprecatedMethodCheck = new DeprecatedMethodCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), deprecatedMethodCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();
    }

    @Test
    public void testIncorrectDMLDeclarationIncorrectInsert() throws Exception {
        deprecatedMethodCheck = new DeprecatedMethodCheck();
        sourceFile = scanFile(new File("src/test/resources/checks/clazzError.cls"), deprecatedMethodCheck);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).
                next().atLine(18).withMessage(
                "The \"myINIT\" method is deprecated, suggest deleting the contents of the method.");
    }
}
