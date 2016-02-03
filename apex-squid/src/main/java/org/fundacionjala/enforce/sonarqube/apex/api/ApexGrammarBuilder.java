/*
 * The MIT License
 *
 * Copyright 2016 Jalasoft.
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

import com.google.common.collect.Lists;
import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.api.typed.GrammarBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.internal.vm.FirstOfExpression;
import org.sonar.sslr.internal.vm.OptionalExpression;
import org.sonar.sslr.internal.vm.ParsingExpression;
import org.sonar.sslr.internal.vm.PatternExpression;
import org.sonar.sslr.internal.vm.SequenceExpression;
import org.sonar.sslr.internal.vm.StringExpression;
import org.sonar.sslr.internal.vm.lexerful.TokenTypeClassExpression;
import org.sonar.sslr.internal.vm.lexerful.TokenTypeExpression;
import org.sonar.sslr.internal.vm.lexerful.TokenValueExpression;

/**
 * Utility class for create a {@link GrammarBuilder} implementation.
 */
public class ApexGrammarBuilder {

    private static final String PARSING_ERROR_MESSAGE = "Incorrect type of parsing expression: %s";
    private Map<GrammarRuleKey, ParsingExpression> mapRules;
    private GrammarRuleKey currentRule;
    private GrammarRuleKey rootRule;
    private boolean fulGrammar;

    public static ApexGrammarBuilder create(boolean fulGrammar) {
        return new ApexGrammarBuilder(fulGrammar);
    }

    public ApexGrammarBuilder(boolean fulGrammar) {
        this.fulGrammar = fulGrammar;
        mapRules = new HashMap<>();
    }

    public void setRootRule(GrammarRuleKey rootRule) {
        this.rootRule = rootRule;
    }

    public ApexGrammarBuilder rule(GrammarRuleKey ruleKey) {
        if (ruleKey == null) {
            throw new IllegalArgumentException();
        }
        currentRule = ruleKey;
        return this;
    }

    public ApexGrammarBuilder is(Object object) {
        return addExpression(convertToExpression(object));
    }

    public ApexGrammarBuilder is(Object object, Object... rest) {
        return addExpression(new SequenceExpression(convertToExpressions(Lists.asList(object, rest))));
    }

    public ParsingExpression firstOf(Object first, Object second) {
        return new FirstOfExpression(convertToExpression(first), convertToExpression(second));
    }

    public ParsingExpression firstOf(Object first, Object second, Object... rest) {
        return new FirstOfExpression(convertToExpressions(Lists.asList(first, second, rest)));
    }

    public ParsingExpression optional(Object object) {
        return new OptionalExpression(convertToExpression(object));
    }

    public Grammar build() {
        Grammar result;
        if (fulGrammar) {
            LexerfulGrammarBuilder grammarBuilder = LexerfulGrammarBuilder.create();
            mapRules.forEach((rule, expression) -> {
                grammarBuilder.rule(rule).is(expression);
            });
            grammarBuilder.setRootRule(rootRule);
            result = grammarBuilder.build();
        } else {
            LexerlessGrammarBuilder grammarBuilder = LexerlessGrammarBuilder.create();
            mapRules.forEach((rule, expression) -> {
                grammarBuilder.rule(rule).is(expression);
            });
            grammarBuilder.setRootRule(rootRule);
            result = grammarBuilder.build();
        }
        return result;
    }

    private ApexGrammarBuilder addExpression(ParsingExpression expression) {
        mapRules.put(currentRule, expression);
        currentRule = null;
        return this;
    }

    private ParsingExpression convertToExpression(Object expression) {
        final ParsingExpression result;
        if (expression instanceof ParsingExpression) {
            result = (ParsingExpression) expression;
        } else if (expression instanceof GrammarRuleKey) {
            GrammarRuleKey ruleKey = (GrammarRuleKey) expression;
            result = mapRules.get(ruleKey);
        } else if (expression instanceof TokenType) {
            result = (fulGrammar) ? new TokenTypeExpression((TokenType) expression)
                    : new PatternExpression("[a-zA-Z]([a-zA-Z0-9_]*[a-zA-Z0-9])?+");
        } else if (expression instanceof String) {
            result = (fulGrammar) ? new TokenValueExpression((String) expression)
                    : new StringExpression((String) expression);
        } else {
            throw new IllegalArgumentException(String.format(PARSING_ERROR_MESSAGE,
                    expression.getClass().toString()));
        }
        return result;
    }

    private ParsingExpression[] convertToExpressions(List<Object> expressions) {
        ParsingExpression[] result = new ParsingExpression[expressions.size()];
        for (int i = 0; i < expressions.size(); i++) {
            result[i] = convertToExpression(expressions.get(i));
        }
        return result;
    }
}
