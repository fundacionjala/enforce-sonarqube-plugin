/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.checks.SquidCheck;

import java.util.List;

/**
 * Verifies that tests methods are only declared in test classes.
 */
@Rule(key = TestMethodInTestClassCheck.CHECK_KEY)
@ActivatedByDefault
public class TestMethodInTestClassCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1022";

    /**
     * Node that represents the class where the test method is declared. Meant
     * to be lazy loaded.
     */
    private AstNode classDeclaration;

    /**
     * The identifier node of the class. Meant to be lazy loaded.
     */
    private AstNode className;

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
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
        if (!TestClassCheck.hasTestAnnotation(getClassDeclaration(astNode))) {
            AstNode member = astNode.getParent();
            List<AstNode> modifiers = member.getFirstChild(ApexGrammarRuleKey.MODIFIERS).getChildren();
            for (AstNode modifier : modifiers) {
                if (modifier.is(ApexKeyword.TESTMETHOD)) {
                    AstNode methodName = astNode.getFirstChild(ApexGrammarRuleKey.METHOD_IDENTIFIER)
                            .getFirstChild(ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER,
                                    ApexGrammarRuleKey.SPECIAL_KEYWORDS_AS_IDENTIFIER);
                    getContext().createLineViolation(this,
                            ChecksBundle.getStringFromBundle("TestMethodsCheckMessage"),
                            astNode, methodName.getTokenOriginalValue(), getClassName().getTokenOriginalValue());
                }
            }
        }
    }

    /**
     * Lazy load the classDeclaration property.
     *
     * @param astNode The node of the method of which the class declaration is
     * obtained.
     * @return the classDeclaration value.
     */
    private AstNode getClassDeclaration(AstNode astNode) {
        if (classDeclaration == null) {
            classDeclaration = astNode.getFirstAncestor(ApexGrammarRuleKey.TYPE_DECLARATION)
                    .getFirstChild(ApexGrammarRuleKey.CLASS_OR_INTERFACE_DECLARATION);
        }
        return classDeclaration;
    }

    /**
     * Lazy load the className property.
     *
     * @return The node identifier of the class.
     */
    private AstNode getClassName() {
        if (className == null) {
            className = classDeclaration.getFirstChild(ApexGrammarRuleKey.COMMON_IDENTIFIER);
        }
        return className;
    }
}
