package org.fundacionjala.enforce.sonarqube.apex.checks;

import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;

import java.io.File;

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.DeeplyNestedIfStmtsCheck;
import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class DeeplyNestedIfStmtsCheckTest {

	private DeeplyNestedIfStmtsCheck check;
	private SourceFile sourceFile;

	@Before
	public void setup(){
		check = new DeeplyNestedIfStmtsCheck();
	}
	/**
	 * Exceeds if-then limit of 3.
	 * @throws Exception
	 */
	@Test
	public void testError() throws Exception{
		//System.out.println("Error");
		sourceFile = scanFile(new File("src/test/resources/checks/nestedIfError.cls"), check);
		//System.out.println("Error: "+sourceFile.getCheckMessages());
		CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
		.next().atLine(8).withMessage(
				"Avoid creating deeply nested if-then statements since they are harder to read and error-prone to maintain, limit to "+check.DEFAULT_IF_DEPTH+".")
		.noMore();
	}

	/**
	 * Should be correct if-then logic with else if conditions.
	 * @throws Exception
	 */
	@Test
	public void testSimpleIfElse() throws Exception{
		//System.out.println("Simple");
		sourceFile = scanFile(new File("src/test/resources/checks/simpleIfElse.cls"), check);
		CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).noMore();
	}

	/**
	 * Exceeds if-then limit of 3 four times.
	 * @throws Exception
	 */
	@Test
	public void testMessyIfElse() throws Exception{
		//System.out.println("Messy");
		sourceFile = scanFile(new File("src/test/resources/checks/messyIfElse.cls"), check);
		//System.out.println(sourceFile.getCheckMessages());
		CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
		.next().atLine(7).withMessage("Avoid creating deeply nested if-then statements since they are harder to read and error-prone to maintain, limit to "+check.DEFAULT_IF_DEPTH+".")
		.next().atLine(12).withMessage("Avoid creating deeply nested if-then statements since they are harder to read and error-prone to maintain, limit to "+check.DEFAULT_IF_DEPTH+".")
		.next().atLine(15).withMessage("Avoid creating deeply nested if-then statements since they are harder to read and error-prone to maintain, limit to "+check.DEFAULT_IF_DEPTH+".")
		.next().atLine(17).withMessage("Avoid creating deeply nested if-then statements since they are harder to read and error-prone to maintain, limit to "+check.DEFAULT_IF_DEPTH+".")
		.next().atLine(28).withMessage("Avoid creating deeply nested if-then statements since they are harder to read and error-prone to maintain, limit to "+check.DEFAULT_IF_DEPTH+".")
		.next().atLine(33).withMessage("Avoid creating deeply nested if-then statements since they are harder to read and error-prone to maintain, limit to "+check.DEFAULT_IF_DEPTH+".")
		.next().atLine(36).withMessage("Avoid creating deeply nested if-then statements since they are harder to read and error-prone to maintain, limit to "+check.DEFAULT_IF_DEPTH+".")
		.next().atLine(38).withMessage("Avoid creating deeply nested if-then statements since they are harder to read and error-prone to maintain, limit to "+check.DEFAULT_IF_DEPTH+".")
		.noMore();
	}

	/**
	 * No violation, just one if.
	 * @throws Exception
	 */
	@Test
	public void testRandomMethodWithoutViolation() throws Exception{
		sourceFile = scanFile(new File("src/test/resources/checks/soqlLimitCorrect.cls"), check);
		CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).noMore();
	}
}
