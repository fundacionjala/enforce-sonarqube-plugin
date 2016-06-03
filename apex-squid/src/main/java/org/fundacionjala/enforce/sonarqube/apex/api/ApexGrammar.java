/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.api;

import org.fundacionjala.enforce.sonarqube.apex.api.grammar.buildersource.*;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

import com.sonar.sslr.api.Grammar;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.APEX_GRAMMAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE_DECLARATION;

import static com.sonar.sslr.api.GenericTokenType.EOF;

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
     * @param errorRecoveryEnabled error recovery status
     * @return the grammar
     */
    public static Grammar create(boolean errorRecoveryEnabled) {
        LexerfulGrammarBuilder grammarBuilder = LexerfulGrammarBuilder.create();

        MostUsed.create(grammarBuilder, errorRecoveryEnabled);

        Expression.create(grammarBuilder);

        Type.create(grammarBuilder);

        Statement.create(grammarBuilder);

        Declaration.create(grammarBuilder, errorRecoveryEnabled);

        grammarBuilder.rule(APEX_GRAMMAR).is(TYPE_DECLARATION, EOF);

        grammarBuilder.setRootRule(APEX_GRAMMAR);
        return grammarBuilder.build();
    }

}
