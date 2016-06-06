/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.fundacionjala.enforce.sonarqube.apex.checks.Tags;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

import java.util.List;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

@Rule(
        key = AssertLiteralBooleanCheck.CHECK_KEY,
        priority = Priority.MAJOR,
        tags = Tags.CONVENTION
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.LOGIC_RELIABILITY)
@SqaleConstantRemediation("1min")
@ActivatedByDefault
public class AssertLiteralBooleanCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1024";

    /**
     * Display message of the check.
     */
    private final String MESSAGE = ChecksBundle.getStringFromBundle("LiteralBooleanAssertCheckMessage");

    /**
     * Keyword to review the assert method call.
     */
    private final String SYSTEM = "SYSTEM";

    /**
     * Keyword to review the assert method call.
     */
    private final String ASSERT = "ASSERT";

    /**
     * Keyword to review the assert method call parameter.
     */
    private final String TRUE_LITERAL = "TRUE";

    /**
     * Keyword to review the assert method call parameter.
     */
    private final String FALSE_LITERAL = "FALSE";

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
                AstNode first = name.getFirstChild(ApexGrammarRuleKey.METHOD_IDENTIFIER);
                AstNode last = name.getLastChild(ApexGrammarRuleKey.METHOD_IDENTIFIER);
                if (first != null && last != null
                        && first.getTokenValue().equals(SYSTEM)
                        && last.getTokenValue().equals(ASSERT)) {
                    List<AstNode> arguments = primaryExpression.getFirstDescendant(ApexGrammarRuleKey.ARGUMENTS_LIST).getChildren();
                    for (AstNode argument : arguments) {
                        if (argument.getTokenValue().equals(TRUE_LITERAL)
                                || argument.getTokenValue().equals(FALSE_LITERAL)) {
                            getContext().createLineViolation(this,
                                    MESSAGE, suffix, astNode.getFirstChild(ApexGrammarRuleKey.METHOD_IDENTIFIER).getTokenOriginalValue());
                        }
                    }
                }
            }
        }
    }

}
