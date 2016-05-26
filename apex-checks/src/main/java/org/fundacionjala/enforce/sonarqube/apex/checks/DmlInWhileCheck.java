/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

/**
 * Check for a DML is not within a "while".
 */
@Rule(
        key = DmlInWhileCheck.CHECK_KEY,
        priority = Priority.CRITICAL,
        name = "\"while\" loop should not have DML statement",
        description = "DML statement in a while",
        tags = Tags.BUG
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class DmlInWhileCheck extends DmlStatementCheck {

    /**
     * Stores a message template.
     */
    private static final String MESSAGE = "The DML statement \"%s\", can not be inside a while loop";

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1003";

    /**
     * The variables are initialized and subscribe the base rule.
     */
    public DmlInWhileCheck() {
        ruleKey = ApexGrammarRuleKey.WHILE_STATEMENT;
        message = MESSAGE;
    }
}
