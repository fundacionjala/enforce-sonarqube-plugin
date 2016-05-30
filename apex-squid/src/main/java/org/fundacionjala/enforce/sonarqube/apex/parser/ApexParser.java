/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.SquidBundle;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexGrammar;
import org.fundacionjala.enforce.sonarqube.apex.lexer.ApexLexer;

/**
 * Builds a {@link Parser} instance for Apex. Required an configuration.
 */
public class ApexParser {

    /**
     * Stores an error message when configuration is null.
     */
    private static final String ERROR_MESSAGE = SquidBundle.getStringFromBundle("parser.messages.error");

    /**
     * Default constructor.
     */
    private ApexParser() {
    }

    /**
     * Creates a Parser integrated with Grammar and Lexer.
     *
     * @param config apex configuration.
     * @return a parser
     * @throws IllegalArgumentException when configuration is null.
     */
    public static Parser<Grammar> create(ApexConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        return Parser.builder(ApexGrammar.create())
                .withLexer(ApexLexer.create(config)).build();
    }
}
