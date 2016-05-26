/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.rules;

import org.fundacionjala.enforce.sonarqube.apex.Apex;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.squidbridge.commonrules.api.CommonRulesDecorator;

/**
 * Enables the decoration of the language.
 */
public class ApexCommonRulesDecorator extends CommonRulesDecorator {

    /**
     * Registers the Apex's key for decoration.
     *
     * @param fileSystem file system.
     * @param checkFactory check factory.
     * @param perspectives  resource perspective.
     */
    public ApexCommonRulesDecorator(FileSystem fileSystem, CheckFactory checkFactory, ResourcePerspectives perspectives) {
        super(Apex.KEY, fileSystem, checkFactory, perspectives);
    }
}
