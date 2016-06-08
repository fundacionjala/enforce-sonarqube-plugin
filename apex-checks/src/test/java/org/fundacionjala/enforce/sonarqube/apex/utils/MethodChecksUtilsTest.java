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
package org.fundacionjala.enforce.sonarqube.apex.utils;

import com.sonar.sslr.api.AstNode;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIERS;
import static org.fundacionjala.enforce.sonarqube.apex.checks.TestAssertionsAndTestMethodKeywordCheck.SYSTEM_ASSERT_PATTERN;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

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
