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

import org.junit.Test;
import org.junit.Before;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;

import static org.sonar.sslr.tests.Assertions.assertThat;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.APEX_GRAMMAR;

public class ApexGrammarTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(APEX_GRAMMAR);
    }

    @Test
    public void correctRuleBasic() {
        assertThat(parser)
                .matches("public class MyClass {"
                        + "public boolean MyMethod(){"
                        + "int name=0;while(true){"
                        + "insert accout;"
                        + "}"
                        + "}"
                        + "}");
    }

    @Test
    public void correctRuleMoreImplements() {
        assertThat(parser)
                .matches("public with sharing class Class1 implements YourClass {"
                        + "public void MyMethod(int myParameter){"
                        + "int number = myParameter;"
                        + "}"
                        + "}");
    }

    @Test
    public void correctRuleMoreExtends() {
        assertThat(parser)
                .matches("public with sharing class Class1 extends YourClass {"
                        + "public int MyMethod(){"
                        + "int number = 0;"
                        + "}"
                        + "}");
    }

    @Test
    public void correctRuleBasicVariable() {
        assertThat(parser)
                .matches("public class MyClass {"
                        + "int myVariable;"
                        + "}");
    }

    @Test
    public void correctRuleMoreImplementsVariableAndMethod() {
        assertThat(parser)
                .matches("public with sharing class Class1 implements YourClass {"
                        + "public boolean my_Variable = true;"
                        + "public boolean MyMethod(){}"
                        + "}");
    }

    @Test
    public void correctRuleMoreExtendsVariableAndMethod() {
        assertThat(parser)
                .matches("public with sharing class Class1 extends YourClass{"
                        + "private int my_Variable = 10;"
                        + "public boolean MyMethod(){}"
                        + "}");
    }
}
