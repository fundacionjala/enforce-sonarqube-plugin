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

public class SoqlInLoopCheckTest {
    
    private SoqlInLoopCheck check;
    private SourceFile sourceFile;

    @Before
    public void setUp() {
        check = new SoqlInLoopCheck();
    }
    
    @Test
    public void testCorrectSOQLLoop() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/soqlLimitError.cls"), check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).
                noMore();
    }

    @Test
    public void testIncorrectSOQLLoop() throws Exception {
    		sourceFile = scanFile(new File("src/test/resources/checks/soqlFileInLoopError.cls"), check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
               .next().atLine(4).withMessage("SOQL statements should not be in loops.")
               .next().atLine(10).withMessage("SOQL statements should not be in loops.")
               .next().atLine(16).withMessage("SOQL statements should not be in loops.")
               .next().atLine(22).withMessage("SOQL statements should not be in loops.")
               .next().atLine(28).withMessage("SOQL statements should not be in loops.")
               .noMore();
    }
    
    /**
     * Nested SOQL statement loop
     */
    @Test
    public void testIncorrectNestedSOQLLoop() throws Exception {
    	sourceFile = scanFile(new File("src/test/resources/checks/soqlFileInNestedLoopError.cls"), check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).
                next().atLine(17).withMessage(
                "SOQL statements should not be in loops.");
    }
    
    /**
     * File with no loops or SOQL statement
     */
    @Test
    public void testNoSOQLLoop() throws Exception {
    	sourceFile = scanFile(new File("src/test/resources/checks/ClassLengthError.cls"), check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).
                noMore();
    }
    
    /**
     * Just loops
     */
    @Test
    public void testLoops() throws Exception {
        sourceFile = scanFile(new File("src/test/resources/checks/justLoops.cls"), check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).noMore();
    }
    
    /**
     * Just SOQL statements
     */
    @Test
    public void testSoqlStatement() throws Exception {
	    	sourceFile = scanFile(new File("src/test/resources/checks/justSoqlStatements.cls"), check);
	    	CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).noMore();
    }
    
    /**
     * For each loop and return 
     */
    @Test
    public void testSoqlForEachAndReturn() throws Exception {
    	System.out.println("Hey");
	    	sourceFile = scanFile(new File("src/test/resources/checks/soqlForEachLoop.cls"), check);
	    	CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).noMore();
    }
}
