/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import java.util.List;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

@Rule(key = AssertMessageCheck.CHECK_KEY)
public class AssertMessageCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1026";

    /**
     * Display message of the check.
     */
    private final String MESSAGE = ChecksBundle.getStringFromBundle("AssertionMessageCheckMessage");

    /**
     * Keyword to review the assert method call.
     */
    private final String SYSTEM = "SYSTEM";

    /**
     * Keyword to review the assert method call.
     */
    private final String ASSERT_REGEX = "ASSERT((NOT)?(EQUALS))?";

    /**
     * Used to determine when the assert only has one parameter and therefore,
     * no message, and to be subtracted from the list size.
     */
    private final int VALUE_OF_ONE = 1;

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.METHOD_DECLARATION);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        if (astNode.hasDescendant(ApexGrammarRuleKey.PRIMARY_SUFFIX)) {
            List<AstNode> suffixes = astNode.getDescendants(ApexGrammarRuleKey.PRIMARY_SUFFIX);
            for (AstNode suffix : suffixes) {
                AstNode primaryExpression = suffix.getParent();
                AstNode name = primaryExpression.getFirstDescendant(ApexGrammarRuleKey.NAME);
                if (name != null) {
                    AstNode first = name.getFirstChild(ApexGrammarRuleKey.METHOD_IDENTIFIER);
                    AstNode last = name.getLastChild(ApexGrammarRuleKey.METHOD_IDENTIFIER);
                    if (first != null && last != null
                            && first.getTokenValue().equals(SYSTEM)
                            && last.getTokenValue().matches(ASSERT_REGEX)) {
                        List<AstNode> arguments = primaryExpression.getFirstDescendant(ApexGrammarRuleKey.ARGUMENTS_LIST).getChildren();
                        if (arguments.size() <= VALUE_OF_ONE || !hasMessage(arguments)) {
                            getContext().createLineViolation(this,
                                    MESSAGE, primaryExpression, astNode.getFirstChild(ApexGrammarRuleKey.METHOD_IDENTIFIER).getTokenOriginalValue());
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks the last item of the arguments list to see if it's a message.
     *
     * @param arguments the list of arguments of the assert method.
     * @return true if the last argument is a message.
     */
    private boolean hasMessage(List<AstNode> arguments) {
        return arguments.get(arguments.size() - VALUE_OF_ONE).hasDescendant(ApexGrammarRuleKey.STRING_LITERAL_STRING);
    }

}
