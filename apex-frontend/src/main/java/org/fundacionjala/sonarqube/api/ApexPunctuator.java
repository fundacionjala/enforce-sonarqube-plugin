package org.fundacionjala.sonarqube.api;

import org.sonar.sslr.grammar.GrammarRuleKey;

public enum ApexPunctuator implements GrammarRuleKey{

    LBRACE("{"),
    RBRACE("}"),
    DOT("."),
    COMMA(","),
    SEMICOLON(";"),
    LPAREN("("),
    RPAREN(")"),
    EQU("="),
    PLUSEQU("+="),
    MINUSEQU("-="), 
    STAREQU("*="), 
    DIVEQU("/="),
    ANDEQU("&="),
    OREQU("|="),
    HATEQU("^="),
    MODEQU("%="),
    SLEQU("<<="),
    SREQU(">>="),
    BSREQU(">>>="),
    LPOINT("<"),
    RPOINT(">");

    private final String value;
    ApexPunctuator(String word) {this.value = word;}

    public String getName() {
        return name();
    }

    public String getValue() {
        return value;
    }
}
