/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_MEMBER;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarClassOrInterfaceMemberTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(CLASS_OR_INTERFACE_MEMBER);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                //can be an initializer declaration (only static modifier allowed)
                .matches("static {integer a;}")
                .matches("{integer x=3; integer y;}") 
                //all the other kinds of member can have the other type of modifiers, and they can be:
                //a class or an interface declaration
                .matches("public without sharing class ClassName {}")
                .matches("private with sharing interface iClassName {}")
                //an enum declaration
                .matches("public enum EnumName{A,B}")
                //a constructor
                .matches("protected ClassName() {}")
                //a field
                .matches("private static integer field=3;")
                //a method
                .matches("public static void doSomething(integer p1);")
                //a property
                .matches("private string Prop {private get; set;}")
                .matches("@someAnnotation\n"
                        + "public global static void aMethodWithLoops(list<integer> collection) {}")
                //case return type and param
                .matches("private Case caseReturnMethod(Case c){}");
    }
    
    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("public without sharing without class ClassName {}")
                .notMatches("private with sharing interface iClassName name {}")
                .notMatches("publics enum EnumName{A,B}")
                .notMatches("private static integer field=3%;")
                .notMatches("public static void doSomething(integerp1);")
                .notMatches("private string Pr op {private get; set;}")
                .notMatches("@someAnnotation\n"
                        + "public global static void aMethodWithLoops(list<<integer> collection) {}");
    }
}
