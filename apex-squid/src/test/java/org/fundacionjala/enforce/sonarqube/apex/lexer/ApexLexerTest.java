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
package org.fundacionjala.enforce.sonarqube.apex.lexer;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.impl.Lexer;
import static com.sonar.sslr.test.lexer.LexerMatchers.hasOriginalToken;

import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType;

import static org.junit.Assert.assertThat;

import static com.sonar.sslr.test.lexer.LexerMatchers.hasToken;

public class ApexLexerTest {

    private List<Token> tokens;
    private Lexer lexer;
    private File file;

    @Before
    public void setup() {
        lexer = ApexLexer.create(new ApexConfiguration(Charsets.UTF_8));
    }

    @Test
    public void testVerifiesTokensOfTheHeadOfAClass() {
        file = new File("src/test/resources/lexer/HeadOfAClass.cls");
        tokens = lexer.lex(file);
        assertThat(tokens, hasOriginalToken("Public"));
        assertThat(tokens, hasOriginalToken("with"));
        assertThat(tokens, hasOriginalToken("SHARING"));
        assertThat(tokens, hasOriginalToken("class"));
        assertThat(tokens, hasOriginalToken("Class1"));
        assertThat(tokens, hasToken("{", ApexPunctuator.LBRACE));
        assertThat(tokens, hasToken("}", ApexPunctuator.RBRACE));
    }

    @Test
    public void testVerifiesTokensOfTheConstructorMethod() {
        file = new File("src/test/resources/lexer/ConstructorMethod.cls");
        tokens = lexer.lex(file);
        assertThat(tokens, hasOriginalToken("public"));
        assertThat(tokens, hasOriginalToken("Class1"));
        assertThat(tokens, hasToken("(", ApexPunctuator.LPAREN));
        assertThat(tokens, hasToken(")", ApexPunctuator.RPAREN));
        assertThat(tokens, hasToken("{", ApexPunctuator.LBRACE));
        assertThat(tokens, hasToken("}", ApexPunctuator.RBRACE));
    }

    @Test
    public void testVerifiesTokensOfTheMethod() {
        file = new File("src/test/resources/lexer/Method.cls");
        tokens = lexer.lex(file);
        assertThat(tokens, hasOriginalToken("public"));
        assertThat(tokens, hasOriginalToken("void"));
        assertThat(tokens, hasOriginalToken("pow"));
        assertThat(tokens, hasToken("(", ApexPunctuator.LPAREN));
        assertThat(tokens, hasOriginalToken("int"));
        assertThat(tokens, hasOriginalToken("number"));
        assertThat(tokens, hasToken(")", ApexPunctuator.RPAREN));
        assertThat(tokens, hasToken("{", ApexPunctuator.LBRACE));
        assertThat(tokens, hasOriginalToken("return"));
        assertThat(tokens, hasOriginalToken("Math"));
        assertThat(tokens, hasToken(".", ApexPunctuator.DOT));
        assertThat(tokens, hasOriginalToken("pow"));
        assertThat(tokens, hasToken("(", ApexPunctuator.LPAREN));
        assertThat(tokens, hasOriginalToken("number"));
        assertThat(tokens, hasToken(",", ApexPunctuator.COMMA));
        assertThat(tokens, hasToken("2", ApexTokenType.NUMERIC));
        assertThat(tokens, hasToken(")", ApexPunctuator.RPAREN));
        assertThat(tokens, hasToken(";", ApexPunctuator.SEMICOLON));
        assertThat(tokens, hasToken("}", ApexPunctuator.RBRACE));
    }

    @Test
    public void testVerifiesTokensWhenAVariableIsInstantiated() {
        file = new File("src/test/resources/lexer/InstanceVariable.cls");
        tokens = lexer.lex(file);
        assertThat(tokens, hasOriginalToken("Account"));
        assertThat(tokens, hasOriginalToken("a"));
        assertThat(tokens, hasToken("=", ApexPunctuator.ASSIGN));
        assertThat(tokens, hasOriginalToken("new"));
        assertThat(tokens, hasToken("(", ApexPunctuator.LPAREN));
        assertThat(tokens, hasOriginalToken("Account"));
        assertThat(tokens, hasToken(")", ApexPunctuator.RPAREN));
        assertThat(tokens, hasToken(";", ApexPunctuator.SEMICOLON));
    }

    @Test
    public void testVerifiesTokensWhenAVariableIsAssigned() {
        file = new File("src/test/resources/lexer/AssignVariable.cls");
        tokens = lexer.lex(file);
        assertThat(tokens, hasOriginalToken("a"));
        assertThat(tokens, hasToken(".", ApexPunctuator.DOT));
        assertThat(tokens, hasOriginalToken("CustomField1__c"));
        assertThat(tokens, hasToken("=", ApexPunctuator.ASSIGN));
        assertThat(tokens, hasToken("\'value\'", ApexTokenType.STRING));
        assertThat(tokens, hasToken(";", ApexPunctuator.SEMICOLON));
    }
}
