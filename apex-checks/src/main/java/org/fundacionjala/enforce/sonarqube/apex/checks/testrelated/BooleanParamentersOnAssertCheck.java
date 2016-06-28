/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import java.util.LinkedList;
import java.util.List;

import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARGUMENTS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FIELD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LOCAL_VARIABLE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATOR;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;
import static org.fundacionjala.enforce.sonarqube.apex.utils.MethodChecksUtils.hasAssertion;

/**
 * Verifies that there is a boolean variable in an assert method.
 */
@Rule(key = BooleanParamentersOnAssertCheck.CHECK_KEY)
public class BooleanParamentersOnAssertCheck extends SquidCheck<Grammar> {

    /**
     * This is the code identifier for this check, used by sonarqube.
     */
    public static final String CHECK_KEY = "A1015";

    private static final String SYSTEM_ASSERT = "System.assert";

    /**
     * This check is subscribed to a method declaration grammar rule.
     */
    @Override
    public void init() {
        subscribeTo(METHOD_DECLARATION);
    }

    /**
     * Verifies if the given node met the rule, in case it does not it creates
     * the error
     *
     * @param astNode the node to be analyzed.
     */
    @Override
    public void visitNode(AstNode astNode) {
        try {
            List<AstNode> statements = astNode.getDescendants(STATEMENT);
            for (AstNode statement : statements) {
                boolean hasAssertion = hasAssertion(statement.getDescendants(NAME), SYSTEM_ASSERT);
                if (hasAssertion && !hasBooleanVariable(astNode)) {
                    getContext().createLineViolation(this,
                            ChecksBundle.getStringFromBundle("AssertBooleanVariableMessage"),
                            astNode, astNode.getFirstChild(METHOD_IDENTIFIER).getTokenOriginalValue());
                }
            }
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }

    /**
     * Looks for a boolean value in the same class.
     *
     * @param methodDeclarationNode node to start the search for a boolean
     * declaration.
     * @return a boolean value, true if there is a boolean declaration in the
     * same class with the same variable name false if there isn't a boolean
     * declaration.
     */
    boolean hasBooleanVariable(AstNode methodDeclarationNode) {
        LinkedList<AstNode> arguments = new LinkedList<>();
        arguments.addAll(methodDeclarationNode.getDescendants(ARGUMENTS_LIST));
        String expectedNodeName = arguments.getFirst().getTokenOriginalValue();
        AstNode classOrInterfaceBodyNode = methodDeclarationNode
                .getParent().getParent();
        boolean hasBooleanVariableField = isBooleanVariable(expectedNodeName,
                classOrInterfaceBodyNode.getDescendants(FIELD_DECLARATION));
        boolean hasBooleanVariableLocal = isBooleanVariable(expectedNodeName,
                methodDeclarationNode.getDescendants(LOCAL_VARIABLE_DECLARATION));
        return hasBooleanVariableField || hasBooleanVariableLocal;
    }

    /**
     * Analyzes if one of the given variables is boolean or not.
     *
     * @param variableName name of the variable to find.
     * @param declarations the list of variables to analyze.
     * @return returns a boolean value if the list of variables contains a
     * variable with the same name wanted.
     */
    boolean isBooleanVariable(String variableName, List<AstNode> declarations) {
        return declarations.stream().anyMatch((field) -> (field.getFirstChild(TYPE).getTokenOriginalValue().matches("(?i)boolean")
                && variableName.equals(field.getFirstChild(VARIABLE_DECLARATOR).getTokenOriginalValue())));
    }
}
