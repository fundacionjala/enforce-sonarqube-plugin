/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.rules;

import org.fundacionjala.enforce.sonarqube.apex.Apex;
import org.fundacionjala.enforce.sonarqube.apex.checks.CheckList;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.squidbridge.annotations.AnnotationBasedRulesDefinition;

/**
 * Defines some coding rules of the same repository.
 */
public class ApexRulesDefinition implements RulesDefinition {

    /**
     * Loads apex custom rules in the repository.
     *
     * @param context builder.
     */
    @Override
    public void define(Context context) {
        NewRepository repository = context
                .createRepository(CheckList.REPOSITORY_KEY, Apex.KEY)
                .setName(CheckList.REPOSITORY_NAME);

        AnnotationBasedRulesDefinition.load(repository, Apex.KEY, CheckList.getChecks());
        repository.done();
    }
}
