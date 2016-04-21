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
    AUTONOMOUS("autonomous"),
    AFTER("after"),
    ARRAY("array"),
    BEFORE("before"),
    BEGIN("begin"),
    BIGDECIMAL("bigdecimal"),
    BOOLEAN("boolean"),
    BREAK("break"),
    BYTE("byte"),
    CASE("case"),
    CAST("cast"),
    CATCH("catch"),
    CATEGORY("category"),
    COLLECT("collect"),
    CONST("const"),
    CONTINUE("continue"),
    CONVERT_CURRENCY("convertcurrency"),
    CHAR("char"),
    CLASS("class"),
    DATA("data"),
    DEFAULT("default"),
    DELETE("delete"),
    DO("do"),
    DOUBLE("double"),
    ELSE("else"),
    END("end"),
    ENUM("enum"),
    EXCEPTION("exception"),
    EXIT("exit"),
    EXTENDS("extends"),
    EXPORT("export"),
    FINAL("final"),
    FINALLY("finally"),
    FIRST("first"),
    FLOAT("float"),
    FOR("for"),
    GET("get"),
    GOTO("goto"),
    GROUP("group"),
    HINT("hint"),
    IF("if"),
    INNER("inner"),
    INSERT("insert"),
    INT("int"),
    INTERFACE("interface"),
    INTO("into"),
    INSTANCEOF("instanceof"),
    IMPLEMENTS("implements"),
    IMPORT("import"),
    ITERATOR("iterator"),
    JOIN("join"),
    LAST("last"),
    LIMIT("limit"),
    LIST("list"),
    LONG("long"),
    LOOP("loop"),
    MAP("map"),
    MERGE("merge"),
    NATIVE("native"),
    NETWORK("network"),
    NEW("new"),
    NULL("null"),
    NUMBER("number"),
    OF("of"),
    OFFSET("offset"),
    OUTER("outer"),
    PACKAGE("package"),
    PARALLEL("parallel"),
    PRIVATE("private"),
    PROTECTED("protected"),
    PUBLIC("public"),
    RETRIEVE("retrieve"),
    RETURN("return"),
    RETURNING("returning"),
    ROLLBACK("rollback"),
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
    SWITCH("switch"),
    THIS("this"),
    TO_LABEL("tolabel"),
    TRIGGER("trigger"),
    TRANSIENT("transient"),
    TRANSACTION("transaction"),
    THEN("then"),
    THROW("throw"),
    TRY("try"),
    UNDELETE("undelete"),
    UPDATE("update"),
    UPSERT("upsert"),
    VOID("void"),
    VOLATILE("volatile"),
    WHEN("when"),
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
    TEST_VISIBLE("TestVisible");

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
