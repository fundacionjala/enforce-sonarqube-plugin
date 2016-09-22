package org.fundacionjala.sonarqube.api;

import org.sonar.sslr.grammar.GrammarRuleKey;

public enum ApexKeyword implements GrammarRuleKey{

    ABSTRACT("abstract"),
    CLASS("class"),
    FINAL("final"),
    GLOBAL("global"),
    INTERFACE("interface"),
    PRIVATE("private"),
    PROTECTED("protected"),
    PUBLIC("public"),
    STATIC("static"),
    TRANSIENT("transient");

    private final String value;

    ApexKeyword(String value) {this.value = value;}

    public String getName() {
        return name();
    }

    public String getValue() {
        return value;
    }

    public static String[] keywordValues() {
        ApexKeyword[] keywordsEnum = ApexKeyword.values();
        String[] keywords = new String[keywordsEnum.length];
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = keywordsEnum[i].getValue();
        }
        return keywords;
    }
}
