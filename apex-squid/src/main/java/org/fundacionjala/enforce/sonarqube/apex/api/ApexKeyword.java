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
    ON("on"),
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
    WEBSERVICE("webservice"),
    WHEN("when"),
    WHILE("while"),
    WITH("with"),
    WITHOUT("without"),
    /**
     * SOQL ANNOTATIONS.
     */
    //Select Notations
    SELECT("SELECT"),
    COUNT("COUNT"),
    SUM("SUM"),
    AVG("AVG"),
    MAX("MAX"),
    MIN("MIN"),
    COUNT_DISTINCT("COUNT_DISTINCT"),
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
    // Date Methods for SOQL
    CALENDAR_MONTH("CALENDAR_MONTH"),
    CALENDAR_QUARTER("CALENDAR_QUARTER"),
    CALENDAR_YEAR("CALENDAR_YEAR"),
    DAY_IN_MONTH("DAY_IN_MONTH"),
    DAY_IN_WEEK("DAY_IN_WEEK"),
    DAY_IN_YEAR("DAY_IN_YEAR"),
    DAY_ONLY("DAY_ONLY"),
    FISCAL_MONTH("FISCAL_MONTH"),
    FISCAL_QUARTER("FISCAL_QUARTER"),
    FISCAL_YEAR("FISCAL_YEAR"),
    HOUR_IN_DAY("HOUR_IN_DAY"),
    WEEK_IN_MONTH("WEEK_IN_MONTH"),
    WEEK_IN_YEAR("WEEK_IN_YEAR"),
    //Date Literals in SOQL
    YESTERDAY("YESTERDAY"),
    TODAY("TODAY"),
    TOMORROW("TOMORROW"),
    LAST_WEEK("LAST_WEEK"),
    THIS_WEEK("THIS_WEEK"),
    NEXT_WEEK("NEXT_WEEK"),
    LAST_MONTH("LAST_MONTH"),
    THIS_MONTH("THIS_MONTH"),
    NEXT_MONTH("NEXT_MONTH"),
    THIS_QUARTER("THIS_QUARTER"),
    LAST_QUARTER("LAST_QUARTER"),
    NEXT_QUARTER("NEXT_QUARTER"),
    THIS_YEAR("THIS_YEAR"),
    LAST_YEAR("LAST_YEAR"),
    NEXT_YEAR("NEXT_YEAR"),
    THIS_FISCAL_QUARTER("THIS_FISCAL_QUARTER"),
    LAST_FISCAL_QUARTER("LAST_FISCAL_QUARTER"),
    NEXT_FISCAL_QUARTER("NEXT_FISCAL_QUARTER"),
    LAST_90_DAYS("LAST_90_DAYS"),
    NEXT_90_DAYS("NEXT_90_DAYS"),
    THIS_FISCAL_YEAR("THIS_FISCAL_YEAR"),
    LAST_FISCAL_YEAR("LAST_FISCAL_YEAR"),
    NEXT_FISCAL_YEAR("NEXT_FISCAL_YEAR"),
    NEXT_N_FISCAL_YEARS("NEXT_N_FISCAL_YEARS"),
    LAST_N_FISCAL_YEARS("LAST_N_FISCAL_YEARS"),
    NEXT_N_FISCAL_QUARTERS("NEXT_N_FISCAL_QUARTERS"),
    LAST_N_FISCAL_QUARTERS("LAST_N_FISCAL_QUARTERS"),
    LAST_N_DAYS("LAST_N_DAYS"),
    NEXT_N_DAYS("NEXT_N_DAYS"),
    NEXT_N_WEEKS("NEXT_N_WEEKS"),
    LAST_N_WEEKS("LAST_N_WEEKS"),
    NEXT_N_MONTHS("NEXT_N_MONTHS"),
    LAST_N_MONTHS("LAST_N_MONTHS"),
    NEXT_N_QUARTERS("NEXT_N_QUARTERS"),
    LAST_N_QUARTERS("LAST_N_QUARTERS"),
    NEXT_N_YEARS("NEXT_N_YEARS"),
    LAST_N_YEARS("LAST_N_YEARS"),
    
    //Limit Notations
    LIMIT("limit"),
    //Order Notations
    ORDER("ORDER"),
    BY("BY"),
    ASC("ASC"),
    DES("DESC"),
    NULLS("NULLS"),
    //Group Notations
    ROLLUP("ROLLUP"),
    CUBE("CUBE");

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
