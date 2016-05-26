/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex;

import com.google.common.collect.ImmutableList;
import org.fundacionjala.enforce.sonarqube.apex.cpd.ApexCpdMapping;
import org.fundacionjala.enforce.sonarqube.apex.rules.ApexCommonRulesDecorator;
import org.fundacionjala.enforce.sonarqube.apex.rules.ApexCommonRulesEngine;
import org.fundacionjala.enforce.sonarqube.apex.rules.ApexRulesDefinition;
import org.sonar.api.SonarPlugin;

import java.util.List;

/**
 * It's an entry-point to declare its extensions.
 */
public class ApexPlugin extends SonarPlugin {

    /**
     * Returns a list of the implemented extensions.
     *
     * @return a list of the extensions.
     */
    @Override
    public List getExtensions() {
        return ImmutableList.of(
                Apex.class,
                ApexCpdMapping.class,

                ApexSquidSensor.class,
                ApexRulesDefinition.class,
                ApexProfile.class,

                ApexCommonRulesDecorator.class,
                ApexCommonRulesEngine.class);
    }
}
