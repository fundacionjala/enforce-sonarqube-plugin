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
                .notMatches("myVariable_[]='5'")
                .notMatches("1myVariable=a")
                .notMatches("1myVariable[]=56");
    }
    
    @Test
    public void positiveRulesAssingInitializerNull() {
        assertThat(grammarBuilder.rule(VARIABLE_DECLARATOR))
                .matches("myVariable=null")
                .matches("myVariable[]=null")
                .matches("my_Variable[]=null")
                .notMatches("myVariable_[]='null")
                .notMatches("1myVariable=null")
                .notMatches("1myVariable[]=null");
    }
    
    @Test
    public void positiveRulesAssingInitializerThis() {
        assertThat(grammarBuilder.rule(VARIABLE_DECLARATOR))
                .matches("myVariable=this")
                .matches("myVariable[]=this")
                .matches("my_Variable[]=this")
                .notMatches("myVariable_[]='this")
                .notMatches("1myVariable=this")
                .notMatches("1myVariable[]=this");
    }
    
    @Test
    public void positiveRulesAssingInitializerSuper() {
        assertThat(grammarBuilder.rule(VARIABLE_DECLARATOR))
                .matches("myVariable=super")
                .matches("myVariable[]=super")
                .matches("my_Variable[]=super")
                .notMatches("myVariable_[]='super")
                .notMatches("1myVariable=super")
                .notMatches("1myVariable[]=super");
    }
    
    @Test
    public void positiveRulesAssingInitializerNumericExpresion() {
        assertThat(grammarBuilder.rule(VARIABLE_DECLARATOR))
                .matches("myVariable=2+2")
                .matches("myVariable[]=10-3")
                .matches("my_Variable[]=4*3")
                .matches("my_Variable[]=10/5")
                .matches("my_Variable[]=10%2")
                .notMatches("myVariable_[]=10+3")
                .notMatches("1myVariable=4*8")
                .notMatches("1myVariable[]=3-9");
    }
    
    @Test
    public void positiveRulesOperationsSimpleMinus() {
        assertThat(grammarBuilder.rule(VARIABLE_DECLARATOR))
                .matches("myVariable=2>2")
                .matches("myVariable[]=10>3")
                .matches("my_Variable[]=4*3")
                .matches("my_Variable[]=10/5")
                .matches("my_Variable[]=10%2")
                .notMatches("myVariable_[]=10+3")
                .notMatches("1myVariable=4*8")
                .notMatches("1myVariable[]=3-9");
    }
    
    @Test
    public void positiveRulesStringExpression() {
        assertThat(grammarBuilder.rule(VARIABLE_DECLARATOR))
                .matches("myVariable=MyName")
                .matches("myVariable[]=MYVARIBLE")
                .notMatches("myVariable_[]=10+3")
                .notMatches("1myVariable=4*8")
                .notMatches("1myVariable[]=3-9");
    }
}
