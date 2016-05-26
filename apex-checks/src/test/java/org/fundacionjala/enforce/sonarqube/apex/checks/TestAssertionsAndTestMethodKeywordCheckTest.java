/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.MODIFIERS;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class TestAssertionsAndTestMethodKeywordCheckTest {

    private final TestAssertionsAndTestMethodKeywordCheck check;

    public TestAssertionsAndTestMethodKeywordCheckTest() {
        check = new TestAssertionsAndTestMethodKeywordCheck();
    }

    @Test
    public void testTestShouldHaveAssertions() {
        File file = new File("src/test/resources/checks/testsClassAssertions.cls");
        SourceFile sourceFile = ApexAstScanner.scanFile(file, check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next()
                .atLine(3)
                .withMessage("Test method at line 3 should have at least one assertion")
                .next()
                .atLine(54)
                .withMessage("Test method in line 54 must have testMethod keyword")
                .noMore();
    }

    @Test
    public void testHasAssertionEmptyListInput() {
        assertFalse(check.hasAssertion(new LinkedList<>()));
    }

    @Test
    public void testHasAssertionNodeNoSystemNameButEmptyChildrenList() {
        List<AstNode> nameNodes = new LinkedList<>();
        AstNode systemNodeMock = mock(AstNode.class);
        when(systemNodeMock.getTokenOriginalValue()).thenReturn("insert");
        nameNodes.add(systemNodeMock);
        assertFalse(check.hasAssertion(nameNodes));
    }

    @Test
    public void testHasAssertionNodeWhithSystemNameButEmptyChildrenList() {
        List<AstNode> nameNodes = new LinkedList<>();
        AstNode systemNodeMock = mock(AstNode.class);
        when(systemNodeMock.getTokenOriginalValue()).thenReturn("System");
        when(systemNodeMock.getChildren()).thenReturn(new LinkedList<>());
        nameNodes.add(systemNodeMock);
        assertFalse(check.hasAssertion(nameNodes));
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
        assertTrue(check.hasAssertion(nameNodes));
    }

    @Test
    public void testHasTestMethodKeywordEmptyModifiersChildrenList() {
        AstNode parentNode = mock(AstNode.class);
        when(parentNode.getChildren(MODIFIERS)).thenReturn(new LinkedList<>());
        assertFalse(check.hasTestMethodKeyword(parentNode));
    }

    @Test
    public void testHasTestMethodKeywordEmptyChildrenListOfModifiers() {
        AstNode parentNode = mock(AstNode.class);
        List<AstNode> modifiers = new LinkedList<>();
        AstNode modifier = mock(AstNode.class);
        when(modifier.getChildren()).thenReturn(new LinkedList<>());
        modifiers.add(modifier);
        when(parentNode.getChildren()).thenReturn(modifiers);
        assertFalse(check.hasTestMethodKeyword(parentNode));
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
        assertFalse(check.hasTestMethodKeyword(astNode));
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
        assertTrue(check.hasTestMethodKeyword(astNode));
    }
}
