/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
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
    ANY("any"),
    AUTONOMOUS("autonomous"),
    AFTER("after"),
    ARRAY("array"),
    BEFORE("before"),
    BEGIN("begin"),
    BIGDECIMAL("bigdecimal"),
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
    ELSE("else"),
    END("end"),
    ENUM("enum"),
    EXCEPTION("exception"),
    EXIT("exit"),
    EXTENDS("extends"),
    EXPORT("export"),
    FALSE("false"),
    FINAL("final"),
    FINALLY("finally"),
    FIRST("first"),
    FLOAT("float"),
    FOR("for"),
    GET("get"),
    GLOBAL("global"),
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
    LOOP("loop"),
    MAP("map"),
    MERGE("merge"),
    NETWORK("network"),
    NEW("new"),
    NULL("null"),
    NUMBER("number"),
    OF("of"),
    OFFSET("offset"),
    OUTER("outer"),
    OVERRIDE("override"),
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
    TESTMETHOD("testmethod"),
    THIS("this"),
    TO_LABEL("tolabel"),
    TRIGGER("trigger"),
    TRANSIENT("transient"),
    TRANSACTION("transaction"),
    THEN("then"),
    THROW("throw"),
    TRUE("true"),
    TRY("try"),
    UNDELETE("undelete"),
    UPDATE("update"),
    UPSERT("upsert"),
    VIRTUAL("virtual"),
    VOID("void"),
    VOLATILE("volatile"),
    WHEN("when"),
    WHILE("while"),
    WITH("with"),
    WITHOUT("without"),
    
      /**
     * SOQL ANNOTATIONS.
     */
    DATABASE("Database"),
    QUERY("query"),
    //Select Notations
    SELECT("SELECT"),
    COUNT("COUNT"),
    //From Notations
    FROM("FROM"),
    AS("AS"),
    USING("USING"),//Pending to implement
    //Where Notations
    WHERE("WHERE"),
    NOT_SOQL("NOT"),
    AND_SOQL("AND"),
    OR_SOQL("OR"),
    IN("IN"),
    INCLUDES("includes"),
    EXCLUDES("excludes"),
    LIKE("LIKE"),
    //Limit Notations
    //Order Notations
    ORDER("ORDER"),
    BY("BY"),
    ASC("ASC"),
    DES("DESC"),
    NULLS("NULLS"),
    //Group Notations
    ROLLUP("ROLLUP"),
    CUBE("CUBE")
    ;

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
