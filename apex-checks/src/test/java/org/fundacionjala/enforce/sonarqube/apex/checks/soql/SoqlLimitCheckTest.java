/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.soql;

import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

public class SoqlLimitCheckTest {
    
    private SoqlLimitCheck check;
    private SourceFile sourceFile;

    @Before
    public void setUp() {
        check = new SoqlLimitCheck();
    }
    
    @Test
    public void soqlLimitCorrect() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/soqlLimitCorrect.cls"), check);
        System.out.println("sourceFile.getCheckMessages(): ==> "+ sourceFile.getCheckMessages());;
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .noMore();  // iterates through and checks if no more violations on next lines
    }

    @Test
    public void soqlLimitError() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/SoqlLimitError.cls"), check);
        System.out.println("sourceFile.getCheckMessages(): ==> "+ sourceFile.getCheckMessages());
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(8).withMessage("Define the LIMIT for this SOQL statement.")
                .next().atLine(15).withMessage("Define the LIMIT for this SOQL statement.");
        // TODO are there cases where SOQL statements don't need a limit?
    }
}
