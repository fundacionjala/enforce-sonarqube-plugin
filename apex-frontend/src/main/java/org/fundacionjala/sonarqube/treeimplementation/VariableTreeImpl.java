package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.*;
import org.sonar.sslr.grammar.GrammarRuleKey;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.Collections;

public class VariableTreeImpl extends ApexTree implements VariableTree {

    private ModifiersTree modifiers;
    private TypeTree type;
    private InternalSyntaxToken simpleName;
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
        this.simpleName = simpleName();
        this.initializer = null;

    }

    public VariableTreeImpl(InternalSyntaxToken equalToken, ExpressionTree initializer) {
        super(Kind.VARIABLE);
        this.equalToken = equalToken;
        this.initializer = initializer;
    }

    public VariableTreeImpl(Kind kind, ModifiersTree modifiers, InternalSyntaxToken simpleName, @Nullable ExpressionTree initializer) {
        super(kind);
        this.modifiers = Preconditions.checkNotNull(modifiers);
        this.simpleName = Preconditions.checkNotNull(simpleName);
        this.initializer = initializer;
    }

    public VariableTreeImpl completeType(TypeTree type) {
        TypeTree actualType = type;
        this.type = actualType;
        return this;
    }

    public VariableTreeImpl completeModifiers(ModifiersTreeImpl modifiers) {
        this.modifiers = modifiers;

        return this;
    }

    public VariableTreeImpl completeModifiersAndType(ModifiersTreeImpl modifiers, TypeTree type) {
        return completeModifiers(modifiers).
                completeType(type);
    }

    public VariableTreeImpl completeTypeAndInitializer(TypeTree type, InternalSyntaxToken equalToken, ExpressionTree initializer) {
        this.initializer = initializer;
        this.equalToken = equalToken;

        return completeType(type);
    }


    public boolean isVararg() {
        return vararg;
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
    public InternalSyntaxToken simpleName() {
        return simpleName;
    }

    @Nullable
    @Override
    public ExpressionTree initializer() {
        return initializer;
    }

    @CheckForNull
    public SyntaxToken equalToken() {
        return equalToken;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitVariable(this);
    }

    @Override
    public int getLine() {
        return simpleName().getLine();
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
