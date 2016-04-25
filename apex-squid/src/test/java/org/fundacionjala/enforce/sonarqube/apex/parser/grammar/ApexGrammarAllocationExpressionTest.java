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

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ALLOCATION_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarAllocationExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(ALLOCATION_EXPRESSION);
    }

    @Test
    public void positiveRules() {
        assertThat(parser)
                .matches("new SomeType()")
                .matches("new SomeType(p1, p2, 0, null)")
                .matches("new SomeType(p1, p2) {}")
                .matches("new SomeType[5]")
                .matches("new SomeType[5-2*4/y]")
                .matches("new SomeType[a.b().c[1]]")
                .matches("new SomeType[]{1, 2, x, y, this}")
                .matches("new list<SomeType>()")
                .matches("new iterator<set<someType>>()")
                .matches("new set<SomeType>(p1, 3, null, this)")
                .matches("new set<SomeType>{}")
                .matches("new map<K, V>()")
                .matches("new map<K, V>{}")
                .matches("new map<K, V>(1, 2 , x, y)")
                .matches("new map<K, V>{k1 => 2 , x => y}");
    }

    @Test
    public void negativeRules() {
        assertThat(parser)
                .notMatches("new")
                .notMatches("new 3")
                .notMatches("new 'aString'")
                .notMatches("new null")
                .notMatches("new SomeType");
    }
}
