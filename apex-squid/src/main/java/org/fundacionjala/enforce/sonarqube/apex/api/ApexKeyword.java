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
 * Enum save the keywords that are used throughout the squid module.
 */
public enum ApexKeyword implements TokenType {

/**
     * RESERVED WORDS AND LITERALS.
     */
    ABSTRACT("abstract"),
    ACTIVATE("activate"),
    ANOTATION("anotation"),
    ANY("any"),
    AFTER("after"),
    BEFORE("before"),
    BOOLEAN("boolean"),
    BREAK("break"),
    BYTE("byte"),
    CATCH("catch"),
    CATEGORY("category"),
    CONTINUE("continue"),
    CONVERT_CURRENCY("convertcurrency"),
    CHAR("char"),
    CLASS("class"),
    DATA("data"),
    DELETE("delete"),
    DO("do"),
    DOUBLE("double"),
    ELSE("else"),
    ENUM("enum"),
    EXTENDS("extends"),
    EXCEPTION("exception"),
    FINAL("final"),
    FINALLY("finally"),
    FIRST("first"),
    FLOAT("float"),
    FOR("for"),
    GET("get"),
    GROUP("group"),
    IF("if"),
    INSERT("insert"),
    INT("int"),
    INTERFACE("interface"),
    INSTANCEOF("instanceof"),
    IMPLEMENTS("implements"),
    ITERATOR("iterator"),
    LAST("last"),
    LIMIT("limit"),
    LIST("list"),
    LONG("long"),
    MAP("map"),
    MERGE("merge"),
    NATIVE("native"),
    NETWORK("network"),
    NEW("new"),
    NULL("null"),
    OFFSET("offset"),
    PRIVATE("private"),
    PROTECTED("protected"),
    PUBLIC("public"),
    RETURN("return"),
    RETURNING("returning"),
    SAVE_POINT("savepoint"),
    SET("set"),
    SEARCH("search"),
    SHARING("sharing"),
    SHORT("short"),
    SUPER("super"),
    STAT("stat"),
    STATIC("static"),
    STRICTFP("strictfp"),
    SYNCHRONIZED("synchronized"),
    THIS("this"),
    TO_LABEL("tolabel"),
    TRANSIENT("transient"),
    THROW("throw"),
    TRY("try"),
    UNDELETE("undelete"),
    UPDATE("update"),
    UPSERT("upsert"),
    VOID("void"),
    VOLATILE("volatile"),
    WHILE("while"),
    WITH("with"),
    WITHOUT("without"),

    /**
     * APEX ANNOTATIONS.
     */
    AURA_ENABLED("AuraEnabled"),
    DEPRECATED("deprecated"),
    FUTURE("future"),
    INVOCABLE_METHOD("InvocableMethod"),
    INVOCABLE_VARIABLE("InvocableVariable"),
    IS_TEST("isTest"),
    READ_ONLY("ReadOnly"),
    REMOTE_ACTION("RemoteAction"),
    TEST_SETUP("testSetup"),
    TEST_VISIBLE("TestVisible"),
    
    /**
     * Pseudo regular expressions.
     */
    UPPERCASE_LITERAL("L"),
    LOWERCASE_LITERAL("l");
    
    /**
     * Save the value with enums.
     */
    private final String value;

    private ApexKeyword(String value) {
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
    public boolean hasToBeSkippedFromAst(AstNode an) {
        return Boolean.FALSE;
    }

}
