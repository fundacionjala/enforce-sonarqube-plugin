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
import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.impl.Lexer;

import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexKeyword;
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
        assertThat(tokens, hasToken("public", ApexKeyword.PUBLIC));
        assertThat(tokens, hasToken("with", ApexKeyword.WITH));
        assertThat(tokens, hasToken("sharing", ApexKeyword.SHARING));
        assertThat(tokens, hasToken("class", ApexKeyword.CLASS));
        assertThat(tokens, hasToken("Class1", GenericTokenType.IDENTIFIER));
        assertThat(tokens, hasToken("{", ApexPunctuator.LBRACE));
        assertThat(tokens, hasToken("}", ApexPunctuator.RBRACE));
    }

    @Test
    public void testVerifiesTokensOfTheConstructorMethod() {
        file = new File("src/test/resources/lexer/ConstructorMethod.cls");
        tokens = lexer.lex(file);
        assertThat(tokens, hasToken("public", ApexKeyword.PUBLIC));
        assertThat(tokens, hasToken("Class1", GenericTokenType.IDENTIFIER));
        assertThat(tokens, hasToken("(", ApexPunctuator.LPAREN));
        assertThat(tokens, hasToken(")", ApexPunctuator.RPAREN));
        assertThat(tokens, hasToken("{", ApexPunctuator.LBRACE));
        assertThat(tokens, hasToken("}", ApexPunctuator.RBRACE));
    }

    @Test
    public void testVerifiesTokensOfTheMethod() {
        file = new File("src/test/resources/lexer/Method.cls");
        tokens = lexer.lex(file);
        assertThat(tokens, hasToken("public", ApexKeyword.PUBLIC));
        assertThat(tokens, hasToken("void", ApexKeyword.VOID));
        assertThat(tokens, hasToken("pow", GenericTokenType.IDENTIFIER));
        assertThat(tokens, hasToken("(", ApexPunctuator.LPAREN));
        assertThat(tokens, hasToken("int", ApexKeyword.INT));
        assertThat(tokens, hasToken("number", GenericTokenType.IDENTIFIER));
        assertThat(tokens, hasToken(")", ApexPunctuator.RPAREN));
        assertThat(tokens, hasToken("{", ApexPunctuator.LBRACE));
        assertThat(tokens, hasToken("return", ApexKeyword.RETURN));
        assertThat(tokens, hasToken("Math", GenericTokenType.IDENTIFIER));
        assertThat(tokens, hasToken(".", ApexPunctuator.DOT));
        assertThat(tokens, hasToken("pow", GenericTokenType.IDENTIFIER));
        assertThat(tokens, hasToken("(", ApexPunctuator.LPAREN));
        assertThat(tokens, hasToken("number", GenericTokenType.IDENTIFIER));
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
        assertThat(tokens, hasToken("Account", GenericTokenType.IDENTIFIER));
        assertThat(tokens, hasToken("a", GenericTokenType.IDENTIFIER));
        assertThat(tokens, hasToken("=", ApexPunctuator.ASSIGN));
        assertThat(tokens, hasToken("new", ApexKeyword.NEW));
        assertThat(tokens, hasToken("(", ApexPunctuator.LPAREN));
        assertThat(tokens, hasToken(")", ApexPunctuator.RPAREN));
        assertThat(tokens, hasToken(";", ApexPunctuator.SEMICOLON));
    }

    @Test
    public void testVerifiesTokensWhenAVariableIsAssigned() {
        file = new File("src/test/resources/lexer/AssignVariable.cls");
        tokens = lexer.lex(file);
        assertThat(tokens, hasToken("a", GenericTokenType.IDENTIFIER));
        assertThat(tokens, hasToken(".", ApexPunctuator.DOT));
        assertThat(tokens, hasToken("CustomField1__c", GenericTokenType.IDENTIFIER));
        assertThat(tokens, hasToken("=", ApexPunctuator.ASSIGN));
        assertThat(tokens, hasToken("'value'", ApexTokenType.STRING));
        assertThat(tokens, hasToken(";", ApexPunctuator.SEMICOLON));
    }
}
