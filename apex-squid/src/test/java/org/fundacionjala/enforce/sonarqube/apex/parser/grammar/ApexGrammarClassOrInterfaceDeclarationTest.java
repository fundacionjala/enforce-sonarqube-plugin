/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_DECLARATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarClassOrInterfaceDeclarationTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(CLASS_OR_INTERFACE_DECLARATION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("with sharing class MyClass{}")
                .matches("with sharing interface MyClass{}")
                .matches("without sharing class MyClass{}")
                .matches("without sharing interface MyClass{}")
                .matches("with sharing class MyClass extends YourClass{}")
                .matches("with sharing class MyClass implements YourClass{}")
                .matches("with sharing class MyClass implements YourClass, MyClass, ThisClass{}")
                .matches("with sharing interface MyClass extends YourClass{}")
                .matches("with sharing interface MyClass extends exception{}")
                .matches("with sharing interface MyClass implements YourClass{}");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("class1MyClass{}")
                .matches("Interface MyClass{}")
                //should fail when extends and iterfaces aren't correctly declared
                .notMatches("with sharing _classMyClassextendsYourClass{}")
                .notMatches("with sharing class-MyClass implements YourClass{}")
                .notMatches("with sharing interface1 MyClass extends YourClass{}")
                .notMatches("with sharing interface MyClass_implements YourClass{}");
    }
}
