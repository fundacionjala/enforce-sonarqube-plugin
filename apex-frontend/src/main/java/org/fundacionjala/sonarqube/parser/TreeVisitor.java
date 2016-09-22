package org.fundacionjala.sonarqube.parser;

import org.fundacionjala.sonarqube.treeimplementation.CompilationUnitTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.ModifiersTreeImpl;

public interface TreeVisitor {
    void visitCompilationUnit(CompilationUnitTreeImpl compilationUnitTree);

    void visitModifier(ModifiersTreeImpl modifierTrees);
}
