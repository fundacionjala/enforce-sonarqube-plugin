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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.api.TokenType;
import org.sonar.sslr.grammar.GrammarRuleBuilder;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.internal.vm.ParsingExpression;
import org.sonar.sslr.internal.vm.PatternExpression;
import org.sonar.sslr.internal.vm.StringExpression;

/**
 * Utility class to create a {@link GrammarBuilder} implementation.
 */
public class ApexGrammarBuilder {

    /**
     * Stores an identifier pattern.
     */
    private static final String IDENTIFIER_PATTERN = "(_{0,2}[a-zA-Z][a-zA-Z0-9]*)+";

    /**
     * Stores an numeric pattern.
     */
    private static final String NUMERIC_PATTERN = "([1-9]\\d*)";

    /**
     * Stores an CHARACTER pattern.
     */
    private static final String CHARACTER_PATTERN = "([A-Za-z])";

    /**
     * Stores a 'setRootRule' method name.
     */
    private static final String SET_ROOT_RULE = "setRootRule";

    /**
     * Stores a 'zeroOrMore' method name.
     */
    private static final String ZERO_OR_MORE = "zeroOrMore";

    /**
     * Stores a 'fisrtOf' method name.
     */
    private static final String FIRST_OF = "firstOf";

    /**
     * Stores a 'optional' method name.
     */
    private static final String OPTIONAL = "optional";

    /**
     * Stores a 'build' method name.
     */
    private static final String BUILD = "build";

    /**
     * Stores a 'rule' method name.
     */
    private static final String RULE = "rule";

    /**
     * Defines the current grammar type which is being used.
     */
    private final boolean isFulGrammar;

    /**
     * Stores the class type of the grammar builder.
     */
    private final Class<?> grammarBuilder;

    /**
     * Stores the grammar builder.
     */
    private final Object baseBuilder;

    /**
     * Stores the grammar rule builder.
     */
    private GrammarRuleBuilder ruleBuilder;

    /**
     * Creates an apex grammar builder.
     *
     * @param isFulGrammar represents the type of grammar builder required.
     * @return an ApexGramamrBuilder.
     */
    public static ApexGrammarBuilder create(boolean isFulGrammar) {
        return new ApexGrammarBuilder(isFulGrammar);
    }

    /**
     * Default constructor that initializes variables.
     *
     * @param isFulGrammar represents the type of grammar builder required.
     */
    private ApexGrammarBuilder(boolean isFulGrammar) {
        baseBuilder = isFulGrammar ? LexerfulGrammarBuilder.create() : LexerlessGrammarBuilder.create();
        grammarBuilder = baseBuilder.getClass();
        this.isFulGrammar = isFulGrammar;
    }

    /**
     * Allows to specify that given rule should be root for grammar.
     *
     * @param rootRule rule to be set.
     */
    public void setRootRule(GrammarRuleKey rootRule) {
        invoke(SET_ROOT_RULE,
                checkTypeArguments(GrammarRuleKey.class),
                checkArguments(rootRule));
    }

    /**
     * Allows to describe rule. The result of this method should be used only
     * for execution of methods in it, i.e. you should not save reference on it.
     *
     * @param ruleKey role to be set.
     * @return an ApexGrammarBuilder instance.
     */
    public ApexGrammarBuilder rule(GrammarRuleKey ruleKey) {
        Object result = invoke(RULE,
                checkTypeArguments(GrammarRuleKey.class),
                checkArguments(ruleKey));
        if (result != null) {
            ruleBuilder = (GrammarRuleBuilder) result;
        }
        return this;
    }

    /**
     * Creates, stores and parsers expression "single".
     *
     * @param object expression.
     * @return an ApexGrammarBuilder instance.
     */
    public ApexGrammarBuilder is(Object object) {
        ruleBuilder.is(checkArgument(object));
        return this;
    }

    /**
     * Creates, stores and parsers expression "sequence".
     *
     * @param object first expression.
     * @param rest rest of expressions.
     * @return an ApexGrammarBuilder instance.
     */
    public ApexGrammarBuilder is(Object object, Object... rest) {
        ruleBuilder.is(checkArgument(object), checkArguments(rest));
        return this;
    }

    /**
     * Creates a parsing expression "first of".
     *
     * @param first is the first expression.
     * @param second is the second expression.
     * @return an Expression.
     */
    public Object firstOf(Object first, Object second) {
        return invoke(FIRST_OF,
                checkTypeArguments(Object.class, Object.class),
                checkArguments(first, second));
    }

