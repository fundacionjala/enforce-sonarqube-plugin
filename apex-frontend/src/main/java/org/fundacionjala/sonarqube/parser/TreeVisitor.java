package org.fundacionjala.sonarqube.parser;

import org.fundacionjala.sonarqube.treeimplementation.*;

public interface TreeVisitor {
    void visitCompilationUnit(CompilationUnitTreeImpl compilationUnitTree);

    void visitModifier(ModifiersTreeImpl modifierTrees);

    void visitClass(ClassTreeImpl classTree);

    void visitTypeParameters(TypeParameterListTreeImpl typeParameterTrees);

    void visitEmptyStatement(EmptyStatementTreeImpl emptyStatementTree);

    void visitBlock(BlockTreeImpl blockTree);

    void visitParenthesized(ParenthesizedTreeImpl parenthesizedTree);

    void visitTypeArguments(TypeArgumentListTreeImpl trees);

    void visitIdentifier(IdentifierTreeImpl identifierTree);

    void visitMethodInvocation(MethodInvocationTreeImpl methodInvocationTree);

    void visitMemberSelectExpression(MemberSelectExpressionTreeImpl memberSelectExpressionTree);

    void visitExpressionStatement(ExpressionStatementTreeImpl expressionStatementTree);

    void visitVariable(VariableTreeImpl variableTree);

    void visitSimpleType(SimpleTypeTreeImpl simpleTypeTree);

    void visitIfStatement(IfStatementTreeImpl ifStatementTree);

    void visitMethod(MethodTreeImpl methodTree);

    void visitConditionalExpression(ConditionalExpressionTreeImpl conditionalExpressionTree);

    void visitBinaryExpression(BinaryExpressionTreeImpl binaryExpressionTree);



    /*void visitClassOrInterfaceType(ClassOrInterfaceTypeTreeImpl classOrInterfaceTypeTree);*/
}
