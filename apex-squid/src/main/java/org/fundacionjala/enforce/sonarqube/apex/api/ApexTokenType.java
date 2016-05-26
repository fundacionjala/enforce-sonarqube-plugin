/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.api;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;

/**
 * Enum save a custom type of Apex.
 */
public enum ApexTokenType implements TokenType {

    NEW_LINE,
    STRING,
    NUMERIC,
    HEXADECIMAL;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getValue() {
        return name();
    }

    @Override
    public boolean hasToBeSkippedFromAst(AstNode an) {
        return Boolean.FALSE;
    }
}
