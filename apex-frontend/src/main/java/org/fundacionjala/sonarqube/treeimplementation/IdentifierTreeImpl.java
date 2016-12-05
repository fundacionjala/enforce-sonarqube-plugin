package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.semantic.Symbol;
import org.fundacionjala.sonarqube.tree.*;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;
import org.fundacionjala.sonarqube.visitors.Scope;

import javax.annotation.Nullable;
import java.util.Collections;

public class IdentifierTreeImpl extends ApexTree implements IdentifierTree {
    private final InternalSyntaxToken nameToken;
    private Symbol symbol = null;
    private Scope scope;

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
    public String simpleName() {
        return identifierToken().text();
    }

    @Nullable
    @Override
    public Symbol symbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public void accept(BaseTreeVisitor visitor) {
        visitor.visitIdentifier(this);
    }

    @Override
    public String toString() {
        return simpleName();
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(Collections.singletonList(nameToken));
    }

    @Override
    public TypeTree symbolType() {
        return null;
    }

    public void scope(Scope scope) {
        this.scope = scope;
    }

    public Scope getScope() {
        return scope;
    }

    @Override
    public String name() {
        return null;
    }
}
