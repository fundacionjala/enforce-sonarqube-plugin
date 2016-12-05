package org.fundacionjala.sonarqube.tree;

import java.util.List;

public interface TypeDeclarationTree extends Tree {

    List<Modifier> modifiers();

    List<ClassOrInterfaceDeclarationTree> classOrInterfaceDeclaration();

    List<TypeTree> enumDeclaration();

}
