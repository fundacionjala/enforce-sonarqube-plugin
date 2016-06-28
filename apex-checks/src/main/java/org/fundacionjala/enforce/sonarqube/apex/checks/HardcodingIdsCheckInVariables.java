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

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_INITIALIZER;

@Rule(key = HardcodingIdsCheckInVariables.CHECK_KEY)
public class HardcodingIdsCheckInVariables extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1010";

    /**
     * Regular expression of the pattern that matches an apex id.
     */
    public static final String ID_PATTERN = "(')[a-zA-Z0-9]{15,18}(')";

    /**
     * Stores a message template.
     */
    private final String MESSAGE = ChecksBundle.getStringFromBundle("IdsInVariablesCheckMessage");

    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.VARIABLE_DECLARATOR);
    }

    @Override
    public void visitNode(AstNode astNode) {
        try {
        AstNode astParent = astNode.getParent();
        List<AstNode> typeChildren = astParent.getChildren(TYPE);
        typeChildren.stream()
                .filter((currentNode)
                        -> (currentNode.getTokenOriginalValue()
                        .matches("ID|id|Id|iD")))
                .forEach((_item) -> {
                    checkValue(astNode.getChildren(VARIABLE_INITIALIZER));
                    _item.getName();
                });
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }

    /**
     * Checks if one of the given nodes values from the list matches the RegEx
     * for ID's.
     *
     * @param astNodes the list of nodes to be analyzed.
     */
    private void checkValue(List<AstNode> astNodes) {
        astNodes.stream().forEach((current) -> {
            String astNodeValue = current.getTokenOriginalValue();
            if (astNodeValue.matches(ID_PATTERN)) {
                AstNode fieldDeclaration = current.getParent()
                        .getFirstDescendant(ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER);
                getContext().createLineViolation(this, String.format(MESSAGE,
                        fieldDeclaration.getTokenOriginalValue()), fieldDeclaration);
            }
        });
    }

}
