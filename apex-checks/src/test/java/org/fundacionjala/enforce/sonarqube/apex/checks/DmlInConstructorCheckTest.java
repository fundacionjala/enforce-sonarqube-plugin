/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.DmlInConstructorCheck;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

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
