/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.Tags;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

import java.util.List;

/**
 * Verifies that only the proper classes are tagged as tests.
 */
@Rule(
        key = TestClassCheck.CHECK_KEY,
        priority = Priority.MAJOR,
        name = "\"@Test\" annotation should only be used for proper test classes",
        description = "\"@Test\" annotation is used to indicate when a class represents a test class, so"
        + "it should be used only for classes (not enums or interfaces) and said class should be "
        + "either public or private",
        tags = Tags.CONFUSING
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.MODULARITY)
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class TestClassCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1021";
    
    /**
     * Identifier of the annotation.
     */
    public static final String IS_TEST = "ISTEST";
    
    /**
     * Constant used to see if the name of the class has the word test in it.
     */
    private final String TEST = "TEST";

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.CLASS_OR_INTERFACE_DECLARATION, ApexGrammarRuleKey.ENUM_DECLARATION);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        AstNode identifier = astNode.getFirstDescendant(ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER,
                ApexGrammarRuleKey.SPECIAL_KEYWORDS_AS_IDENTIFIER);
        if (hasTestAnnotation(astNode)) {
            if (astNode.is(ApexGrammarRuleKey.ENUM_DECLARATION)
                    || astNode.getFirstDescendant(ApexGrammarRuleKey.TYPE_CLASS).hasDirectChildren(ApexKeyword.INTERFACE)) {
                getContext().createLineViolation(this,
                        "The \"@isTest\" annotation should be used only for classes, remove it from the declaration of \"{0}\".",
                        astNode, identifier.getTokenOriginalValue());
            }
        } else if (identifier.getTokenValue().contains(TEST)) {
            getContext().createLineViolation(this,
                        "The name of the class \"{0}\" suggests this is a test class, either add an @isTest annotation"
                                + "or change the name of the class.",
                        astNode, identifier.getTokenOriginalValue());
        }
    }
    
    /**
     * Determines whether or not a declarations has the @isTest annotation amongst its modifiers.
     * @param astNode the declaration to be checked.
     * @return true if the @isTest annotation is amongst the modifiers.
     */
    public static boolean hasTestAnnotation(AstNode astNode) {
        AstNode modifiers = astNode.getParent().getFirstDescendant(ApexGrammarRuleKey.MODIFIERS);
        if (modifiers.hasDescendant(ApexGrammarRuleKey.ANNOTATION)) {
            List<AstNode> annotations = modifiers.getDescendants(ApexGrammarRuleKey.ANNOTATION);
            for (AstNode annotation : annotations) {
                String annotationValue = annotation.getFirstDescendant(ApexGrammarRuleKey.NAME).getTokenValue();
                if (annotationValue.equals(IS_TEST)) {
                    return true;
                }
            }
        }
        return false;
    }
}
