package org.fundacionjala.sonarqube.parser;

import org.fundacionjala.sonarqube.treeimplementation.ClassTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.CompilationUnitTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.ModifiersTreeImpl;

public class BaseTreeVisitor implements TreeVisitor{
    @Override
    public void visitCompilationUnit(CompilationUnitTreeImpl compilationUnitTree) {

    }

    @Override
    public void visitModifier(ModifiersTreeImpl modifierTrees) {

    }

    @Override
    public void visitClass(ClassTreeImpl classTree) {

    }
}
