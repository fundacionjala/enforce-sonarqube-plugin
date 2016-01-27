/*
 * The MIT License
 *
 * Copyright 2016 Jalasoft.
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

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.APEX_GRAMMAR;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_OR_INTERDACE_BODY_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.END_CLASS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_DECLARATION;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.body.ApexGrammarClassOrInterfaceBodyDeclaration;
import org.fundacionjala.enforce.sonarqube.apex.api.grammar.head.ApexGrammarTypeDeclaration;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

/**
 * This class unites all the rules you need a class.
 */
public class ApexGrammar {

    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

        b.rule(END_CLASS).is("}");
        b.rule(APEX_GRAMMAR).is(
                ApexGrammarTypeDeclaration.createGrammarBuilder().build().rule(TYPE_DECLARATION),
                ApexGrammarClassOrInterfaceBodyDeclaration.createGrammarBuilder()
                .build().rule(CLASS_OR_INTERDACE_BODY_DECLARATION),
                END_CLASS
        );
        return b;
    }
}
