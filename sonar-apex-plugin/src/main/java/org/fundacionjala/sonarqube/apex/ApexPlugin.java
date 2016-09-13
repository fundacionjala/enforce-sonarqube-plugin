/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.sonarqube.apex;

import org.fundacionjala.sonarqube.apex.api.Apex;
import org.sonar.api.Plugin;

public class ApexPlugin implements Plugin {

    public static final String FILE_SUFFIXES_KEY = "sonar.apex.file.suffixes";
    private Context context;

    /**
     * This method is executed at runtime when:
     * <ul>
     * <li>Web Server starts</li>
     * <li>Compute Engine starts</li>
     * <li>Scanner starts</li>
     * </ul>
     *
     * @param context
     */
    public void define(Context context) {
        context.addExtensions(
                Apex.class,
                ApexSquidSensor.class,
                SonarComponents.class
        );
    }


}