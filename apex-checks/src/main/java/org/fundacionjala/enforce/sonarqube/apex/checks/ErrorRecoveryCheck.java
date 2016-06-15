/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import java.util.ArrayList;
import java.util.List;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

/**
 * Visits all the instances where the parser had to skip tokens because of a
 * probable parsing error and creates a message for them.
 */
@Rule(key = ErrorRecoveryCheck.CHECK_KEY)
public class ErrorRecoveryCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "R1001";

    private final String MESSAGE = ChecksBundle.getStringFromBundle("RecoveryCheckMessage");

    private List<Integer> errors;

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        errors = new ArrayList<>();
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
        if (!errors.contains(astNode.getTokenLine())) {
            errors.add(astNode.getTokenLine());
            getContext().createLineViolation(this, MESSAGE, astNode);
        }
    }
}
