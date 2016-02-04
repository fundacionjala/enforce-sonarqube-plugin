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

import com.sonar.sslr.api.Grammar;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_OR_INTERFACE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXTENDES_OR_IMPLEMENTS;
import org.junit.Test;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarClassOrInterfaceDeclarationTest {

    private final Grammar grammarBuilder = ApexGrammar.create(Boolean.FALSE);

    @Test
    public void positiveRulesExtends() {
        assertThat(grammarBuilder.rule(EXTENDES_OR_IMPLEMENTS))
                .matches("extendsYourClass")
                .matches("extendsYourClass");
    }

    @Test
    public void negativeRulesExtends() {
        assertThat(grammarBuilder.rule(EXTENDES_OR_IMPLEMENTS))
                .notMatches("_extendsYourClass")
                .notMatches(" Extends _YourClass ");
    }

    @Test
    public void positiveRulesImplements() {
        assertThat(grammarBuilder.rule(EXTENDES_OR_IMPLEMENTS))
                .matches("implementsYourClass")
                .matches("implementsYourClass");
    }

    @Test
    public void negativeRulesImplements() {
        assertThat(grammarBuilder.rule(EXTENDES_OR_IMPLEMENTS))
                .notMatches("ImplementsYourClass")
                .notMatches("_implements_YourClass");
    }

    @Test
    public void positiveRulesExtendsOrImplements() {
        assertThat(grammarBuilder.rule(EXTENDES_OR_IMPLEMENTS))
                .matches("")
                .matches("implementsYourClass")
                .matches("implementsMyClass")
                .matches("extendsYourClass")
                .matches("extendsMyClass");
    }

    @Test
    public void negativeRulesExtendsOrImplements() {
        assertThat(grammarBuilder.rule(EXTENDES_OR_IMPLEMENTS))
                .notMatches("_")
                .notMatches("Implements YourClass")
                .notMatches("_implements MyClass")
                .notMatches("extendS YourClass")
                .notMatches("extends 1MyClass");
    }

    @Test
    public void positiveRules() {
        assertThat(grammarBuilder.rule(CLASS_OR_INTERFACE_DECLARATION))
                .matches("classMyClass")
                .matches("interfaceMyClass")
                .matches("classMyClassextendsYourClass")
                .matches("classMyClassimplementsYourClass")
                .matches("interfaceMyClassextendsYourClass")
                .matches("interfaceMyClassimplementsYourClass");
    }

    @Test
    public void negativeRules() {
        assertThat(grammarBuilder.rule(CLASS_OR_INTERFACE_DECLARATION))
                .notMatches("class1MyClass")
                .notMatches("InterfaceMyClass")
                .notMatches("_classMyClassextendsYourClass")
                .notMatches("class-MyClass implements YourClass")
                .notMatches("interface1 MyClass extends YourClass")
                .notMatches("interface MyClass_implements YourClass");
    }

}
