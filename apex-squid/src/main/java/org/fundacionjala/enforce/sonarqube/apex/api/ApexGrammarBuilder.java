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
import org.sonar.sslr.internal.vm.lexerful.TokenTypeExpression;
import org.sonar.sslr.internal.vm.lexerful.TokenValueExpression;

/**
 * Utility class for create a {@link GrammarBuilder} implementation.
 */
public class ApexGrammarBuilder {

    /**
     * Error message when failing parser.
     */
    private static final String PARSING_ERROR_MESSAGE = "Incorrect type of parsing expression: %s";

    /**
     * Error message when rule is null.
     */
    private static final String RULE_ERROR_MESSAGE = "Rules can't be null";

    /**
     * Represents a map of the rules and expression.
     */
    private final Map<GrammarRuleKey, ParsingExpression> mapRules;

    /**
     * Represents a state of grammar required. true -> LexerfulGrammarBuilder. false ->
     * LexerlessGrammarBuilder.
     */
    private final boolean fulGrammar;

    /**
     * Represents a current rule.
     */
    private GrammarRuleKey currentRule;

    /**
     * Represents a root rule.
     */
    private GrammarRuleKey rootRule;

    /**
     * Static method when built a ApexGrammar.
     *
     * @param fulGrammar represents the type of grammar builder required.
     *
     * @return a ApexGramamrBuilder.
     */
    public static ApexGrammarBuilder create(boolean fulGrammar) {
        return new ApexGrammarBuilder(fulGrammar);
    }

    /**
     * Default constructor that initialize variables.
     *
     * @param fulGrammar represents the type of grammar builder required.
     */
    private ApexGrammarBuilder(boolean fulGrammar) {
        this.fulGrammar = fulGrammar;
        mapRules = new HashMap<>();
    }

    /**
     * Allows to specify that given rule should be root for grammar.
     *
     * @param rootRule rule to be set.
     */
    public void setRootRule(GrammarRuleKey rootRule) {
        this.rootRule = rootRule;
    }

    /**
     * Allows to describe rule. Result of this method should be used only for execution of methods
     * in it, i.e. you should not save reference on it.
     *
     * @param ruleKey role to be set.
     *
     * @return an ApexGrammarBuilder instance.
     *
     * @throws IllegalArgumentException when ruleKey is null.
     */
    public ApexGrammarBuilder rule(GrammarRuleKey ruleKey) {
        validateRule(ruleKey);
        currentRule = ruleKey;
        return this;
    }

    /**
     * Creates and stores and parsing expression "single".
     *
     * @param object expression.
     *
     * @return an ApexGrammarBuilder instance.
     *
     * @throws IllegalArgumentException when currentRule is null.
     */
    public ApexGrammarBuilder is(Object object) {
        return addExpression(convertToExpression(object));
    }

    /**
     * Creates and stores and parsing expression "sequence".
     *
     * @param object first expression.
     * @param rest rest of expressions.
     *
     * @return an ApexGrammarBuilder instance.
     *
     * @throws IllegalArgumentException when currentRule is null.
     */
    public ApexGrammarBuilder is(Object object, Object... rest) {
        return addExpression(new SequenceExpression(convertToExpressions(Lists.asList(object, rest))));
    }

    /**
     * Creates parsing expression "first of".
     *
     * @param first first expression.
     * @param second second expression.
     *
     * @return an Expression.
     */
    public ParsingExpression firstOf(Object first, Object second) {
        return new FirstOfExpression(convertToExpression(first), convertToExpression(second));
    }

    /**
     * Creates parsing expression "first of" by a sequence.
     *
     * @param first first expression.
     * @param second second expression.
     * @param rest rest of expressions.
     *
     * @return an Expression
     */
    public ParsingExpression firstOf(Object first, Object second, Object... rest) {
        return new FirstOfExpression(convertToExpressions(Lists.asList(first, second, rest)));
    }

    /**
     * Creates parsing expression "optional".
     *
     * @param object expression.
     *
     * @return an Expression.
     */
    public ParsingExpression optional(Object object) {
        return new OptionalExpression(convertToExpression(object));
    }

    /**
     * Returns a grammar instance, built on a specific grammar builder.
     *
     * @return a Grammar
     */
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

    /**
     * Gets a map rules. Only available for unit test.
     *
     * @return a map rules.
     */
    Map<GrammarRuleKey, ParsingExpression> getMapRules() {
        return mapRules;
    }

    /**
     * Gets a current rule. Only available for unit test.
     *
     * @return a rule.
     */
    GrammarRuleKey getCurrentRule() {
        return currentRule;
    }

    /**
     * Verify that a rule is not null.
     *
     * @param ruleKey grammar rule
     *
     * @throws IllegalArgumentException when rule is null.
     */
    private void validateRule(GrammarRuleKey ruleKey) {
        if (ruleKey == null) {
            throw new IllegalArgumentException(RULE_ERROR_MESSAGE);
        }
    }

    /**
     * Stores a rule and expression.
     *
     * @param expression parsing expression.
     *
     * @return an ApexGrammarBuilder instance.
     *
     * @throws IllegalArgumentException when currentRule is null.
     */
    private ApexGrammarBuilder addExpression(ParsingExpression expression) {
        validateRule(currentRule);
        mapRules.put(currentRule, expression);
        currentRule = null;
        return this;
    }

    /**
     * Convert an object in parser expression.
     *
     * @param expression object.
     *
     * @return an expression.
     *
     * @throws IllegalArgumentException when it can't do.
     */
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

    /**
     * Convert a list of the object in parser expression.
     *
     * @param expression list of the objects.
     *
     * @return an expression.
     *
     * @throws IllegalArgumentException when it can't do.
     */
    private ParsingExpression[] convertToExpressions(List<Object> expressions) {
        ParsingExpression[] result = new ParsingExpression[expressions.size()];
        for (int i = 0; i < expressions.size(); i++) {
            result[i] = convertToExpression(expressions.get(i));
        }
        return result;
    }
}
