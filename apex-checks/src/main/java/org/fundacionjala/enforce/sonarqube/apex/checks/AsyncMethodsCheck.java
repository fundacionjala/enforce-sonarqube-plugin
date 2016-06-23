/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import java.util.List;

/**
 * Checks that no "loop" block of code calls an async method, which have the
 * "@future" annotation.
 */
@Rule(key = AsyncMethodsCheck.CHECK_KEY)
public class AsyncMethodsCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1009";

    /**
     * Value of the annotation future, which this check looks for.
     */
    private final String FUTURE = "future";

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.WHILE_STATEMENT,
                ApexGrammarRuleKey.FOR_STATEMENT,
                ApexGrammarRuleKey.DO_STATEMENT);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        if (astNode.hasDescendant(ApexGrammarRuleKey.STATEMENT_EXPRESSION)) {
            List<AstNode> expressions = astNode.getDescendants(ApexGrammarRuleKey.STATEMENT_EXPRESSION);
            for (AstNode expression : expressions) {
                if (expression.hasDescendant(ApexGrammarRuleKey.PRIMARY_SUFFIX)
                        && expression.hasDescendant(ApexGrammarRuleKey.ARGUMENTS)) {
                    List<AstNode> arguments = expression.getDescendants(ApexGrammarRuleKey.ARGUMENTS);
                    for (AstNode argument : arguments) {
                        AstNode prefix = argument.getPreviousAstNode();
                        AstNode method = prefix.getFirstDescendant(ApexGrammarRuleKey.NAME,
                                ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER,
                                ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER_FOR_METHODS);
                        if (method != null) {
                            AstNode methodIdentifier = method.hasDescendant(ApexGrammarRuleKey.METHOD_IDENTIFIER)
                                    ? method.getLastChild(ApexGrammarRuleKey.METHOD_IDENTIFIER) : method;
                            String methodName = methodIdentifier.getTokenValue();
                            if (methodIsAsync(astNode, methodName)) {
                                String message = ChecksBundle.getStringFromBundle("AsyncMethodsCheckMessage");
                                getContext().createLineViolation(this,
                                        message, method,
                                        methodIdentifier.getTokenOriginalValue());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Determines if a method is Async by looking for the "@future" amongst it's
     * annotations.
     *
     * @param astNode the node of the loop statement.
     * @param methodName the name of the method.
     * @return True if the method has the "@future" annotation.
     */
    private boolean methodIsAsync(AstNode astNode, String methodName) {
        AstNode firstAncestor = astNode.getFirstAncestor(ApexGrammarRuleKey.TYPE_DECLARATION);
        List<AstNode> methods = firstAncestor.getDescendants(ApexGrammarRuleKey.METHOD_DECLARATION);
        if (!methods.isEmpty()) {
            for (AstNode method : methods) {
                String name = method.getFirstChild(ApexGrammarRuleKey.METHOD_IDENTIFIER).getTokenValue();
                if (name.equals(methodName)) {
                    AstNode member = method.getFirstAncestor(ApexGrammarRuleKey.CLASS_OR_INTERFACE_MEMBER);
                    AstNode modifiers = member.getFirstChild(ApexGrammarRuleKey.MODIFIERS);
                    if (modifiers.hasDescendant(ApexGrammarRuleKey.ANNOTATION)) {
                        List<AstNode> annotations = modifiers.getChildren(ApexGrammarRuleKey.ANNOTATION);
                        for (AstNode annotation : annotations) {
                            String annotationValue = annotation.getFirstChild(ApexGrammarRuleKey.NAME).getTokenValue();
                            if (annotationValue.equalsIgnoreCase(FUTURE)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
