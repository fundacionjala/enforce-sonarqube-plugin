/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.utils;

import java.util.List;

import com.sonar.sslr.api.AstNode;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIERS;

/**
 * Performs some operations on methods used by some checks for methods. 
 */
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
