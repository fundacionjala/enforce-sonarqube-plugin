package org.fundacionjala.sonarqube.treeimplementation;

import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.api.ApexPunctuator;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.ApexTree;
import org.fundacionjala.sonarqube.tree.ClassOrInterfaceTypeTree;
import org.fundacionjala.sonarqube.tree.SyntaxToken;
import org.fundacionjala.sonarqube.tree.Tree;

import javax.annotation.Nullable;

public class ClassOrInterfaceTypeTreeImpl extends ApexTree implements ClassOrInterfaceTypeTree {
    private SyntaxToken keywordAsIdentifier;
    private ApexPunctuator dot;
    private SyntaxToken methodCallingFromKeyword;

    public ClassOrInterfaceTypeTreeImpl(InternalSyntaxToken keywordAsIdentifier, ApexPunctuator dot,
                                        InternalSyntaxToken methodCallingFromKeyword) {
        super(Kind.CLASS_OR_INTERFACE_TYPE);
        this.keywordAsIdentifier = keywordAsIdentifier;
        this.dot = dot;
        this.methodCallingFromKeyword = methodCallingFromKeyword;
    }

    @Override
    public SyntaxToken keywordsAsIdentifier() {
        return keywordAsIdentifier;
    }

    @Override
    public ApexPunctuator dot() {
        return dot;
    }

    @Override
    public SyntaxToken methodCallingFromKeyword() {
        return methodCallingFromKeyword;
    }

    @Override
    public void accept(TreeVisitor visitor) {

    }

    @Nullable
    @Override
    public Tree parent() {
        return null;
    }

    @Override
    protected Iterable<Tree> children() {
        return null;
    }

    @Nullable
    @Override
    public SyntaxToken firstToken() {
        return null;
    }

    @Nullable
    @Override
    public SyntaxToken lastToken() {
        return null;
    }

    @Override
    public Kind kind() {
        return Kind.CLASS_OR_INTERFACE_TYPE;
    }
}
