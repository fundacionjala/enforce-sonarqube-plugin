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
package org.fundacionjala.enforce.sonarqube.apex.api.grammar.head;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_OR_INTERFACE_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXTENDES_OR_IMPLEMENTS;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.EXTENDS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.IMPLEMENTS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TYPE_CLASS;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

/**
 * The class creates the rule for the head of the class have: TypeClass ,
 * Identifier, extends/implements identifier. Example: class MiClass extends
 * YourClass or interface _MiInterface extends Your_Class.
 */
public class ApexGrammarClassOrInterfaceDeclaration {

    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
        b.rule(EXTENDES_OR_IMPLEMENTS).is(b.optional(
                ApexGrammarImplementsList.createGrammarBuilder().build().rule(IMPLEMENTS_LIST),
                ApexGrammarExtendsList.createGrammarBuilder().build().rule(EXTENDS_LIST)));
        b.rule(CLASS_OR_INTERFACE_DECLARATION).is(
                ApexGrammarTypeClass.createGrammarBuilder().build().rule(TYPE_CLASS),
                ApexGrammarIdentifier.createGrammarBuilder().build().rule(IDENTIFIER),
                EXTENDES_OR_IMPLEMENTS);

        return b;
    }
}
