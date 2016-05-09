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
