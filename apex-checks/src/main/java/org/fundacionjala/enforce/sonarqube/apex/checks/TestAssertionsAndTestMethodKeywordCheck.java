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

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIERS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NAME;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import java.util.List;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

@Rule(
        key = TestAssertionsAndTestMethodKeywordCheck.CHECK_KEY,
        priority = Priority.MAJOR,
        name = "Test methods should have assertions and testMethod keyword.",
        tags = Tags.CONVENTION
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.UNIT_TESTS)
@SqaleConstantRemediation("3min")
@ActivatedByDefault
public class TestAssertionsAndTestMethodKeywordCheck extends SquidCheck<Grammar> {

    private static final String ASSERTION_ERROR = "Test method at line %d should have at least one assertion";

    private static final String TEST_METHOD_KEYWORD_ERROR = "Test method in line %d must have testMethod keyword";

    private static final String SYSTEM_ASSERT_PATTERN = "(?i)system\\.assert(notequals|equals)?";

    private static final String TEST_METHOD_PATTERN = "(?i)testmethod";

    private static final String SYSTEM_PATTERN = "(?i)System";

    public static final String CHECK_KEY = "A1012";

    @Override
    public void init() {
        subscribeTo(METHOD_DECLARATION);
    }

    @Override
    public void visitNode(AstNode astNode) {
        boolean hasTestMethodKeyword = hasTestMethodKeyword(astNode.getParent());
        List<AstNode> nameNodes = astNode.getDescendants(NAME);
        boolean hasAssertion = hasAssertion(nameNodes);
        if (hasTestMethodKeyword && !hasAssertion) {
            getContext().createLineViolation(this,
                    String.format(ASSERTION_ERROR, astNode.getTokenLine()), astNode);
        }
        if (!hasTestMethodKeyword && hasAssertion) {
            getContext().createLineViolation(this,
                    String.format(TEST_METHOD_KEYWORD_ERROR, astNode.getTokenLine()), astNode);
        }
    }

    /**
     * Looks for an Assertion inside method given a list of name nodes.
     *
     * @param nameNodes list of Name nodes
     * @return a boolean value true if there is an assertion, false otherwise.
     */
    boolean hasAssertion(List<AstNode> nameNodes) {
        StringBuilder validcall = new StringBuilder();
        for (AstNode nameNodeChild : nameNodes) {
            if (nameNodeChild.getTokenOriginalValue().matches(SYSTEM_PATTERN)) {
                nameNodeChild.getChildren().stream().forEach((AstNode nameNodeGrandChild) -> {
                    validcall.append(nameNodeGrandChild.getTokenOriginalValue());
                });
                break;
            }
        }
        return validcall.toString().matches(SYSTEM_ASSERT_PATTERN);
    }

    /**
     * Method that search for testMethod keyword in a method given a node.
     *
     * @param astNode parent node for method.
     * @return a boolean value, returns true if there is a testMethod keyword or
     * false otherwise.
     */
    boolean hasTestMethodKeyword(AstNode astNode) {
        boolean hasAnnotation = false;
        List<AstNode> modifiersChildren = astNode.getChildren(MODIFIERS);
        for (AstNode modifier : modifiersChildren) {
            for (AstNode modifierChild : modifier.getChildren()) {
                if ((modifierChild.getTokenOriginalValue().matches(TEST_METHOD_PATTERN))) {
                    hasAnnotation = true;
                    break;
                }
            }
        }
        return hasAnnotation;
    }
}
