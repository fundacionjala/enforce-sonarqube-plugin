package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.tree.ApexTree;
import org.fundacionjala.sonarqube.tree.CompilationUnitTree;
import org.fundacionjala.sonarqube.tree.SyntaxToken;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;

import java.util.Collections;
import java.util.List;

public class CompilationUnitTreeImpl extends ApexTree implements CompilationUnitTree{

    private final List<Tree> types;
    private final SyntaxToken eofToken;

    public CompilationUnitTreeImpl(List<Tree> types, SyntaxToken eofToken) {
        super(Kind.COMPILATION_UNIT);
        this.types = types;
        this.eofToken = eofToken;
    }

    public List<Tree> types() {
        return types;
    }

    @Override
    public Kind kind() {
        return Kind.COMPILATION_UNIT;
    }

    @Override
    public void accept(BaseTreeVisitor visitor) {
        visitor.visitCompilationUnit(this);
    }

    public Iterable<Tree> children() {
        return Iterables.concat(
                types,
                Collections.<Tree>singletonList(eofToken));
    }

    @Override
    public SyntaxToken eofToken() {
        return eofToken;
    }

}
