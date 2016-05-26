/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.cpd;

import com.sonar.sslr.api.Token;
import com.sonar.sslr.impl.Lexer;
import net.sourceforge.pmd.cpd.SourceCode;
import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokenizer;
import net.sourceforge.pmd.cpd.Tokens;
import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.lexer.ApexLexer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Scans the source code to build {@link TokenEntry}.
 */
public class ApexTokenizer implements Tokenizer {

    /**
     * Stores an apex configuration.
     */
    private final ApexConfiguration config;

    /**
     * Default constructor to initialize the configuration.
     *
     * @param charset type of configuration.
     */
    public ApexTokenizer(Charset charset) {
        this.config = new ApexConfiguration(charset);
    }

    /**
     * Builds and stores a {@link TokenEntry} from the source code.
     *
     * @param source source code.
     * @param cpdTokens tokens.
     * @throws IOException when it can't read a source file.
     */
    @Override
    public void tokenize(SourceCode source, Tokens cpdTokens) throws IOException {
        Lexer lexer = ApexLexer.create(config);
        String fileName = source.getFileName();
        List<Token> tokens = lexer.lex(new File(fileName));
        tokens.forEach(token -> {
            TokenEntry cpdToken = new TokenEntry(getTokenImage(token), fileName, token.getLine());
            cpdTokens.add(cpdToken);
        });
        cpdTokens.add(TokenEntry.getEOF());
    }

    /**
     * Returns the Token's value.
     *
     * @param token current token.
     * @return the token's value
     */
    private String getTokenImage(Token token) {
        return token.getValue();
    }
}
