/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import java.util.LinkedList;
import java.util.List;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONSTRUCTOR_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STRING_LITERAL_STRING;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

@Rule(
        key = HardcodingIdsInMethodsAndConstructorsCheck.CHECK_KEY,
        priority = Priority.MAJOR,
        name = "ID's should not be hardcoded",
        tags = Tags.CONVENTION
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("1min")
@ActivatedByDefault
public class HardcodingIdsInMethodsAndConstructorsCheck extends SquidCheck<Grammar> {

    /**
     * Stores a message template.
     */
    public static final String MESSAGE = "Line %d contains hard-coded value %s";

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1011";

    @Override
    public void init() {
        subscribeTo(CONSTRUCTOR_DECLARATION, METHOD_DECLARATION);
    }

    @Override
    public void visitNode(AstNode astNode) {
        List<AstNode> hardCodedLines = lookFor(astNode);
        if (!hardCodedLines.isEmpty()) {
            hardCodedLines.stream().forEach((hardCodedNode) -> {
                int hardCodedLineIndex = hardCodedNode.getTokenLine();
                String hardCodedValue = hardCodedNode.getTokenOriginalValue();
                getContext().createLineViolation(this, String.format(MESSAGE,
                        hardCodedLineIndex, hardCodedValue), hardCodedLineIndex);
            });
        }
    }

    /**
     * Look for an string literal string node and then it compares this token
     * with a regex to proof it contains a hard-coded ID.
     * @param astNode initial node.
     * @return a list of nodes which contains hard-coded values.
     */
    private List<AstNode> lookFor(AstNode astNode) {
        List<AstNode> hardCodedNodes = new LinkedList<>();
        List<AstNode> foundNodes = astNode.getDescendants(STRING_LITERAL_STRING);
        foundNodes.stream().filter((expressionNode) 
                -> (expressionNode.getTokenOriginalValue()
                        .matches("(')[a-zA-Z0-9]{15,18}(')"))).forEach((expressionNode) 
                        -> { 
                            hardCodedNodes.add(expressionNode);
                            }
        );
        
        return hardCodedNodes;
    }

}
