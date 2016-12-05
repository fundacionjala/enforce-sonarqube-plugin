package org.fundacionjala.sonarqube.visitors;

import org.fundacionjala.sonarqube.semantic.SymbolModelBuilder;
import org.fundacionjala.sonarqube.tree.*;
import org.fundacionjala.sonarqube.treeimplementation.IdentifierTreeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopeVisitor extends BaseTreeVisitor {
    private SymbolModelBuilder symbolModel;
    private Scope currentScope;
    private Map<Tree, Scope> treeScopeMap;

    // List of block trees for which scope is created for another tree (e.g. function declaration or for statement)
    private List<BlockTree> skippedBlocks;

    public Map<Tree, Scope> getTreeScopeMap() {
        return treeScopeMap;
    }

    @Override
    public void visitCompilationUnit(CompilationUnitTree tree) {
        this.symbolModel = (SymbolModelBuilder) getContext().getSymbolModel();
        this.currentScope = null;
        this.skippedBlocks = new ArrayList<>();
        this.treeScopeMap = new HashMap<>();

        newMethodScope(tree);
        super.visitCompilationUnit(tree);
        leaveScope();
    }

    @Override
    public void visitBlock(BlockTree tree) {
        if (isScopeAlreadyCreated(tree)) {
            super.visitBlock(tree);

        } else {
            newBlockScope(tree);
            super.visitBlock(tree);
            leaveScope();
        }
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        newMethodInvocationScope(tree);
        super.visitMethodInvocation(tree);
        leaveScope();
    }

    @Override
    public void visitMethod(MethodTree tree) {
        newMethodScope(tree);

        skipBlock(tree.block());
        super.visitMethod(tree);

        leaveScope();
    }

    @Override
    public void visitClass(ClassTree tree) {
        newBlockScope(tree);

        super.visitClass(tree);

        leaveScope();
    }

    @Override
    public void visitIdentifier(IdentifierTree tree) {
        ((IdentifierTreeImpl) tree).scope(currentScope);
    }

    private void leaveScope() {
        if (currentScope != null) {
            currentScope = currentScope.outer();
        }
    }

    private void newMethodInvocationScope(Tree tree) {
        newScope(tree, true);
    }

    private void newMethodScope(Tree tree) {
        newScope(tree, false);
    }

    private void newBlockScope(Tree tree) {
        newScope(tree, true);
    }

    private void newScope(Tree tree, boolean isBlock) {
        currentScope = new Scope(currentScope, tree, isBlock);
        treeScopeMap.put(tree, currentScope);
        symbolModel.addScope(currentScope);
    }

    private void skipBlock(Tree tree) {
        if (tree.is(Tree.Kind.BLOCK)) {
            skippedBlocks.add((BlockTree) tree);
        }
    }

    private boolean isScopeAlreadyCreated(BlockTree tree) {
        return skippedBlocks.contains(tree);
    }

}
