package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.ApexTree;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.tree.Type;
import org.fundacionjala.sonarqube.tree.TypeTree;

import java.util.Collections;

public class SimpleTypeTreeImpl extends ApexTree implements TypeTree{

    InternalSyntaxToken identifier;

    public SimpleTypeTreeImpl(InternalSyntaxToken identifier) {
        super(Kind.TYPE);
        this.identifier = identifier;
    }

    public InternalSyntaxToken getIdentifier() {
        return identifier;
    }

    @Override
    protected Iterable<Tree> children() {
        return Iterables.concat(Collections.singletonList(identifier));
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitSimpleType(this);
    }

    @Override
    public Kind kind() {
        return Kind.TYPE;
    }

    @Override
    public Type symbolType() {
        return null;
    }
}
