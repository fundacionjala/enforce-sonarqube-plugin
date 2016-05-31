/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks;

import java.util.ResourceBundle;

public class ChecksBundle {

    private static ResourceBundle bundle;

    public static final String getStringFromBundle(String key) {
        if(bundle == null) {
            bundle = ResourceBundle.getBundle("ChecksResourceBundle");
        }
        return bundle.getString(key);
    }
}
