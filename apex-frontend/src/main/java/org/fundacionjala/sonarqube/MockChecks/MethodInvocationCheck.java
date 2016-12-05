package org.fundacionjala.sonarqube.MockChecks;

import com.google.common.base.Preconditions;
import org.fundacionjala.sonarqube.apex.ApexAstScanner;
import org.fundacionjala.sonarqube.semantic.SymbolModel;
import org.fundacionjala.sonarqube.tree.IdentifierTree;
import org.fundacionjala.sonarqube.tree.MethodInvocationTree;
import org.fundacionjala.sonarqube.tree.MethodTree;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.treeimplementation.MemberSelectExpressionTreeImpl;

import java.util.Map;


//Check to test relations between classes.
public class MethodInvocationCheck extends SpecialCheck {

    private Map<String, SymbolModel> unbindedSymbols;

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (tree.methodSelect().is(Tree.Kind.IDENTIFIER)) {
            if ("assertTrue".equals(((IdentifierTree)tree.methodSelect()).simpleName()) && tree.arguments().get(0).is(Tree.Kind.METHOD_INVOCATION)) {
                IdentifierTree methodInvocationName = ((MemberSelectExpressionTreeImpl)((MethodInvocationTree)tree.arguments().get(0)).methodSelect()).identifier();
                MethodTree methodTree = (MethodTree)((IdentifierTree) tree.methodSelect()).symbol().scope().outer().tree();
                String variableType = ApexAstScanner.findMethodParameterType(methodTree, methodInvocationName.simpleName(), "");
                SymbolModel symbolModel = unbindedSymbols.get(variableType + ".cls");
                Preconditions.checkState(symbolModel.getSymbols(methodInvocationName.simpleName()).isEmpty());
                addIssue(tree, "There is a literal return from methodInvocation.");
            }
        }
        super.visitMethodInvocation(tree);
    }

    @Override
    public void setUnbindedSymbols(Map<String, SymbolModel> symbols) {
        this.unbindedSymbols = symbols;
    }
}
