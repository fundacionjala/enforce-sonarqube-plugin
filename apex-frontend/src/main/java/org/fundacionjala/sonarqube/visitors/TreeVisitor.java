package org.fundacionjala.sonarqube.visitors;

import org.fundacionjala.sonarqube.tree.*;

public interface TreeVisitor {

    TreeVisitorContext getContext();

    void scanTree(TreeVisitorContext context);


    void visitCompilationUnit(CompilationUnitTree compilationUnitTree);

    void visitModifier(ModifiersTree modifierTrees);

    void visitClass(ClassTree classTree);

    void visitTypeParameters(Tree typeParameterTrees);

    void visitEmptyStatement(EmptyStatementTree emptyStatementTree);

    void visitBlock(BlockTree blockTree);

    void visitParenthesized(ParenthesizedTree parenthesizedTree);

    void visitTypeArguments(Tree trees);

    void visitIdentifier(IdentifierTree identifierTree);

    void visitMethodInvocation(MethodInvocationTree methodInvocationTree);

    void visitMemberSelectExpression(MemberSelectExpressionTree memberSelectExpressionTree);

    void visitExpressionStatement(ExpressionStatementTree expressionStatementTree);

    void visitVariable(VariableTree variableTree);

    void visitSimpleType(TypeTree simpleTypeTree);

    void visitIfStatement(IfStatementTree ifStatementTree);

    void visitMethod(MethodTree methodTree);

    void visitConditionalExpression(ConditionalExpressionTree conditionalExpressionTree);

    void visitBinaryExpression(BinaryExpressionTree binaryExpressionTree);

    void visitComment(SyntaxTrivia internalSyntaxTrivia);
}
