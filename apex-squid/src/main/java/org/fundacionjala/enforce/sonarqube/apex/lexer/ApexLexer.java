/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.lexer;

import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;
import com.sonar.sslr.impl.channel.IdentifierAndKeywordChannel;
import com.sonar.sslr.impl.channel.PunctuatorChannel;
import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType;

import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.regexp;

/**
 * Builds a {@link Lexer} instance for Apex.
 */
public class ApexLexer {

    /**
     * Stores a pattern to identify a keyword.
     */
    private static final String KEYWORD = "(_{0,2}[a-zA-Z][a-zA-Z0-9]*)+";

    /**
     * Stores a pattern to identify a String.
     */
    private static final String STRING_PATTERN = "'([^'\\\\]*+(\\\\[\\s\\S])?+)*+'";

    /**
     * Stores an numeric pattern.
     */
    private static final String NUMERIC_PATTERN = "(0([0-7]*)?|\\d\\d*([eE][+-]?\\d+)?)[lL|fF|dD]?";
    
    /**
     * Stores an hexadecimal pattern.
     */
    private static final String HEXADECIMAL_PATTERN = "[Xx]((\\p{XDigit}+[.]?)?\\p{XDigit}+)([pP][+-]?\\d+)?[lL|fF|dD]?";
    
    /**
     * Stores a pattern to identify a black hole.
     */
    private static final String BLACK_HOLE = "[ \t\r\n]+";

    /**
     * Default constructor.
     */
    private ApexLexer() {
    }

    /**
     * Creates a Lexer, contains all channels to analyze apex language.
     *
     * @param config apex configuration.
     * @return a lexer instance.
     */
    public static Lexer create(ApexConfiguration config) {
        return Lexer.builder()
                .withCharset(config.getCharset())
                .withFailIfNoChannelToConsumeOneCharacter(Boolean.TRUE)
                .withChannel(regexp(ApexTokenType.NUMERIC, NUMERIC_PATTERN))
                .withChannel(regexp(ApexTokenType.STRING, STRING_PATTERN))
                .withChannel(regexp(ApexTokenType.HEXADECIMAL, HEXADECIMAL_PATTERN))
                .withChannel(new IdentifierAndKeywordChannel(KEYWORD,
                        Boolean.FALSE, ApexKeyword.values()))
                .withChannel(new PunctuatorChannel(ApexPunctuator.values()))
                .withChannel(new BlackHoleChannel(BLACK_HOLE))
                .build();
    }
}
