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
package org.fundacionjala.enforce.sonarqube.apex.api.grammar;

import org.sonar.sslr.grammar.GrammarRuleKey;

/**
 * Contains enum all the rules used in the grammar.
 */
public enum RuleKey implements GrammarRuleKey {

    APEX_GRAMMAR,
    BODY_IDENTIFIER,
    CHAR,
    CLASS_OR_INTERDACE_BODY_DECLARATION,
    CLASS_OR_INTERFACE_DECLARATION,
    DEFAULT_VALUE,
    END_CLASS,
    EXTENDS,
    EXTENDS_LIST,
    EXTENDES_OR_IMPLEMENTS,
    IDENTIFIER,
    IMPLEMENTS,
    IMPLEMENTS_LIST,
    INIT_IDENTIFIER,
    KEYWORD,
    LOOKAHEAD,
    LOOKAHEAD_KEYWORD,
    MERGE_TYPE_EXTENDS,
    MERGE_TYPE_IMPLEMENTS,
    METHOD_DECLARATION,
    MODIFIER,
    MODIFIERS,
    PRIMITIVE_TYPE,
    TYPE,
    TYPE_CLASS,
    TYPE_DECLARATION,
    TYPE_METHOD,
    TYPE_PATAMETERS,
    RESULT_TYPE,
    WITH_SHARING,
    WITHOUT_SHARING;
}
