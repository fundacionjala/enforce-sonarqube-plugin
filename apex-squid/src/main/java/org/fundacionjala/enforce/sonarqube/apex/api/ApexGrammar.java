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
package org.fundacionjala.enforce.sonarqube.apex.api;

import com.sonar.sslr.api.Grammar;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.rbuilders.DeclarationsRulesBuilder;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.rbuilders.ExpressionRulesBuilder;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.rbuilders.KeywordRulesBuilder;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.rbuilders.StatementRulesBuilder;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.rbuilders.TypeRuleBuilder;
import static com.sonar.sslr.api.GenericTokenType.EOF;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.APEX_GRAMMAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE_DECLARATION;

/**
 * This class unites all the rules you need a class.
 */
public class ApexGrammar {

    /**
     * Default constructor.
     */
    private ApexGrammar() {
    }

    /**
     * It is the main method of grammar. Here all other grammars are
     * constructed.
     *
     * @return the grammar
     */
    public static Grammar create() {
        LexerfulGrammarBuilder grammarBuilder = LexerfulGrammarBuilder.create();

        ExpressionRulesBuilder.create(grammarBuilder);
        
        TypeRuleBuilder.create(grammarBuilder);

        KeywordRulesBuilder.create(grammarBuilder);
        
        StatementRulesBuilder.create(grammarBuilder);
        
        DeclarationsRulesBuilder.create(grammarBuilder);
        
        grammarBuilder.rule(APEX_GRAMMAR).is(TYPE_DECLARATION, EOF);
        grammarBuilder.setRootRule(APEX_GRAMMAR);
        return grammarBuilder.build();
    }
}
