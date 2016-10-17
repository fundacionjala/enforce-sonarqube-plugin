package org.fundacionjala.enforce.sonarqube.apex.checks;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

import java.io.File;

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.EmptyCatchCheck;
import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class EmptyCatchCheckTest {
	
	private EmptyCatchCheck check;
	private SourceFile sourceFile;
	
	private final String expectedMessage = ChecksBundle.getStringFromBundle("EmptyCatchCheckMessage");
	
	@Before
	public void setup(){
		check = new EmptyCatchCheck();
		sourceFile = scanFile(new File("src/test/resources/checks/emptyCatch.cls"), check);
	}
	
	@Test
	public void testEmptyCatch(){
		CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
			.next().atLine(8).withMessage(expectedMessage)
		.noMore();
	}

}
