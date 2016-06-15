/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.rules;

import org.sonar.api.server.debt.DebtRemediationFunction;
import org.sonar.api.server.rule.RulesDefinition.DebtRemediationFunctions;

/**
 * Determines the remediation cost of a function. 
 */
public class Remediation {

    String func;
    String constantCost;
    String linearDesc;
    String linearOffset;
    String linearFactor;

    public DebtRemediationFunction remediationFunction(DebtRemediationFunctions debt) {
        if (func.startsWith("Constant")) {
            return debt.constantPerIssue(constantCost.replace("mn", "min"));
        }
        if ("Linear".equals(func)) {
            return debt.linear(linearFactor.replace("mn", "min"));
        }
        return debt.linearWithOffset(linearFactor.replace("mn", "min"), linearOffset.replace("mn", "min"));
    }
}
