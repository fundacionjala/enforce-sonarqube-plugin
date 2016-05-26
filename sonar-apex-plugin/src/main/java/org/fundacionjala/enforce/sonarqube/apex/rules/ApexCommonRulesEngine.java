/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.rules;

import org.fundacionjala.enforce.sonarqube.apex.Apex;
import org.sonar.squidbridge.commonrules.api.CommonRulesEngine;
import org.sonar.squidbridge.commonrules.api.CommonRulesRepository;

/**
 * Enables the use of common rules.
 */
public class ApexCommonRulesEngine extends CommonRulesEngine {

    /**
     * Default constructor to define the Apex's key.
     */
    public ApexCommonRulesEngine() {
        super(Apex.KEY);
    }

    /**
     * Enables rules in the repository.
     *
     * @param repository rule repository.
     */
    @Override
    protected void doEnableRules(CommonRulesRepository repository) {
        repository
                .enableDuplicatedBlocksRule()
                .enableInsufficientCommentDensityRule(null)
                .enableInsufficientLineCoverageRule(null)
                .enableInsufficientBranchCoverageRule(null);
    }
}
