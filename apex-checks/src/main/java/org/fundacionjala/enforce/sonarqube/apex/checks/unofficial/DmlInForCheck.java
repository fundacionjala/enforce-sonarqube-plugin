/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;

/**
 * Check for a DML is not within a "for loop".
 */
@Rule(key = DmlInForCheck.CHECK_KEY)
public class DmlInForCheck extends DmlStatementCheck {

    /**
     * Stores a message template.
     */
    private static final String MESSAGE = ChecksBundle.getStringFromBundle("DmlInForCheckMessage");

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1004";

    public DmlInForCheck() {
        ruleKey = ApexGrammarRuleKey.FOR_STATEMENT;
        message = MESSAGE;
    }
}
