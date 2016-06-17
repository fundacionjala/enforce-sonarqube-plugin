/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.lexer;

import com.google.common.base.Charsets;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.impl.Lexer;
import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexPunctuator;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexTokenType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static com.sonar.sslr.test.lexer.LexerMatchers.hasOriginalToken;
import static com.sonar.sslr.test.lexer.LexerMatchers.hasToken;
import static org.junit.Assert.assertThat;

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
