package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.*;

import java.util.Collections;
import java.util.List;

public class IdentifierTreeImpl extends ApexTree implements IdentifierTree{
    private final InternalSyntaxToken nameToken;

    public IdentifierTreeImpl(InternalSyntaxToken nameToken) {
        super(Kind.IDENTIFIER);
        this.nameToken = Preconditions.checkNotNull(nameToken);
    }

    @Override
    public Kind kind() {
        return Kind.IDENTIFIER;
    }

    @Override
    public SyntaxToken identifierToken() {
        return nameToken;
    }

    @Override
    public String name() {
        return identifierToken().text();
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitIdentifier(this);
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(Collections.singletonList(nameToken));
    }

    @Override
    public Type symbolType() {
        return null;
    }
}
