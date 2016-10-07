package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.*;
import org.sonar.sslr.grammar.GrammarRuleKey;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.List;

public class MethodTreeImpl extends ApexTree implements MethodTree{

    private ModifiersTree modifiers;
    private TypeTree returnType;
    private InternalSyntaxToken simpleName;
    private final SyntaxToken openParenToken;
    private final FormalParametersListTreeImpl parameters;
    private final SyntaxToken closeParenToken;
    private final BlockTree block;

    public MethodTreeImpl(
            TypeTree returnType,
            InternalSyntaxToken simpleName,
            FormalParametersListTreeImpl parameters,
            BlockTree block) {

        super(Kind.METHOD);
        this.returnType = returnType;
        this.modifiers = null;
        this.simpleName = Preconditions.checkNotNull(simpleName);
        this.parameters = Preconditions.checkNotNull(parameters);
        this.openParenToken = parameters.openParenToken();
        this.closeParenToken = parameters.closeParenToken();
        this.block = block;
    }

    public MethodTreeImpl completeWithModifiers(ModifiersTreeImpl modifiers) {
        Preconditions.checkState(this.modifiers == null);
        this.modifiers = modifiers;

        return this;
    }

    @Override
    public Kind kind() {
        return returnType == null ? Kind.CONSTRUCTOR : Kind.METHOD;
    }

    @Override
    public ModifiersTree modifiers() {
        return modifiers;
    }

    @Nullable
    @Override
    public TypeTree returnType() {
        return returnType;
    }

    @Override
    public InternalSyntaxToken simpleName() {
        return simpleName;
    }

    @Override
    public SyntaxToken openParenToken() {
        return openParenToken;
    }

    @Override
    public List<VariableTree> parameters() {
        return (List) parameters;
    }

    @Override
    public SyntaxToken closeParenToken() {
        return closeParenToken;
    }


    @Nullable
    @Override
    public BlockTree block() {
        return block;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitMethod(this);
    }

    @Override
    public int getLine() {
        return parameters.openParenToken().getLine();
    }

    @Override
    public Iterable<Tree> children() {
        ImmutableList.Builder<Tree> iteratorBuilder = ImmutableList.builder();
        if (returnType != null) {
            iteratorBuilder.add(returnType);
        }
        iteratorBuilder.add(simpleName, openParenToken);
        iteratorBuilder.addAll(parameters.iterator());
        iteratorBuilder.add(closeParenToken);
        iteratorBuilder.add(block);
        return iteratorBuilder.build();
    }

}
