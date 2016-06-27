/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logs the messages that can be created when executing a check analysis.
 */
public class ChecksLogger {
    
    /**
     * The logger used for all the checks' messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ChecksLogger.class.getName());
    
    /**
     * Flag to determine if the logger has been previously set up.
     */
    private static boolean configured;
    
    /**
     * Sets up the logger.
     */
    private static void configureLogger() {
        LOGGER.addHandler(new ConsoleHandler());
        LOGGER.setLevel(Level.WARNING);
        configured = true;
    }
    
    /**
     * Logs a message for a check.
     * @param sourceClass The check class.
     * @param sourceMethod Method that throws the message.
     * @param message Value of the message.
     */
    public static void logCheckError(String sourceClass, String sourceMethod, String message) {
        if (!configured) {
            configureLogger();
        }
        
        LOGGER.logp(Level.WARNING, sourceClass, sourceMethod, message);
    }
}
