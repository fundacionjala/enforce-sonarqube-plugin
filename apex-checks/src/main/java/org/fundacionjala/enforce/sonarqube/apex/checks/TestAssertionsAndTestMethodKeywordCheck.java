/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import java.util.List;

import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.*;
import static org.fundacionjala.enforce.sonarqube.apex.utils.MethodChecksUtils.hasAssertion;
import static org.fundacionjala.enforce.sonarqube.apex.utils.MethodChecksUtils.hasTestMethodKeyword;

@Rule(key = TestAssertionsAndTestMethodKeywordCheck.CHECK_KEY)
public class TestAssertionsAndTestMethodKeywordCheck extends SquidCheck<Grammar> {

    public static final String SYSTEM_ASSERT_PATTERN = "(?i)system\\.assert(notequals|equals)?";

    public static final String CHECK_KEY = "A1012";

    @Override
    public void init() {
        subscribeTo(METHOD_DECLARATION);
    }

    @Override
    public void visitNode(AstNode astNode) {
        boolean hasTestMethodKeyword = hasTestMethodKeyword(astNode.getParent());
        List<AstNode> nameNodes = astNode.getDescendants(NAME);
        boolean hasAssertion = hasAssertion(nameNodes, SYSTEM_ASSERT_PATTERN);
        if (hasTestMethodKeyword && !hasAssertion) {
            getContext().createLineViolation(this,
                    String.format(ChecksBundle.getStringFromBundle("AssertionError"),
                            astNode.getFirstDescendant(METHOD_IDENTIFIER).getTokenOriginalValue()), astNode);
        }
        if (!hasTestMethodKeyword && hasAssertion) {
            getContext().createLineViolation(this,
                    String.format(ChecksBundle.getStringFromBundle("TestMethodKeywordError"),
                            astNode.getFirstDescendant(METHOD_IDENTIFIER).getTokenOriginalValue()), astNode);
        }
    }
}
