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
package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import java.util.List;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey;
import org.fundacionjala.enforce.sonarqube.apex.checks.Tags;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

/**
 * Verifies that tests methods are only declared in test classes.
 */
@Rule(
        key = TestMethodInTestClassCheck.CHECK_KEY,
        priority = Priority.MAJOR,
        name = "A testMethod should not be declared or implemented in a class that isn't a test",
        description = "A method that has the \"testMethod\" modifier should only be declared or implemented"
        + "in a class defined as a test class (with the \"@isTest\" annotation)",
        tags = Tags.CONFUSING
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.MODULARITY)
@SqaleConstantRemediation("7min")
@ActivatedByDefault
public class TestMethodInTestClassCheck extends SquidCheck<Grammar> {

    /**
     * It is the code of the rule for the plugin.
     */
    public static final String CHECK_KEY = "A1022";
    
    /**
    * Node that represents the class where the test method is declared. Meant to be lazy loaded.
    */
    private AstNode classDeclaration;
    
    /**
     * The identifier node of the class. Meant to be lazy loaded.
     */
    private AstNode className;

    /**
     * The variables are initialized and subscribe the base rule.
     */
    @Override
    public void init() {
        subscribeTo(ApexGrammarRuleKey.METHOD_DECLARATION);
    }

    /**
     * It is responsible for verifying whether the rule is met in the rule base.
     * In the event that the rule is not correct, create message error.
     *
     * @param astNode It is the node that stores all the rules.
     */
    @Override
    public void visitNode(AstNode astNode) {
        if (!TestClassCheck.hasTestAnnotation(getClassDeclaration(astNode))) {
            AstNode member = astNode.getParent();
            List<AstNode> modifiers = member.getFirstChild(ApexGrammarRuleKey.MODIFIERS).getChildren();
            for (AstNode modifier : modifiers) {
                if (modifier.is(ApexKeyword.TESTMETHOD)) {
                    AstNode methodName = astNode.getFirstChild(ApexGrammarRuleKey.METHOD_IDENTIFIER)
                            .getFirstChild(ApexGrammarRuleKey.ALLOWED_KEYWORDS_AS_IDENTIFIER,
                            ApexGrammarRuleKey.SPECIAL_KEYWORDS_AS_IDENTIFIER);
                    getContext().createLineViolation(this,
                            "The method \"{0}\" is marked as a testMethod but it"
                            + " is not in a test class, move it to a proper class or add the \"@isTest\" annotation"
                            + "to the class \"{1}\"",
                            astNode, methodName.getTokenOriginalValue(), getClassName().getTokenOriginalValue());
                }
            }
        }
    }
    
    /**
     * Lazy load the classDeclaration property.
     * @param astNode The node of the method of which the class declaration is obtained. 
     * @return the classDeclaration value.
     */
    private AstNode getClassDeclaration(AstNode astNode) {
        if (classDeclaration == null) {
            classDeclaration = astNode.getFirstAncestor(ApexGrammarRuleKey.TYPE_DECLARATION)
                    .getFirstChild(ApexGrammarRuleKey.CLASS_OR_INTERFACE_DECLARATION);
        }
        return classDeclaration;
    }
    
    /**
     * Lazy load the className property.
     * @return The node identifier of the class.
     */
    private AstNode getClassName() {
        if (className == null) {
            className = classDeclaration.getFirstChild(ApexGrammarRuleKey.COMMON_IDENTIFIER);
        }
        return className;
    }
}