    /**
     * Creates a parsing expression "first of" by a sequence.
     *
     * @param first first expression.
     * @param second second expression.
     * @param rest rest of expressions.
     * @return an Expression
     */
    public Object firstOf(Object first, Object second, Object... rest) {
        return invoke(FIRST_OF,
                checkTypeArguments(Object.class, Object.class, Object[].class),
                checkArguments(first, second, rest));
    }

    /**
     * Creates a parsing expression "optional".
     *
     * @param object expression.
     * @return an Expression.
     */
    public Object optional(Object object) {
        return invoke(OPTIONAL,
                checkTypeArguments(Object.class),
                checkArguments(object));
    }

    /**
     * Creates a parsing expression "optional".
     *
     * @param object expression.
     * @param rest rest of expressions.
     * @return an Expression.
     */
    public Object optional(Object object, Object... rest) {
        return invoke(OPTIONAL,
                checkTypeArguments(Object.class),
                checkArguments(object));
    }

    /**
     * Creates a parsing expression "zeroOrMore".
     *
     * @param object expression.
     * @return an Expression.
     */
    public Object zeroOrMore(Object object) {
        return invoke(ZERO_OR_MORE,
                checkTypeArguments(Object.class),
                checkArguments(object));
    }

    /**
     * Creates a parsing expression "zeroOrMore".
     *
     * @param object expression.
     * @param rest rest of expressions.
     * @return an Expression.
     */
    public Object zeroOrMore(Object object, Object... rest) {
        return invoke(ZERO_OR_MORE,
                checkTypeArguments(Object.class),
                checkArguments(object));
    }

    /**
     * Builds and returns a grammar instance, built on a specific grammar
     * builder.
     *
     * @return a Grammar
     */
    public Grammar build() {
        Grammar result = null;
        Object exec = invoke(BUILD,
                checkTypeArguments(),
                checkArguments());
        if (exec != null) {
            result = (Grammar) exec;
        }
        return result;
    }

    /**
     * Gets the grammar rule builder.
     *
     * @return the rule builder.
     */
    GrammarRuleBuilder getRuleBuilder() {
        return ruleBuilder;
    }

    private Object invoke(String name, Class<?>[] typeArguments, Object[] arguments) {
        Object result = null;
        try {
            Method method = grammarBuilder.getMethod(name, typeArguments);
            method.setAccessible(Boolean.TRUE);
            result = method.invoke(baseBuilder, arguments);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ApexGrammarBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Returns the array with the class type of the arguments.
     *
     * @param types array of the class type.
     * @return an array.
     */
    private Class<?>[] checkTypeArguments(Class<?>... types) {
        return types;
    }

    /**
     * Analyzes and returns the array of the arguments.
     *
     * @param arguments array of the arguments.
     * @return an array
     */
    private Object[] checkArguments(Object... arguments) {
        Object argument;
        if (!isFulGrammar) {
            for (int index = 0; index < arguments.length; index++) {
                argument = arguments[index];
                arguments[index] = isArray(argument) ? checkArguments((Object[]) argument)
                        : checkArgument(argument);
            }
        }
        return arguments;
    }

    /**
     * Analyzes and returns the argument. Replaces {@link TokenType} by
     * {@link ParsingExpression} if lessGrammarBuilder is required.
     *
     * @param argument to be analyzed.
     * @return the argument.
     */
    private Object checkArgument(Object argument) {
        if (!isFulGrammar) {
            if (argument instanceof TokenType) {
                TokenType token = (TokenType) argument;
                if (isIdentifier(token)) {
                    argument = new PatternExpression(IDENTIFIER_PATTERN);
                } else if (isNumeric(token)) {
                    argument = new PatternExpression(NUMERIC_PATTERN);
                } else if (isCharacter(token)) {
                    argument = new PatternExpression(CHARACTER_PATTERN);
                } else {
                    argument = new StringExpression(token.getValue());
                }
            }
        }
        return argument;
    }

    /**
     * Determines if a {@link TokenType} represents an identifier.
     *
     * @param token to be analyzed.
     * @return a boolean.
     */
    private boolean isIdentifier(TokenType token) {
        return token.equals(GenericTokenType.IDENTIFIER);
    }

    /**
     * Determines if a {@link TokenType} represents an numeric.
     *
     * @param token to be analyzed.
     * @return a boolean.
     */
    private boolean isNumeric(TokenType token) {
        return token.equals(ApexTokenType.NUMERIC);
    }

    /**
     * Determines if a {@link TokenType} represents an character.
     *
     * @param token to be analyzed.
     * @return a boolean.
     */
    private boolean isCharacter(TokenType token) {
        return token.equals(ApexTokenType.CHARACTER);
    }

    /**
     * Determines if a object represents an array class.
     *
     * @param object to be analyzed.
     * @return a boolean.
     */
    private boolean isArray(Object object) {
        return object.getClass().isArray();
    }
}
