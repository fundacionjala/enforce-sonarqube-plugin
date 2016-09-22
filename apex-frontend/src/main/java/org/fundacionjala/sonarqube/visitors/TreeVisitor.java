package org.fundacionjala.sonarqube.visitors;

import org.fundacionjala.sonarqube.tree.ClassTree;
import org.fundacionjala.sonarqube.tree.CompilationUnitTree;
import org.fundacionjala.sonarqube.tree.MethodTree;

public interface TreeVisitor {

    void visitCompilationUnit(CompilationUnitTree tree);

}
