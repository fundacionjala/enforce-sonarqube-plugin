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
package org.fundacionjala.enforce.sonarqube.apex.parser;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;

import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexGrammar;
import org.fundacionjala.enforce.sonarqube.apex.lexer.ApexLexer;

/**
 * Builds a {@link Parser} instance for Apex. Required an configuration.
 */
public class ApexParser {

    /**
     * Stores an error message when configuration is null.
     */
    private static final String ERROR_MESSAGE = "ApexConfiguration can't be null";

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
