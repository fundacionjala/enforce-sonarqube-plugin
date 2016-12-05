package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.tree.*;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.Collections;

public class VariableTreeImpl extends ApexTree implements VariableTree {

    private ModifiersTree modifiers;
    private InternalSyntaxToken finalToken;
    private TypeTree type;
    private IdentifierTree simpleName;
    @Nullable
    private SyntaxToken equalToken;
    @Nullable
    private ExpressionTree initializer;
    @Nullable
    private SyntaxToken endToken;

    // Syntax tree holders
    private boolean vararg = false;

    public VariableTreeImpl(IdentifierTreeImpl simpleName) {
        super(Kind.VARIABLE);
        this.modifiers = ModifiersTreeImpl.emptyModifiers();
        this.simpleName = simpleName;
        this.initializer = null;

    }

    public VariableTreeImpl completeType(TypeTree type) {
        this.type = type;
        return this;
    }

    public VariableTreeImpl completeFinalKeywordAndType(InternalSyntaxToken finalKeyword, TypeTree type) {
        return completeFinalKeyword(finalKeyword).
                completeType(type);
    }

    public VariableTreeImpl completeFinalKeyword(InternalSyntaxToken finalKeyword) {
        this.finalToken = finalKeyword;
        return this;
    }

    @Override
    public Kind kind() {
        return Kind.VARIABLE;
    }

    @Override
    public ModifiersTree modifiers() {
        return modifiers;
    }

    @Override
    public TypeTree type() {
        return type;
    }

    @Override
    public IdentifierTree simpleName() {
        return simpleName;
    }

    @Nullable
    @Override
    public ExpressionTree initializer() {
        return initializer;
    }

    @Override
    public void accept(BaseTreeVisitor visitor) {
        visitor.visitVariable(this);
    }

    @Override
    public int getLine() {
        return ((IdentifierTreeImpl)simpleName()).getLine();
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                Lists.newArrayList(modifiers, type, simpleName),
                initializer != null ? Lists.newArrayList(equalToken, initializer) : Collections.<Tree>emptyList(),
                endToken != null ? Collections.singletonList(endToken) : Collections.<Tree>emptyList()
        );
    }

    @CheckForNull
    @Override
    public SyntaxToken endToken() {
        return endToken;
    }

    public void setEndToken(InternalSyntaxToken endToken) {
        this.endToken = endToken;
    }
}
