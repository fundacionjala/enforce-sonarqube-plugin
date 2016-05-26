/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks.unofficial;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.Tags;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

import java.util.regex.Pattern;

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
