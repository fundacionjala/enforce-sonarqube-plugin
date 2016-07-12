/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.toolkit;

import org.sonar.sslr.toolkit.Toolkit;

public class ApexToolkit {

    private ApexToolkit() {}

    public static void main(String[] args) {
        Toolkit toolkit = new Toolkit("SSLR :: Apex :: Toolkit", new ApexConfigurationModel());
        toolkit.run();
    }
}
