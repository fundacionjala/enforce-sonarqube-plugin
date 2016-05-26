/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser;

import com.google.common.base.Charsets;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.sonar.sslr.grammar.GrammarRuleKey;

public class ApexRuleTest {

    protected Parser<Grammar> parser = ApexParser.create(new ApexConfiguration(Charsets.UTF_8));

    protected void setRootRule(GrammarRuleKey ruleKey) {
        parser.setRootRule(parser.getGrammar().rule(ruleKey));
    }
}
