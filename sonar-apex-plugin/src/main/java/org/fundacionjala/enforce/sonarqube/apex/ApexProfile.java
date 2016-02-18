/*
 * The MIT License
 *
 * Copyright 2016 Fundacion Jala.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.fundacionjala.enforce.sonarqube.apex;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.squidbridge.annotations.AnnotationBasedProfileBuilder;

import org.fundacionjala.enforce.sonarqube.apex.checks.CheckList;

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
        RulesProfile rules = annotationBasedProfileBuilder.build(
                CheckList.REPOSITORY_KEY,
                CheckList.SONAR_WAY_PROFILE,
                Apex.KEY,
                CheckList.getChecks(),
                validation);
        return rules;
    }
}
