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
package org.fundacionjala.enforce.sonarqube.apex.utils;

import com.sonar.sslr.api.AstNode;
import java.util.List;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIERS;

public class MethodChecksUtils {

    private static final String TEST_METHOD_PATTERN = "(?i)testmethod";

    private static final String SYSTEM_PATTERN = "(?i)System";

    /**
     * Looks for an Assertion inside method given a list of name nodes.
     *
     * @param nameNodes list of Name nodes
     * @param pattern a pattern to define if it is assert only or assertEquals
     * or assertNotEquals
     * @return a boolean value true if there is an assertion, false otherwise.
     */
    public static boolean hasAssertion(List<AstNode> nameNodes, String pattern) {
        StringBuilder validcall = new StringBuilder();
        for (AstNode nameNodeChild : nameNodes) {
            if (nameNodeChild.getTokenOriginalValue().matches(SYSTEM_PATTERN)) {
                nameNodeChild.getChildren().stream().forEach((AstNode nameNodeGrandChild) -> {
                    validcall.append(nameNodeGrandChild.getTokenOriginalValue());
                });
                break;
            }
        }
        return validcall.toString().matches(pattern);
    }

    /**
     * Method that search for testMethod keyword in a method given a node.
     *
     * @param astNode parent node for method.
     * @return a boolean value, returns true if there is a testMethod keyword or
     * false otherwise.
     */
    public static boolean hasTestMethodKeyword(AstNode astNode) {
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
