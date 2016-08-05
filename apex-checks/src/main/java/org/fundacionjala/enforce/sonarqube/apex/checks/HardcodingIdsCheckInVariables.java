/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.ast.AstSelect;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Grammar;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CLASS_OR_INTERFACE_MEMBER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FIELD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.LOCAL_VARIABLE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.PRIMARY_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT_EXPRESSION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATOR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_INITIALIZER;

import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;

@Rule(key = HardcodingIdsCheckInVariables.CHECK_KEY)
public class HardcodingIdsCheckInVariables extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1010";

    /**
     * Regular expression of the pattern that matches an apex id.
     */
    public static final String ID_PATTERN = "(')([a-zA-Z0-9]{18}|[a-zA-Z0-9]{15})(')";

    /**
     * Stores a message template.
     */
    private final String MESSAGE = ChecksBundle.getStringFromBundle("IdsInVariablesCheckMessage");

    private Map<String, AstNode> hardcodedVariables;

    @Override
    public void init() {
        subscribeTo(METHOD_DECLARATION);
    }

    @Override
    public void visitNode(AstNode astNode) {
        hardcodedVariables = new HashMap<>();
        try {
            lookForIdVariableHardCoded(astNode);
            hardcodedVariables.keySet().stream().forEach((hardCodedVariableName) -> {
                getContext().createLineViolation(this, String.format(MESSAGE,
                        hardCodedVariableName), hardcodedVariables.get(hardCodedVariableName));
            });
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }

    /**
     * Retrieves all hard-coded values from method and then verifies if these
     * values belongs to an ID type variable.
     *
     * @param methodDeclarationNode the node where check vists syntax tree.
     */
    void lookForIdVariableHardCoded(AstNode methodDeclarationNode) {
        AstNode classOrInterfaceBodyNode = methodDeclarationNode
                .getParent().getParent();
        AstSelect hardCodedExpressions = methodDeclarationNode.select()
                .descendants(STATEMENT_EXPRESSION).descendants(EXPRESSION);
        AstSelect localVariableInitializers = methodDeclarationNode.select()
                .descendants(VARIABLE_INITIALIZER);
        AstSelect fieldVariableInitializers = classOrInterfaceBodyNode.select().children(CLASS_OR_INTERFACE_MEMBER)
                .children(FIELD_DECLARATION).children(VARIABLE_DECLARATOR).children(VARIABLE_INITIALIZER);
        registerHardcodedVariables(hardCodedExpressions, FIELD_DECLARATION, PRIMARY_EXPRESSION, classOrInterfaceBodyNode);
        registerHardcodedVariables(localVariableInitializers, LOCAL_VARIABLE_DECLARATION, IDENTIFIER, classOrInterfaceBodyNode);
        registerHardcodedVariables(fieldVariableInitializers, FIELD_DECLARATION, IDENTIFIER, classOrInterfaceBodyNode);
    }

    /**
     * Given the list of hard-coded values, analyzes them if they match with the
     * SFDC ID regular expression and if this value belongs to an ID type
     * variable if so then registers this node into a list of hard-coded
     * variables detected.
     *
     * @param variableInitializers list of hard-coded values found.
     * @param variableDeclarationType the node type of variable declarations,
     * according the situation defines what nodes to look for
     * @param variableValueNodeType the node type of variable values, according
     * the situation defines what nodes to look for
     * @param classOrInterfaceBodyNode a superior node where some nodes are
     * visited from.
     */
    void registerHardcodedVariables(AstSelect variableInitializers, AstNodeType variableDeclarationType,
            AstNodeType variableValueNodeType, AstNode classOrInterfaceBodyNode) {
        for (AstNode current : variableInitializers) {
            String variableName = current.getParent()
                    .getFirstDescendant(variableValueNodeType).getTokenOriginalValue();
            if (current.getTokenOriginalValue().matches(ID_PATTERN)
                    && isIdTypeVariable(variableName, classOrInterfaceBodyNode
                            .getDescendants(variableDeclarationType))) {
                hardcodedVariables.put(variableName, current.getParent());
            }
        }
    }

    /**
     * Verifies if the list of declarations contains a variable of id type and
     * also verifies if its variable name is equals to the variable name given.
     *
     * @param variableName a variable name to compare.
     * @param declarations a list of declarations.
     * @return a boolean value true if any declarations name from declarations
     * list is equal to the variable name given.
     */
    boolean isIdTypeVariable(String variableName, List<AstNode> declarations) {
        return declarations.stream().anyMatch((current)
                -> (current.getFirstChild(TYPE).getTokenOriginalValue().matches("(?i)id")
                && variableName.equals(current.getFirstDescendant(VARIABLE_DECLARATOR)
                        .getTokenOriginalValue())));
    }
}
