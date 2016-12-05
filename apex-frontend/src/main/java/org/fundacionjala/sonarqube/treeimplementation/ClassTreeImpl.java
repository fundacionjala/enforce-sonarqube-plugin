package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.semantic.Symbol;
import org.fundacionjala.sonarqube.tree.*;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ClassTreeImpl extends ApexTree implements ClassTree{
    private final Kind kind;
    private final SyntaxToken openBraceToken;
    private final List<Tree> members;
    private SyntaxToken withOrWithoutToken;
    private final SyntaxToken closeBraceToken;
    private ModifiersTree modifiers;
    private SyntaxToken declarationKeyword;
    private IdentifierTree simpleName;
    private TypeParameters typeParameters;
    private SyntaxToken extendsKeyword;
    private InternalSyntaxToken superClass;
    private InternalSyntaxToken sharingToken;
    private Symbol.Kind symbol = Symbol.Kind.CLASS;

    public ClassTreeImpl(Kind kind, SyntaxToken openBraceToken, List<Tree> members, SyntaxToken closeBraceToken) {
        super(kind);

        this.kind = kind;
        this.openBraceToken = openBraceToken;
        this.members = members;
        this.closeBraceToken = closeBraceToken;
        this.modifiers = ModifiersTreeImpl.emptyModifiers();
    }

    public ClassTreeImpl completeSharingRules(List<InternalSyntaxToken> sharingRules) {
        this.withOrWithoutToken = sharingRules.iterator().next();
        this.sharingToken = sharingRules.iterator().next();
        return this;
    }

    public ClassTreeImpl completeModifiers(ModifiersTreeImpl modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public ClassTreeImpl completeIdentifier(IdentifierTree identifier) {
        this.simpleName = identifier;
        return this;
    }

    public ClassTreeImpl completeSuperclass(List<InternalSyntaxToken> extensions) {
        this.extendsKeyword = extensions.iterator().next();
        this.superClass = extensions.iterator().next();
        return this;
    }

    public ClassTreeImpl completeDeclarationKeyword(SyntaxToken declarationKeyword) {
        this.declarationKeyword = declarationKeyword;
        return this;
    }

    @Override
    public Kind kind() {
        return kind;
    }

   @Nullable
    @Override
    public IdentifierTree simpleName() {
        return simpleName;
    }

    @Override
    public TypeParameters typeParameters() {
        return null;
    }

    @Override
    public ModifiersTree modifiers() {
        return modifiers;
    }

    @Nullable
    @Override
    public SyntaxToken openBraceToken() {
        return openBraceToken;
    }

    @Override
    public List<Tree> members() {
        return members;
    }

    @Nullable
    @Override
    public SyntaxToken closeBraceToken() {
        return closeBraceToken;
    }

    @Override
    public Symbol.Kind symbol() {
        return symbol;
    }

    @Nullable
    @Override
    public SyntaxToken declarationKeyword() {
        return declarationKeyword;
    }

    @Override
    public void accept(BaseTreeVisitor visitor) {
        visitor.visitClass(this);
    }

    @Override
    public int getLine() {
        if (simpleName == null) {
            return super.getLine();
        }
        return ((IdentifierTreeImpl)simpleName).getLine();
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                Collections.singletonList(modifiers),
                addIfNotNull(withOrWithoutToken),
                addIfNotNull(declarationKeyword),
                addIfNotNull(simpleName),
                Collections.singletonList(typeParameters),
                addIfNotNull(extendsKeyword),
                addIfNotNull(superClass),
                addIfNotNull(openBraceToken),
                members,
                addIfNotNull(closeBraceToken)
        );
    }

    private static Iterable<Tree> addIfNotNull(@Nullable Tree tree) {
        if (tree == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(tree);
    }

}
