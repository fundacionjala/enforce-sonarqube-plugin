/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex;

import org.fundacionjala.enforce.sonarqube.apex.checks.CheckList;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.squidbridge.annotations.AnnotationBasedProfileBuilder;

/**
 * Defines a profile which is automatically registered during sonar startup.
 */
public class ApexProfile extends ProfileDefinition {

    /**
     * Stores an annotation rule.
     */
    private final RuleFinder ruleFinder;

    /**
     * Default constructor to store a rule.
     *
     * @param ruleFinder rules.
     */
    public ApexProfile(RuleFinder ruleFinder) {
        this.ruleFinder = ruleFinder;
    }

    /**
     * Returns a {@link RulesProfile} based on a list of classes annotated.
     *
     * @param validation validation message.
     * @return a rule profile.
     */
    @Override
    public RulesProfile createProfile(ValidationMessages validation) {
        AnnotationBasedProfileBuilder annotationBasedProfileBuilder = new AnnotationBasedProfileBuilder(ruleFinder);
        return annotationBasedProfileBuilder.build(
                CheckList.REPOSITORY_KEY,
                CheckList.SONAR_WAY_PROFILE,
                Apex.KEY,
                CheckList.getChecks(),
                validation);
    }
}
