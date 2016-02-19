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

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.VARIABLE_DECLARATOR;

public class ApexGrammarVariableDeclaratorTest {

    private final Grammar grammarBuilder = ApexGrammar.create(Boolean.FALSE);

    @Test
    public void positiveRules() {
        assertThat(grammarBuilder.rule(VARIABLE_DECLARATOR))
                .matches("myVariable")
                .matches("myVariable[]")
                .matches("my_Variable[]")
                .notMatches("myVariable_[]")
                .notMatches("1myVariable")
                .notMatches("1myVariable[]");
    }
    
    @Test
    public void positiveRulesAssingInitializerNumeric() {
        assertThat(grammarBuilder.rule(VARIABLE_DECLARATOR))
                .matches("myVariable=1")
                .matches("myVariable[]=98")
                .matches("my_Variable[]=88")
                .notMatches("myVariable_[]=5")
                .notMatches("1myVariable=4")
                .notMatches("1myVariable[]=56");
    }
    
    @Test
    public void positiveRulesAssingInitializerCharacter() {
        assertThat(grammarBuilder.rule(VARIABLE_DECLARATOR))
                .matches("myVariable='A'")
                .matches("myVariable[]='B'")
                .matches("my_Variable[]='z'")
                .notMatches("my_Variable[]=a")
                .notMatches("myVariable_[]='5'")
                .notMatches("1myVariable=a")
                .notMatches("1myVariable[]=56");
    }
    
    @Test
    public void positiveRulesAssingInitializerString() {
        assertThat(grammarBuilder.rule(VARIABLE_DECLARATOR))
                .matches("myVariable=\"MiName\"")
                .matches("myVariable[]=\"BASE\"")
                .matches("my_Variable[]=\"zero\"")
                .notMatches("my_Variable[]=a")
                .notMatches("myVariable_[]='5'")
                .notMatches("1myVariable=a")
                .notMatches("1myVariable[]=56");
    }
}
