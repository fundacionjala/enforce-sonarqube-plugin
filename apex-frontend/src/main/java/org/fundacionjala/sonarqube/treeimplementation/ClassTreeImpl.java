package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.*;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ClassTreeImpl extends ApexTree implements ClassTree{
    private final Kind kind;
    @Nullable
    private final SyntaxToken openBraceToken;
    private final List<Tree> members;
    @Nullable
    private final SyntaxToken closeBraceToken;
    private ModifiersTree modifiers;
    private SyntaxToken atToken;
    private SyntaxToken declarationKeyword;
    private IdentifierTree simpleName;
    private TypeParameters typeParameters;
    @Nullable
    private SyntaxToken extendsKeyword;
    @Nullable
    private TypeTree superClass;
    @Nullable
    private SyntaxToken implementsKeyword;
    private ListTree<TypeTree> superInterfaces;

    public ClassTreeImpl(Kind kind, SyntaxToken openBraceToken, List<Tree> members, SyntaxToken closeBraceToken) {
        super(kind);

        this.kind = kind;
        this.openBraceToken = openBraceToken;
        this.members = members;
        this.closeBraceToken = closeBraceToken;
        this.modifiers = ModifiersTreeImpl.emptyModifiers();
    }

    public ClassTreeImpl completeModifiers(ModifiersTreeImpl modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public ClassTreeImpl completeIdentifier(IdentifierTree identifier) {
        this.simpleName = identifier;
        return this;
    }

    public ClassTreeImpl completeSuperclass(SyntaxToken extendsKeyword, TypeTree superClass) {
        this.extendsKeyword = extendsKeyword;
        this.superClass = superClass;
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

 /*   @Nullable
    @Override
    public IdentifierTree simpleName() {
        return simpleName;
    }

    @Override
    public TypeParameters typeParameters() {
        return typeParameters;
    }

    @Override
    public ModifiersTree modifiers() {
        return modifiers;
    }

    @Nullable
    @Override
    public TypeTree superClass() {
        return superClass;
    }

    @Override
    public ListTree<TypeTree> superInterfaces() {
        return superInterfaces;
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
*/
    @Nullable
    @Override
    public SyntaxToken declarationKeyword() {
        return declarationKeyword;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitClass(this);
    }

    @Override
    public int getLine() {
        if (simpleName == null) {
            return super.getLine();
        }
        return ((IdentifierTreeImpl) simpleName).getLine();
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                Collections.singletonList(modifiers),
                addIfNotNull(atToken),
                addIfNotNull(declarationKeyword),
                addIfNotNull(simpleName),
                Collections.singletonList(typeParameters),
                addIfNotNull(extendsKeyword),
                addIfNotNull(superClass),
                addIfNotNull(implementsKeyword),
                Collections.singletonList(superInterfaces),
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
