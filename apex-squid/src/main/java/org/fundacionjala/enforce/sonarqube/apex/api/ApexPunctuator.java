/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.api;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;

/**
 * The enum tokens and GrammarRuleKey handles of punctuation for the Squid
 * module.
 */
public enum ApexPunctuator implements TokenType {

    /**
     * SEPARATORS.
     */
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    LBRACKET("["),
    RBRACKET("]"),
    COLON(":"),
    SEMICOLON(";"),
    COMMA(","),
    DOT("."),
    AT("@"),
    QUESTION("?"),
    OR("|"),
    AND("&"),
    EXCOR("^"),
    NOT("!"),
    EQUALS("=="),
    NOTEQUALS("!="),
    
    /**
     * OPERATORS.
     */
    DIV("/"),
    UNDERSCORE("_"),
    MINUS("-"),
    MOD("%"),
    PLUS("+"),
    STAR("*"),
    GT(">"),
    LT("<"),
    LEQUT("<="),
    GEQUT(">="),
    MLT("<<"),
    MGTEQU(">>="),
    
    /**
     * ASSIGNMENT OPERATORS.
     */
    ASSIGN("="),
    DIVEQU("/="),
    MINUSEQU("-="),
    PLUSEQU("+="),
    MODEQU("%="),
    STAREQU("*="),
    LTLTEQU("<<="),
    GTGTEQU(">>="),
    GTGTGTEQU(">>>="),
    ANDEQU("&="),
    OREQU("^="),
    EXCOREQU("|="),
    MAP_ASSIGN("=>");

    /**
     * Save the value of each enum.
     */
    private final String value;

    private ApexPunctuator(String value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean hasToBeSkippedFromAst(AstNode node) {
        return Boolean.FALSE;
    }
}
