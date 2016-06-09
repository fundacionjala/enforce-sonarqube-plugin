/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

/**
 * Visits all the instances where the parser had to skip tokens because of a probable
 * parsing error and creates a message for them.
 */
@Rule(
        key = ErrorRecoveryCheck.CHECK_KEY,
        priority = Priority.INFO,
        tags = Tags.BUG
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.SYNCHRONIZATION_RELIABILITY)
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class ErrorRecoveryCheck extends SquidCheck<Grammar> {
    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "R1001";
    
    private final String MESSAGE = ChecksBundle.getStringFromBundle("RecoveryCheckMessage");
    
    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.RECOVERED_MEMBER,
                ApexGrammarRuleKey.RECOVERED_STATEMENT);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        getContext().createLineViolation(this, MESSAGE, astNode);
    }
}
