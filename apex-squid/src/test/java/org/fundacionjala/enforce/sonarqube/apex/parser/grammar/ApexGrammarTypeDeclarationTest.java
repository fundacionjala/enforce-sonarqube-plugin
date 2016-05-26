/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE_DECLARATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarTypeDeclarationTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(TYPE_DECLARATION);
    }

    @Test
    public void positiveRulesForClass() {
        assertThat(parser)
                .matches("public with sharing class MyClass{}")
                .matches("private without sharing class MyClass{}")
                .matches("public with sharing class MyClass implements YourClass{}")
                .matches("public without sharing class MyClass extends YourClass{}")
                .matches("public with sharing class MyClass implements YourClass{}")
                .matches("static without sharing class MyClass implements YourClass{}")
                .matches("public with sharing class MyClass extends YourClass{}")
                .matches("public enum MyEnum {e1, e2}")
                .matches("public static enum MyEnum {ENUM_VALUE, ANOTHER_ENUM_VALUE}")
                .matches("public class MyClass implementS YourClass {}")
                .matches("Public class MyClass extends YourClass {}")
                .matches("Class MyClass {}");
    }

    @Test
    public void negativeRulesForClass() {
        assertThat(parser)
                .notMatches("publicstaticclassMyClass{}")
                .notMatches("public_classwithsharingclassMyClass{}")
                .notMatches("privateclasswithoutsharingclassMyClass {}")
                .notMatches("public with_sharing class MyClass implements YourClass {}")
                .notMatches("static without sharing 5class MyClass implements YourClass {}")
                .notMatches("public with sharing class 9MyClass extends YourClass {}")
                .notMatches("static without sharing class MyClass enum extends YourClass {}");
    }

    @Test
    public void positiveRulesForInterface() {
        assertThat(parser)
                .matches("public with sharing interface MyClass{}")
                .matches("public with sharing interface MyClass{}")
                .matches("private without sharing interface MyClass{}")
                .matches("public with sharing interface MyClass implements YourClass{}")
                .matches("public without sharing interface MyClass extends YourClass{}")
                .matches("public with sharing interface MyClass implements YourClass, OtherClass{}")
                .matches("static without sharing interface MyClass implements YourClass{}")
                .matches("public with sharing interface MyClass extends YourClass{}")
                .matches("static without sharing interface MyClass extends YourClass{}");
    }

    @Test
    public void negativeRulesForInterface() {
        assertThat(parser)
                .notMatches("public interface MyClass implementS YourClass {")
                .notMatches("public  interface  with sharing class MyClass {")
                .notMatches("Interface MyClass {")
                .notMatches("publicstatic interface MyClass {")
                .notMatches("private class without sharing interface MyClass {")
                .notMatches("PublicinterfaceMyClassextendsYourClass{")
                .notMatches("public with_sharing interface MyClass implements YourClass {")
                .notMatches("static without sharing 5interface MyClass implements YourClass {")
                .notMatches("public with sharing interface 9MyClass extends YourClass {")
                .notMatches("static without sharing interface MyClass enum extends YourClass {");
    }
}
