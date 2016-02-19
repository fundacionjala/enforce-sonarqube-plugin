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
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.APEX_GRAMMAR;

import org.junit.Before;
import org.junit.Test;

import org.sonar.sslr.internal.grammar.MutableGrammar;
import org.sonar.sslr.internal.vm.FirstOfExpression;
import org.sonar.sslr.internal.vm.OptionalExpression;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThat;

public class ApexGrammarBuilderTest {

    private static final boolean FUL_GRAMMAR = Boolean.FALSE;
    private ApexGrammarBuilder grammarBuilder;

    @Before
    public void setup() {
        grammarBuilder = ApexGrammarBuilder.create(FUL_GRAMMAR);
    }

    @Test
    public void testVerifiesTypeOfGrammarWhenUsingFulGrammarBuilder() {
        Grammar grammar = grammarBuilder.build();
        assertThat(grammar, instanceOf(MutableGrammar.class));
    }

    @Test
    public void testVerifiesTypeOfGrammarWhenUsingLessGrammarBuilder() {
        final boolean lessGrammar = !FUL_GRAMMAR;
        grammarBuilder = ApexGrammarBuilder.create(lessGrammar);
        Grammar grammar = grammarBuilder.build();
        assertThat(grammar, instanceOf(MutableGrammar.class));
    }

    @Test
    public void testUpdateRuleBuilderWhenRuleIsAdded() {
        assertThat(grammarBuilder.getRuleBuilder(), nullValue());

        grammarBuilder.rule(APEX_GRAMMAR);
        assertThat(grammarBuilder.getRuleBuilder(), notNullValue());
    }

    @Test
    public void testVerifiesTypeOfTheOptionalExpression() {
        Object expresion = grammarBuilder.optional("option");
        assertThat(expresion, instanceOf(OptionalExpression.class));
    }

    @Test
    public void testVerifiesTypeOfTheFirstOfExpression() {
        Object expresion = grammarBuilder.firstOf("first", "second");
        assertThat(expresion, instanceOf(FirstOfExpression.class));

        expresion = grammarBuilder.firstOf("first", "second", "third", "fourth");
        assertThat(expresion, instanceOf(FirstOfExpression.class));
    }
}
