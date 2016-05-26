/*
 * The MIT License
 *
 * Copyright 2016 Fundacion Jala.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
