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
        key = MethodNameCheck.CHECK_KEY,
        priority = Priority.MAJOR,
        name = "Variables should not be hardcoded",
        tags = Tags.CONVENTION
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("1min")
@ActivatedByDefault
public class HardcodingIdsCheck extends SquidCheck<Grammar> {

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
        });
    }
    
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
