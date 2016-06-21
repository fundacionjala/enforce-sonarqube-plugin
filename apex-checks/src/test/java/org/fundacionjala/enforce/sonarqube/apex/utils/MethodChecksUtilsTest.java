/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.utils;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.sonar.sslr.api.AstNode;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIERS;
import static org.fundacionjala.enforce.sonarqube.apex.checks.TestAssertionsAndTestMethodKeywordCheck.SYSTEM_ASSERT_PATTERN;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MethodChecksUtilsTest {

    @Test
    public void testHasAssertionEmptyListInput() {
        assertFalse(MethodChecksUtils.hasAssertion(new LinkedList<>(), SYSTEM_ASSERT_PATTERN));
    }

    @Test
    public void testHasAssertionNodeNoSystemNameButEmptyChildrenList() {
        List<AstNode> nameNodes = new LinkedList<>();
        AstNode systemNodeMock = mock(AstNode.class);
        when(systemNodeMock.getTokenOriginalValue()).thenReturn("insert");
        nameNodes.add(systemNodeMock);
        assertFalse(MethodChecksUtils.hasAssertion(nameNodes, SYSTEM_ASSERT_PATTERN));
    }

    @Test
    public void testHasAssertionNodeWhithSystemNameButEmptyChildrenList() {
        List<AstNode> nameNodes = new LinkedList<>();
        AstNode systemNodeMock = mock(AstNode.class);
        when(systemNodeMock.getTokenOriginalValue()).thenReturn("System");
        when(systemNodeMock.getChildren()).thenReturn(new LinkedList<>());
        nameNodes.add(systemNodeMock);
        assertFalse(MethodChecksUtils.hasAssertion(nameNodes, SYSTEM_ASSERT_PATTERN));
    }

    @Test
    public void testHasAssertionValidCase() {
        List<AstNode> nameNodes = new LinkedList<>();
        List<AstNode> children = new LinkedList<>();

        AstNode systemNodeMock = mock(AstNode.class);
        when(systemNodeMock.getTokenOriginalValue()).thenReturn("System");
        nameNodes.add(systemNodeMock);

        AstNode dotNodeMock = mock(AstNode.class);
        when(dotNodeMock.getTokenOriginalValue()).thenReturn(".");

        AstNode assertEqualsNodeMock = mock(AstNode.class);
        when(assertEqualsNodeMock.getTokenOriginalValue()).thenReturn("assertEquals");

        children.add(systemNodeMock);
        children.add(dotNodeMock);
        children.add(assertEqualsNodeMock);
        when(systemNodeMock.getChildren()).thenReturn(children);
        assertTrue(MethodChecksUtils.hasAssertion(nameNodes, SYSTEM_ASSERT_PATTERN));
    }

    @Test
    public void testHasTestMethodKeywordEmptyModifiersChildrenList() {
        AstNode parentNode = mock(AstNode.class);
        when(parentNode.getChildren(MODIFIERS)).thenReturn(new LinkedList<>());
        assertFalse(MethodChecksUtils.hasTestMethodKeyword(parentNode));
    }

    @Test
    public void testHasTestMethodKeywordEmptyChildrenListOfModifiers() {
        AstNode parentNode = mock(AstNode.class);
        List<AstNode> modifiers = new LinkedList<>();
        AstNode modifier = mock(AstNode.class);
        when(modifier.getChildren()).thenReturn(new LinkedList<>());
        modifiers.add(modifier);
        when(parentNode.getChildren()).thenReturn(modifiers);
        assertFalse(MethodChecksUtils.hasTestMethodKeyword(parentNode));
    }

    @Test
    public void testHasTestMethodKeywordNotMatchesTestMethodPattern() {
        AstNode astNode = mock(AstNode.class);
        AstNode modifier = mock(AstNode.class);
        AstNode notTestMethodNode = mock(AstNode.class);

        List<AstNode> modifiers = new LinkedList<>();
        List<AstNode> modifierChildren = new LinkedList<>();

        when(notTestMethodNode.getTokenOriginalValue()).thenReturn("somethingElse");
        modifierChildren.add(notTestMethodNode);
        when(modifier.getChildren()).thenReturn(modifierChildren);
        modifiers.add(modifier);

        when(astNode.getChildren(MODIFIERS)).thenReturn(modifiers);
        assertFalse(MethodChecksUtils.hasTestMethodKeyword(astNode));
    }

    @Test
    public void testHasTestMethodKeywordMatchesTestMethodPattern() {
        AstNode astNode = mock(AstNode.class);
        AstNode modifier = mock(AstNode.class);
        AstNode notTestMethodNode = mock(AstNode.class);

        List<AstNode> modifiers = new LinkedList<>();
        List<AstNode> modifierChildren = new LinkedList<>();

        when(notTestMethodNode.getTokenOriginalValue()).thenReturn("testMethod");
        modifierChildren.add(notTestMethodNode);
        when(modifier.getChildren()).thenReturn(modifierChildren);
        modifiers.add(modifier);

        when(astNode.getChildren(MODIFIERS)).thenReturn(modifiers);
        assertTrue(MethodChecksUtils.hasTestMethodKeyword(astNode));
    }

}
