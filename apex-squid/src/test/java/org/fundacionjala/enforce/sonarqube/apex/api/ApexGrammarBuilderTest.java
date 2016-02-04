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

import java.util.Map;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.APEX_GRAMMAR;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.internal.vm.FirstOfExpression;
import org.sonar.sslr.internal.vm.OptionalExpression;
import org.sonar.sslr.internal.vm.ParsingExpression;
import org.sonar.sslr.internal.vm.SequenceExpression;
import org.sonar.sslr.internal.vm.StringExpression;

public class ApexGrammarBuilderTest {

    private static final boolean FUL_GRAMMAR = Boolean.FALSE;
    private Map<GrammarRuleKey, ParsingExpression> rules;
    private ApexGrammarBuilder grammarBuilder;

    @Before
    public void setup() {
        grammarBuilder = ApexGrammarBuilder.create(FUL_GRAMMAR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowingAnExceptionWhenAddedANullRule() {
        grammarBuilder.rule(null);
    }

    @Test
    public void testUpdateCurrentRuleWhenRuleIsAdded() {
        grammarBuilder.rule(APEX_GRAMMAR);
        assertThat(grammarBuilder.getCurrentRule(), is(APEX_GRAMMAR));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowingAnExceptionWhenAddedAExpressionWithoutRule() {
        grammarBuilder.is("error");
    }

    @Test
    public void testVerifiesThatStoresARule() {
        grammarBuilder.rule(APEX_GRAMMAR).is("expression");
        rules = grammarBuilder.getMapRules();
        assertThat(rules, hasKey(APEX_GRAMMAR));
    }

    @Test
    public void testVerifiesTypeOfTheSimpleExpression() {
        grammarBuilder.rule(APEX_GRAMMAR).is("expression");
        rules = grammarBuilder.getMapRules();
        ParsingExpression expresion = rules.get(APEX_GRAMMAR);
        assertThat(expresion, instanceOf(StringExpression.class));
    }

    @Test
    public void testVerifiesTypeOfTheSecuenceExpression() {
        grammarBuilder.rule(APEX_GRAMMAR).is("start", "sequence", "end");
        rules = grammarBuilder.getMapRules();
        ParsingExpression expresion = rules.get(APEX_GRAMMAR);
        assertThat(expresion, instanceOf(SequenceExpression.class));
    }

    @Test
    public void testVerifiesTypeOfTheOptionalExpression() {
        ParsingExpression expresion = grammarBuilder.optional("option");
        assertThat(expresion, instanceOf(OptionalExpression.class));
    }

    @Test
    public void testVerifiesTypeOfTheFirstOfExpression() {
        ParsingExpression expresion = grammarBuilder.firstOf("First", "second", "final");
        assertThat(expresion, instanceOf(FirstOfExpression.class));
    }
}
