package org.fundacionjala.sonarqube.visitors;

import com.google.common.base.Preconditions;
import org.fundacionjala.sonarqube.tree.*;

import javax.annotation.Nullable;
import java.util.Iterator;

public abstract class BaseTreeVisitor implements TreeVisitor {

    private TreeVisitorContext context = null;

    @Override
    public TreeVisitorContext getContext() {
        Preconditions.checkState(context != null);
        return context;
    }

    @Override
    public final void scanTree(TreeVisitorContext context) {
        this.context = context;
        scan(context.getTopTree());
    }


    @Override
    public void visitCompilationUnit(CompilationUnitTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitTypeParameters(Tree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitEmptyStatement(EmptyStatementTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitBlock(BlockTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitParenthesized(ParenthesizedTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitTypeArguments(Tree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitIdentifier(IdentifierTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatementTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitVariable(VariableTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitSimpleType(TypeTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitIfStatement(IfStatementTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitMethod(MethodTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitConditionalExpression(ConditionalExpressionTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitBinaryExpression(BinaryExpressionTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitModifier(ModifiersTree tree) {
        scanChildren(tree);
    }

    @Override
    public void visitClass(ClassTree tree) {
        scanChildren(tree);
    }

    protected void scan(@Nullable Tree tree) {
        if (tree != null) {
            tree.accept(this);
        }
    }

    protected void scanChildren(Tree tree) {
        Iterator<Tree> childrenIterator = ((ApexTree) tree).getChildren().iterator();

        Tree child;

        while (childrenIterator.hasNext()) {
            child = childrenIterator.next();
            if (child != null) {
                child.accept(this);
            }
        }
    }

    public void visitComment(SyntaxTrivia commentToken) {
        // no sub-tree
    }

    public void visitToken(SyntaxToken token) {
        for (SyntaxTrivia syntaxTrivia : token.trivias()) {
            syntaxTrivia.accept(this);
        }
    }
}
