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

import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ABSTRACT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.ANOTATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.FINAL;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.NATIVE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PRIVATE;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PROTECTED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.PUBLIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STATIC;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.STRICTFP;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.SYNCHRONIZED;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.TRANSIENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword.VOLATILE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.LOOKAHEAD;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

/**
 * The class creates all the rules for reserved word lookahead. Example: public,
 * private, final ...
 */
public class ApexGrammarLookahead {

    public static LexerlessGrammarBuilder createGrammarBuilder() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

        b.rule(PUBLIC).is("public");
        b.rule(STATIC).is("static");
        b.rule(PROTECTED).is("protected");
        b.rule(PRIVATE).is("private");
        b.rule(FINAL).is("final");
        b.rule(ABSTRACT).is("abstract");
        b.rule(SYNCHRONIZED).is("synchronized");
        b.rule(NATIVE).is("native");
        b.rule(TRANSIENT).is("transient");
        b.rule(VOLATILE).is("volatile");
        b.rule(STRICTFP).is("strictfp");
        b.rule(ANOTATION).is("anotation");

        b.rule(LOOKAHEAD).is(b.firstOf(
                PUBLIC,
                STATIC,
                PROTECTED,
                PRIVATE,
                FINAL,
                ABSTRACT,
                SYNCHRONIZED,
                NATIVE,
                TRANSIENT,
                VOLATILE,
                STRICTFP,
                ANOTATION));

        return b;
    }
}
