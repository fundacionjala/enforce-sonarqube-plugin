package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;
import org.sonar.squidbridge.api.CheckMessage;
import org.sonar.squidbridge.checks.SquidCheck;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

/**
 * Verification of the name of the class.
 */
@Rule(key = EmptyCatchCheck.CHECK_KEY)
public class EmptyCatchCheck extends SquidCheck<Grammar> {
	
	/**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1028";
	
	private final String MESSAGE = ChecksBundle.getStringFromBundle("EmptyCatchCheckMessage");

	@Override
	public void init() {
		subscribeTo(ApexGrammarRuleKey.CATCH_STATEMENT);
	}

	@Override
	public void visitNode(AstNode astNode) {
		List<AstNode> statements = astNode.getDescendants(ApexGrammarRuleKey.STATEMENT);
		//catch should have at least one statement
		if(statements.size() == 0){
			getContext().createLineViolation(this, MESSAGE, astNode, "Extra arg 1", "Extra arg2");
		}
	}

}
