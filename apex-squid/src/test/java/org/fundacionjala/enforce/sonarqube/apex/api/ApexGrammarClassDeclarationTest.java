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

import org.junit.Test;

import com.sonar.sslr.api.Grammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_DECLARATION;

public class ApexGrammarClassDeclarationTest {

    private final Grammar grammarBuilder = ApexGrammar.create(Boolean.FALSE);

    @Test
    public void positiveRules() {
        assertThat(grammarBuilder.rule(CLASS_DECLARATION))
                .matches("classMyClass{}")
                .matches("interfaceMyClass{}")
                .matches("classMyClassextendsYourClass{}")
                .matches("classMyClassimplementsYourClass{}")
                .matches("interfaceMyClassextendsYourClass{}")
                .matches("interfaceMyClassimplementsYourClass{}");
    }

    @Test
    public void negativeRules() {
        assertThat(grammarBuilder.rule(CLASS_DECLARATION))
                .notMatches("class1MyClass{}")
                .notMatches("InterfaceMyClass{}")
                .notMatches("_classMyClassextendsYourClass{}")
                .notMatches("class-MyClass implements YourClass{}")
                .notMatches("interface1 MyClass extends YourClass{}")
                .notMatches("interface MyClass_implements YourClass{}");
    }

    @Test
    public void positiveRulesForClass() {
        assertThat(grammarBuilder.rule(CLASS_DECLARATION))
                .matches("publicclassMyClass{}")
                .matches("publicwithsharingclassMyClass{}")
                .matches("privatewithoutsharingclassMyClass{}")
                .matches("publicclassMyClassimplementsYourClass{}")
                .matches("publicclassMyClassextendsYourClass{}")
                .matches("publicwithsharingclassMyClassimplementsYourClass{}")
                .matches("staticwithoutsharingclassMyClassimplementsYourClass{}")
                .matches("publicwithsharingclassMyClassextendsYourClass{}")
                .matches("staticwithoutsharingclassMyClassextendsYourClass{}");
    }

    @Test
    public void negativeRulesForClass() {
        assertThat(grammarBuilder.rule(CLASS_DECLARATION))
                .notMatches("Class MyClass {")
                .notMatches("publicstaticclassMyClass{")
                .notMatches("public_classwithsharingclassMyClass{")
                .notMatches("privateclasswithoutsharingclassMyClass {")
                .notMatches("public class MyClass implementS YourClass {")
                .notMatches("Public class MyClass extends YourClass {")
                .notMatches("public with_sharing class MyClass implements YourClass {")
                .notMatches("static without sharing 5class MyClass implements YourClass {")
                .notMatches("public with sharing class 9MyClass extends YourClass {")
                .notMatches("static without sharing class MyClass enum extends YourClass {");
    }

    @Test
    public void positiveRulesForInterface() {
        assertThat(grammarBuilder.rule(CLASS_DECLARATION))
                .matches("publicinterfaceMyClass{}")
                .matches("publicwithsharinginterfaceMyClass{}")
                .matches("privatewithoutsharinginterfaceMyClass{}")
                .matches("publicinterfaceMyClassimplementsYourClass{}")
                .matches("publicinterfaceMyClassextendsYourClass{}")
                .matches("publicwithsharinginterfaceMyClassimplementsYourClass{}")
                .matches("staticwithoutsharinginterfaceMyClassimplementsYourClass{}")
                .matches("publicwithsharinginterfaceMyClassextendsYourClass{}")
                .matches("staticwithoutsharinginterfaceMyClassextendsYourClass{}");
    }

    @Test
    public void negativeRulesForInterface() {
        assertThat(grammarBuilder.rule(CLASS_DECLARATION))
                .notMatches("Interface MyClass {}")
                .notMatches("publicstatic interface MyClass {}")
                .notMatches("public  interface  with sharing class MyClass {}")
                .notMatches("private class without sharing interface MyClass {}")
                .notMatches("public interface MyClass implementS YourClass {}")
                .notMatches("PublicinterfaceMyClassextendsYourClass{}")
                .notMatches("public with_sharing interface MyClass implements YourClass {}")
                .notMatches("static without sharing 5interface MyClass implements YourClass {}")
                .notMatches("public with sharing interface 9MyClass extends YourClass {}")
                .notMatches("static without sharing interface MyClass enum extends YourClass {}");
    }
}
