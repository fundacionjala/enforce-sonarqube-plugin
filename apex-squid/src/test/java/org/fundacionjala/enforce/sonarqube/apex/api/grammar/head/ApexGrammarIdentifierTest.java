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

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.BODY_IDENTIFIER;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.INIT_IDENTIFIER;
import org.junit.Test;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.IDENTIFIER;

public class ApexGrammarIdentifierTest {

    private final LexerlessGrammarBuilder b = ApexGrammarIdentifier.createGrammarBuilder();

    @Test
    public void buildIdentifierInitIdentifier() {
        assertThat(b.build().rule(INIT_IDENTIFIER))
                .matches("M")
                .matches("A")
                .matches("Z")
                .matches("N")
                .notMatches("Ñ");
    }

    @Test
    public void buildIdentifierWithOneCharacterUnderscore() {
        assertThat(b.build().rule(INIT_IDENTIFIER))
                .matches("_")
                .notMatches("__");
    }

    @Test
    public void buildBodyIdentifier() {
        assertThat(b.build().rule(BODY_IDENTIFIER))
                .matches("A")
                .matches("_")
                .matches("a")
                .matches("0")
                .matches("9")
                .matches("MyClass")
                .matches("Mi1Class")
                .matches("")
                .notMatches("miClass>")
                .notMatches("My Class");
    }

    @Test
    public void buidIdentifierCorrectCases() {
        assertThat(b.build().rule(IDENTIFIER))
                .matches("A")
                .matches("_")
                .matches("a")
                .matches("MyClass")
                .matches("Mi1Class")
                .matches("_MyClass")
                .matches("MyClass_");
    }

    @Test
    public void buidIdentifierIncorrectCases() {
        assertThat(b.build().rule(IDENTIFIER))
                .notMatches("1A")
                .notMatches("2_")
                .notMatches("a-")
                .notMatches("My-Class")
                .notMatches("3Mi1Class")
                .notMatches("MyClass_?");
    }

}
