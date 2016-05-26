/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_INITIALIZER;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import java.util.List;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

@Rule(
        key = HardcodingIdsCheckInVariables.CHECK_KEY,
        priority = Priority.MAJOR,
        name = "ID's should not be hardcoded",
        tags = Tags.CONVENTION
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("1min")
@ActivatedByDefault
public class HardcodingIdsCheckInVariables extends SquidCheck<Grammar> {

    /**
     * Stores a message template.
     */
    public static final String MESSAGE = "The \"%s\" variable has hard-coded lines";

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1010";

    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.VARIABLE_DECLARATOR);
    }

    @Override
    public void visitNode(AstNode astNode) {
        AstNode astParent = astNode.getParent();
        List<AstNode> typeChildren = astParent.getChildren(TYPE);
        typeChildren.stream()
                .filter((currentNode) -> 
                        (currentNode .getTokenOriginalValue()
                                .matches("ID|id|Id|iD")))
                .forEach((_item) -> {
            checkValue(astNode.getChildren(VARIABLE_INITIALIZER));
            _item.getName();
                });
    }
    
    /**
     * Checks if one of the given nodes values from the list matches the RegEx
     * for ID's.
     * @param astNodes the list of nodes to be analyzed.
     */
    private void checkValue(List<AstNode> astNodes) {
        astNodes.stream().forEach((current) -> {
            String astNodeValue = current.getTokenOriginalValue();
            if (astNodeValue.matches("(')[a-zA-Z0-9]{15,18}(')")) {
                AstNode fieldDeclaration = current.getParent()
                        .getFirstDescendant(ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER);
                getContext().createLineViolation(this, String.format(MESSAGE,
                        fieldDeclaration.getTokenOriginalValue()), fieldDeclaration);
            }
        });
    }

}
