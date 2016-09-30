package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.*;
import org.sonar.sslr.grammar.GrammarRuleKey;

import javax.annotation.Nullable;

public class MethodTreeImpl extends ApexTree implements MethodTree{


    private ModifiersTree modifiers;
    private TypeParameters typeParameters;
    @Nullable
    private InternalSyntaxToken returnType;
    private InternalSyntaxToken simpleName;
    private final SyntaxToken openParenToken;
    private final FormalParametersListTreeImpl parameters;
    private final SyntaxToken closeParenToken;
    @Nullable
    private final BlockTree block;
    @Nullable
    private SyntaxToken semicolonToken;
    @Nullable
    private final SyntaxToken throwsToken;
    /*private final ListTree<TypeTree> throwsClauses;*/
/*    private final SyntaxToken defaultToken;
    private final ExpressionTree defaultValue;*/

    public MethodTreeImpl(SyntaxToken returnType, InternalSyntaxToken simpleName,
                          FormalParametersListTreeImpl parameters,
                          BlockTree block,
                          SyntaxToken semicolon) {
        super(Kind.METHOD);
        this.typeParameters = new TypeParameterListTreeImpl();
        this.parameters = parameters;
        this.openParenToken = parameters.openParenToken();
        this.closeParenToken = parameters.closeParenToken();
        this.block = null;
        this.throwsToken = null;
    }

    public MethodTreeImpl complete(InternalSyntaxToken returnType, InternalSyntaxToken simpleName, SyntaxToken semicolonToken) {
        Preconditions.checkState(this.simpleName == null);
        this.returnType = returnType;
        this.simpleName = simpleName;
        this.semicolonToken = semicolonToken;

        return this;
    }

    public MethodTreeImpl completeWithTypeParameters(TypeParameterListTreeImpl typeParameters) {
        this.typeParameters = typeParameters;
        return this;
    }

    public MethodTreeImpl completeWithModifiers(ModifiersTreeImpl modifiers) {
        Preconditions.checkState(this.modifiers == null);
        this.modifiers = modifiers;
        return this;
    }

    @Override
    protected Iterable<Tree> children() {
        ImmutableList.Builder<Tree> iteratorBuilder = ImmutableList.builder();
        iteratorBuilder.add(modifiers, typeParameters);
        if (returnType != null) {
            iteratorBuilder.add(returnType);
        }
        iteratorBuilder.add(simpleName, openParenToken);
        iteratorBuilder.addAll(parameters.iterator());
        iteratorBuilder.add(closeParenToken);
        if (throwsToken != null) {
            iteratorBuilder.add(throwsToken);
        }
        //Posible error with default tokens.
        if (block != null) {
            iteratorBuilder.add(block);
        } else {
            iteratorBuilder.add(semicolonToken);
        }
        return iteratorBuilder.build();
    }

    @Override
    public void accept(TreeVisitor visitor) {

    }

    @Override
    public Kind kind() {
        return returnType == null ? Kind.CONSTRUCTOR : Kind.METHOD;
    }

    @Override
    public ModifiersTree modifiers() {
        return modifiers;
    }

    @Override
    public InternalSyntaxToken returnType() {
        return returnType;
    }

    @Override
    public InternalSyntaxToken simpleName() {
        return simpleName;
    }
}
