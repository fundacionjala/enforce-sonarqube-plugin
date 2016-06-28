/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksBundle;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FORMAL_PARAMETER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_DECLARATION;
import org.fundacionjala.enforce.sonarqube.apex.checks.ChecksLogger;
import static org.fundacionjala.enforce.sonarqube.apex.utils.MethodChecksUtils.hasTestMethodKeyword;

@Rule(key = TestMethodsParametersCheck.CHECK_KEY)
public class TestMethodsParametersCheck extends SquidCheck<Grammar> {

    public static final String CHECK_KEY = "A1024";

    @Override
    public void init() {
        subscribeTo(METHOD_DECLARATION);
    }

    @Override
    public void visitNode(AstNode astNode) {
        try {
            if (hasTestMethodKeyword(astNode.getParent())
                    && !astNode.getDescendants(FORMAL_PARAMETER).isEmpty()) {
                getContext().createLineViolation(this,
                        ChecksBundle.getStringFromBundle("TestMethodParametersError"),
                        astNode, astNode.getTokenLine());

            }
        } catch (Exception e) {
            ChecksLogger.logCheckError(this.toString(), "visitNode", e.toString());
        }
    }
}
