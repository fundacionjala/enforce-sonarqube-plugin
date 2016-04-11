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
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_DECLARATION;
import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;
import static org.sonar.sslr.tests.Assertions.assertThat;

/**
 *
 * @author vicente_rodriguez
 */
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
                .matches("with sharing interface MyClass extends YourClass{}")
                .matches("with sharing interface MyClass implements YourClass{}");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("class1MyClass{}")
                .notMatches("Interface MyClass{}")
                //should fail when you have no with or without sharing
                .notMatches("interface MyClass{}")
                .notMatches("class MyClass{}")
                .notMatches("with InterfaceMyClass{}")
                .notMatches("without InterfaceMyClass{}")
                .notMatches("sharing InterfaceMyClass{}")
                //should fail when extends and iterfaces aren't correctly declared
                .notMatches("with sharing _classMyClassextendsYourClass{}")
                .notMatches("with sharing class-MyClass implements YourClass{}")
                .notMatches("with sharing interface1 MyClass extends YourClass{}")
                .notMatches("with sharing interface MyClass_implements YourClass{}");
    }
}
