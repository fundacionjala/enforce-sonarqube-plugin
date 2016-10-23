package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.APEX_GRAMMAR;
import static org.sonar.sslr.tests.Assertions.assertThat;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.impl.ast.AstXmlPrinter;

public class ApexGrammarTriggerTest extends ApexRuleTest {

	@Before
	public void init(){
		setRootRule(APEX_GRAMMAR);
	}
	
	@Test
	public void basic(){
		AstNode astNode = parser.parse("trigger triggerName on Case(before insert){}");
		System.out.println(AstXmlPrinter.print(astNode));
	}
	
	@Test
	public void iterations(){
		assertThat(parser)
			.matches("trigger tName on Case(before insert){}")
			.matches("trigger tName on Case(before update){}")
			.matches("trigger tName on Case(before delete){}")
			.matches("trigger tName on Case(after insert){}")
			.matches("trigger tName on Case(after update){}")
			.matches("trigger tName on Case(after delete){}")
			.matches("trigger tName on Case(after undelete){}")
			.matches("trigger triggerName on Object(before insert, before update, before delete, after insert, after update, after delete, after undelete){}");
	}

}
