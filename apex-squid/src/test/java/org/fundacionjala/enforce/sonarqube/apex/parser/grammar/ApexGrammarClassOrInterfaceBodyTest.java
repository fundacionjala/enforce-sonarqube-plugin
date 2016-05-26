/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_BODY;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarClassOrInterfaceBodyTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(CLASS_OR_INTERFACE_BODY);
    }

    @Test
    public void testValidClassOrInterfaceBody() {
        assertThat(parser)
                .matches("")
                .matches("public static integer addition();"
                        + "public integer multiplication();")
                .matches("with sharing class MyClass{}"
                        + "with sharing class MyClass{}")
                .matches("static {integer suma = 0;}")
                .matches("private string myString;")
                .matches("private integer myIntVariable;"
                        + "public MyClass() {"
                        + "integer myIntVariable = 6;"
                        + "}"
                        + "public integer additition() {}")
                .matches("public integer Addition{get; set;}")
                .matches("integer prop;"
                        + "public integer Addition{get { return 3;}"
                        + "                    set{integer prop = 1;}}")
                .matches("public integer firstProperty { get; set;}"
                        + "public integer secondProperty { get; set;}")
                .matches("public static integer propertyWithParameters{"
                        + "get { return variable;}"
                        + "set { integer variable = 1;}}");
    }

    @Test
    public void testInValidClassOrInterfaceBody() {
        assertThat(parser)
                .notMatches("static public Class invalid")
                .notMatches("private integer noSemicolon")
                .notMatches("public integer wrongProperty {get set}")
                .notMatches("private WrongConstructor integer { }");
    }

}
