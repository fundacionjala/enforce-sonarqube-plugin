/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import java.util.LinkedList;
import java.util.List;

import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.*;

@Rule(key = HardcodingIdsInMethodsAndConstructorsCheck.CHECK_KEY)
public class HardcodingIdsInMethodsAndConstructorsCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1011";

    /**
     * Stores a message template.
     */
    private final String MESSAGE = ChecksBundle.getStringFromBundle("HardcodingIdsInMethodsCheck");

    @Override
    public void init() {
        subscribeTo(CONSTRUCTOR_DECLARATION, METHOD_DECLARATION);
    }

    @Override
    public void visitNode(AstNode astNode) {
        try {
            List<AstNode> hardCodedLines = lookFor(astNode);
            if (!hardCodedLines.isEmpty()) {
                hardCodedLines.stream().forEach((hardCodedNode) -> {
                    String hardCodedValue = hardCodedNode.getTokenOriginalValue();
                    getContext().createLineViolation(this, String.format(MESSAGE,
                            hardCodedValue), hardCodedNode);
                });
            }
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }

    /**
     * Look for an string literal string node and then it compares this token
     * with a regex to proof it contains a hard-coded ID.
     *
     * @param astNode initial node.
     * @return a list of nodes which contains hard-coded values.
     */
    private List<AstNode> lookFor(AstNode astNode) {
        List<AstNode> hardCodedNodes = new LinkedList<>();
        List<AstNode> foundNodes = astNode.getDescendants(STRING_LITERAL_STRING);
        foundNodes.stream().filter((expressionNode)
                -> (expressionNode.getTokenOriginalValue()
                .matches(HardcodingIdsCheckInVariables.ID_PATTERN))).forEach((expressionNode)
                -> {
            hardCodedNodes.add(expressionNode);
        }
        );

        return hardCodedNodes;
    }

}
