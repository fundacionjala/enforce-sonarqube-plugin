/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.checks.testrelated;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner;
import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;
import com.sonar.sslr.api.AstNode;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.ARGUMENTS_LIST;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FIELD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.TYPE;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.VARIABLE_DECLARATOR;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BooleanParamentersOnAssertTest {

    private BooleanParamentersOnAssertCheck check;

    @Before
    public void setUp() {
        check = new BooleanParamentersOnAssertCheck();
    }
    
    @Test
    public void testBooleanParametersOnAssert() {
        File file = new File("src/test/resources/checks/testsClassAssertions.cls");
        SourceFile sourceFile = ApexAstScanner.scanFile(file, check);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages())
                .next().atLine(105).withMessage("Method testMethodWithNoBooleanVariableOnAssert "
                + "has a wrong System.assert parameter, it should be a boolean one.")
                .next().atLine(115).withMessage("Method testMethodWithoutExpressionValueOnAssert "
                + "has a wrong System.assert parameter, it should be a boolean one.")
                .noMore();
    }

    @Test
    public void testHasBooleanVariableInFied() {
        AstNode typeNode = mock(AstNode.class);
        when(typeNode.getTokenOriginalValue()).thenReturn("boolean");
        AstNode variableDeclarator = mock(AstNode.class);
        when(variableDeclarator.getTokenOriginalValue()).thenReturn("sampleVariable");
        
        AstNode firstFieldDeclaration = mock(AstNode.class);
        when(firstFieldDeclaration.getFirstChild(eq(TYPE)))
                .thenReturn(typeNode);
        when(firstFieldDeclaration.getFirstChild(eq(VARIABLE_DECLARATOR)))
                .thenReturn(variableDeclarator);
        AstNode secondFieldDeclaration = mock(AstNode.class);
        AstNode thirdFieldDeclaration = mock(AstNode.class);
        
        List<AstNode> fieldDeclarations = new LinkedList<>();
        fieldDeclarations.add(firstFieldDeclaration);
        fieldDeclarations.add(secondFieldDeclaration);
        fieldDeclarations.add(thirdFieldDeclaration);
        
        AstNode classOrInterfaceBody = mock(AstNode.class);
        when(classOrInterfaceBody.getDescendants(eq(FIELD_DECLARATION)))
                .thenReturn(fieldDeclarations);
        
        AstNode classOrInterfaceMembernode = mock(AstNode.class);
        when(classOrInterfaceMembernode.getParent()).thenReturn(classOrInterfaceBody);
        
        AstNode methodDeclarationNode = mock(AstNode.class);
        when(methodDeclarationNode.getParent()).thenReturn(classOrInterfaceMembernode);
        
        AstNode firstArgument = mock(AstNode.class);
        when(firstArgument.getTokenOriginalValue()).thenReturn("sampleVariable");
        AstNode secondArgument = mock(AstNode.class);

        List<AstNode> argumentsList = new LinkedList<>();
        argumentsList.add(firstArgument);
        argumentsList.add(secondArgument);
        
        when(methodDeclarationNode.getDescendants(eq(ARGUMENTS_LIST)))
                .thenReturn(argumentsList);
        
        assertTrue(check.hasBooleanVariable(methodDeclarationNode));
    }
    
    @Test
    public void testIsBooleanValueEmptyDeclarationsList() {
        assertFalse(check.isBooleanVariable("", new LinkedList<>()));
    }
    
    @Test
    public void testIsBooleanValue() {
        AstNode firstDeclarationFirstChildVariableDeclarator = mock(AstNode.class);
        when(firstDeclarationFirstChildVariableDeclarator.getTokenOriginalValue())
                .thenReturn("notWantedVariable");
        AstNode secondDeclarationFirstChildVariableDeclarator = mock(AstNode.class);
        when(secondDeclarationFirstChildVariableDeclarator.getTokenOriginalValue())
                .thenReturn("wantedVariable");
        
        AstNode firstDeclarationFirstChildType = mock(AstNode.class);
        when(firstDeclarationFirstChildType.getTokenOriginalValue()).thenReturn("boolean");
        
        AstNode secondDeclarationFirstChildType = mock(AstNode.class);
        when(secondDeclarationFirstChildType.getTokenOriginalValue()).thenReturn("boolean");
        
        AstNode firstDeclaration = mock(AstNode.class);
        when(firstDeclaration.getFirstChild(eq(TYPE)))
                .thenReturn(firstDeclarationFirstChildType);
        when(firstDeclaration.getFirstChild(eq(VARIABLE_DECLARATOR)))
                .thenReturn(firstDeclarationFirstChildVariableDeclarator);
        
        AstNode secondDeclaration = mock(AstNode.class);
        when(secondDeclaration.getFirstChild(eq(TYPE)))
                .thenReturn(secondDeclarationFirstChildType);
        when(secondDeclaration.getFirstChild(eq(VARIABLE_DECLARATOR)))
                .thenReturn(secondDeclarationFirstChildVariableDeclarator);
        
        List<AstNode> declarations = new LinkedList<>();
        declarations.add(firstDeclaration);
        declarations.add(secondDeclaration);
        
        assertTrue(check.isBooleanVariable("wantedVariable", declarations));
    }
}
