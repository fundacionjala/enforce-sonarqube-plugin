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
package org.fundacionjala.enforce.sonarqube.apex.checks;

import java.util.regex.Pattern;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;

/**
 * Verification of the name of the method in a class.
 */
@Rule(
        key = MethodNameCheck.CHECK_KEY,
        priority = Priority.MINOR,
        name = "Method names should comply with a naming convention",
        tags = Tags.CONVENTION
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("1min")
@ActivatedByDefault
public class MethodNameCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1002";

    /**
     * The structure must have the name of the method.
     */
    private static final String DEFAULT = "^[a-z][a-zA-Z0-9]+$";

    @RuleProperty(
            key = "format",
            defaultValue = "" + DEFAULT)

    /**
     * Stores the format for the rule.
     */
    public String format = DEFAULT;

    /**
     * Manages the patron of rule.
     */
    private Pattern pattern = null;

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        pattern = Pattern.compile(format);
        subscribeTo(ApexGrammarRuleKey.METHOD_DECLARATION);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        String methodName = astNode.getFirstDescendant(ApexGrammarRuleKey.METHOD_IDENTIFIER).getTokenOriginalValue();
        if (!pattern.matcher(methodName).matches()) {
            getContext().createLineViolation(this,
                    "Rename method \"{0}\" to match the regular expression {1}.", astNode, methodName, format);
        }
    }
}
