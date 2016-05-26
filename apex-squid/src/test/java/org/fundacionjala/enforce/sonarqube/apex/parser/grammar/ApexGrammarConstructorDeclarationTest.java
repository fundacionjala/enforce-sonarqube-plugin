/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.CONSTRUCTOR_DECLARATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarConstructorDeclarationTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(CONSTRUCTOR_DECLARATION);
    }

    @Test
    public void testValidConstructorsDeclarations() {
        assertThat(parser)
                .matches("something () {"
                        + "this(blockstatement);"
                        + "}")
                .matches("something (integer something) {"
                        + "super(blockstatement);"
                        + "}")
                .matches("myClass () {"
                        + "}")
                .matches("myClass (other otherClass) { "
                        + "super();"
                        + "}")
                .matches("myConstructor (integer parameter){}")
                .matches("transient (integer parameter) {super(parameter);}")
                .matches("after (MYClass classParameter) {"
                        + "this();"
                        + "}")
                .matches("data (ClassType parameter) {}")
                .matches("group (integer parameter) {super(parameter);}");

    }

    @Test
    public void testInvalidConstructorDeclarations() {
        assertThat(parser)
                .notMatches("myClass)( {"
                        + "this(blockstatement);"
                        + "}")
                .notMatches("public classDeclaration(int parameter) {"
                        + "super(blockstatement);"
                        + "}")
                .notMatches("myClass() {"
                        + "{")
                .notMatches("myClass(lowercaselasstype otherClass) { "
                        + "super()"
                        + "}");

    }

}
