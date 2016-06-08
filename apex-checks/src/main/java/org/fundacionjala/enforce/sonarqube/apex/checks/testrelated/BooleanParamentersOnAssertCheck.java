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
import org.sonar.sslr.ast.AstSelect;
import org.sonar.squidbridge.checks.SquidCheck;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARGUMENTS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.METHOD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.utils.MethodChecksUtils.hasAssertion;

public class BooleanParamentersOnAssertCheck extends SquidCheck<Grammar> {

    private static final String SYSTEM_ASSERT = "System.assert";

    @Override
    public void init() {
        subscribeTo(METHOD_DECLARATION);
    }

    @Override
    public void visitNode(AstNode astNode) {
        List<AstNode> statements = astNode.getDescendants(STATEMENT);
        for (AstNode statement : statements) {
            boolean hasAssertion = hasAssertion(statement.getDescendants(NAME), SYSTEM_ASSERT);
            if (hasAssertion && !hasBooleanVariable(statement)) {

            }
        }
    }

    boolean hasBooleanVariable(AstNode statement) {
        AstSelect arguments = statement.select().descendants(ARGUMENTS_LIST);
       
        for (AstNode currentNode : arguments) {
            if (currentNode.getTokenOriginalValue().matches("")) {
                
            }
        }
        return false;
    }

}
