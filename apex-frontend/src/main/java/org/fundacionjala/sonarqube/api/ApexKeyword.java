package org.fundacionjala.sonarqube.api;

import org.sonar.sslr.grammar.GrammarRuleKey;

public enum ApexKeyword implements GrammarRuleKey{

    WITH("with"),
    WITHOUT("without"),
    SHARING("sharing"),
    ABSTRACT("abstract"),
    CLASS("class"),
    FINAL("final"),
    GLOBAL("global"),
    INTERFACE("interface"),
    PRIVATE("private"),
    PROTECTED("protected"),
    PUBLIC("public"),
    STATIC("static"),
    TRANSIENT("transient"),
    RETURNING("returning"),
    SEARCH("search"),
    STAT("stat"),
    CONVERTCURRENCY("convertcurrency"),
    SAVEPOINT("savepoint"),
    TOLABEL("tolabel"),
    GET("get"),
    AFTER("after"),
    BEFORE("before"),
    FIRST("first"),
    LAST("last"),
    CATEGORY("category"),
    NETWORK("network"),
    ITERATOR("iterator"),
    OFFSET("offset"),
    DATA("data"),
    GROUP("group"),
    LIMIT("limit"),
    EXTENDS("extends"),
    IMPLEMENTS("implements"),
    IF("if"),
    ELSE("else"),
    VOID("void"),
    THIS("this"),
    SUPER("super"),
    NEW("new");

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
