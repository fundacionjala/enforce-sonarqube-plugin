/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.lexer;

import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.ANY_CHAR;
import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.commentRegexp;
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
    private static final String IDENTIFIER_PATTERN = "(_{0,2}[a-zA-Z][_a-zA-Z0-9]*)+";

    /**
     * Stores a pattern to identify a String.
     */
    private static final String STRING_PATTERN = "'([^'\\\\]*+(\\\\[\\s\\S])?+)*+'";

    private static final String DECIMAL_EXPONENT_NUMBER = "(?:[Ee][+-]?+[0-9]++)";
    private static final String HEXADECIMAL_EXPONENT_NUMBER = "(?:[Pp][+-]?+[0-9]++)";
    /**
     * Stores an numeric pattern.
     */
    private static final String INTEGER_LITERAL_PATTERN = "(?:(0[Xx]\\p{XDigit}+|[1-9](\\d)*|0([0-7]*))([lL]?))";
    
    /**
     * 
     * Stores an hexadecimal pattern.
     */
    
    private static final String DECIMAL_FLOATING_POINT_LITERAL_PATTERN = "(?:\\d+\\.(\\d)*("+DECIMAL_EXPONENT_NUMBER+")?([fFdD])?"
            + "|"+"\\.(\\d)+("+DECIMAL_EXPONENT_NUMBER+")?([FfdD])?"
            + "|"+"([\\d])+"+DECIMAL_EXPONENT_NUMBER+"([fFdD])?"
            + "|"+"([\\d])+("+DECIMAL_EXPONENT_NUMBER+")?[fFdD])";
    
    private static final String HEXADECIMAL_FLOATING_POINT_LITERAL_PATTERN = "(?:0[Xx]\\p{XDigit}+\\.?"+HEXADECIMAL_EXPONENT_NUMBER+"([fFdD])?"
            + "|0[Xx]\\p{XDigit}*\\.\\p{XDigit}+"+HEXADECIMAL_EXPONENT_NUMBER+"([fFdD])?)";

    
    private static final String SINGLE_LINE_PATTERN = "//[^\\n\\r]*+";
    
    private static final String MULTI_LINE_PATTERN = "/\\*" + ANY_CHAR + "*?\\*\\/";
    
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
                .withChannel(commentRegexp(SINGLE_LINE_PATTERN))
                .withChannel(commentRegexp(MULTI_LINE_PATTERN))
                .withChannel(regexp(ApexTokenType.DECIMAL_FLOATING_POINT_LITERAL, DECIMAL_FLOATING_POINT_LITERAL_PATTERN))
                .withChannel(regexp(ApexTokenType.HEXADECIMAL_FLOATING_POINT_LITERAL, HEXADECIMAL_FLOATING_POINT_LITERAL_PATTERN))
                .withChannel(regexp(ApexTokenType.INTEGER_LITERAL, INTEGER_LITERAL_PATTERN))
                .withChannel(regexp(ApexTokenType.STRING, STRING_PATTERN))
                .withChannel(new IdentifierAndKeywordChannel(IDENTIFIER_PATTERN,
                        Boolean.FALSE, ApexKeyword.values()))
                .withChannel(new PunctuatorChannel(ApexPunctuator.values()))
                .withChannel(new BlackHoleChannel(BLACK_HOLE))
                .build();
    }
}
