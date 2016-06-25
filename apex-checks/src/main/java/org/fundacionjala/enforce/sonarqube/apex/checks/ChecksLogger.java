/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChecksLogger {

    private static final Logger LOGGER = Logger.getLogger(ChecksLogger.class.getName());

    private static boolean configured;

    private static void configureLogger() {
        LOGGER.addHandler(new ConsoleHandler());
        LOGGER.setLevel(Level.WARNING);
        configured = true;
    }

    public static void logCheckError(String sourceClass, String sourceMethod, String message) {
        if (!configured) {
            configureLogger();
        }
        
        LOGGER.logp(Level.WARNING, sourceClass, sourceMethod, message);
    }
}
