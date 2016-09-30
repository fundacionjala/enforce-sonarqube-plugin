package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
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
    private SyntaxToken withOrWithougToken;
    @Nullable
    private final SyntaxToken closeBraceToken;
    private ModifiersTree modifiers;
    private SyntaxToken atToken;
    private SyntaxToken declarationKeyword;
    private InternalSyntaxToken simpleName;
    private TypeParameters typeParameters;
    @Nullable
    private SyntaxToken extendsKeyword;
    @Nullable
    private InternalSyntaxToken superClass;
    @Nullable
    private SyntaxToken implementsKeyword;
    private ListTree<TypeTree> superInterfaces;
    private InternalSyntaxToken sharingToken;

    public ClassTreeImpl(Kind kind, SyntaxToken openBraceToken, List<Tree> members, SyntaxToken closeBraceToken) {
        super(kind);

        this.kind = kind;
        this.openBraceToken = openBraceToken;
        this.members = members;
        this.closeBraceToken = closeBraceToken;
        this.modifiers = ModifiersTreeImpl.emptyModifiers();
    }

    //Delete this constructor when members will being implemented


    public ClassTreeImpl completeSharingRules(List<InternalSyntaxToken> sharingRules) {
        this.withOrWithougToken = sharingRules.iterator().next();
        this.sharingToken = sharingRules.iterator().next();
        return this;
    }

    public ClassTreeImpl completeModifiers(ModifiersTreeImpl modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public ClassTreeImpl completeIdentifier(NamingKeywordsTree identifier) {
        this.simpleName = identifier.idendifier();
        return this;
    }

    public ClassTreeImpl completeSuperclass(List<InternalSyntaxToken> extensions) {
        this.extendsKeyword = extensions.iterator().next();
        this.superClass = extensions.iterator().next();
        return this;
    }
/*

    public ClassTreeImpl completeInterfaces(SyntaxToken keyword, QualifiedIdentifierListTreeImpl interfaces) {
        if (is(Kind.INTERFACE)) {
            extendsKeyword = keyword;
        } else {
            implementsKeyword = keyword;
        }
        this.superInterfaces = interfaces;
        return this;
    }
*/

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
    public InternalSyntaxToken simpleName() {
        return simpleName;
    }

/*    @Override
    public TypeParameters typeParameters() {
        return typeParameters;
    }
*/
    @Override
    public ModifiersTree modifiers() {
        return modifiers;
    }
/*
    @Nullable
    @Override
    public TypeTree superClass() {
        return superClass;
    }

    @Override
    public ListTree<TypeTree> superInterfaces() {
        return superInterfaces;
    }
*/
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
        return simpleName.getLine();
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                Collections.singletonList(modifiers),
                addIfNotNull(atToken),
                addIfNotNull(withOrWithougToken),
                addIfNotNull(sharingToken),
                addIfNotNull(declarationKeyword),
                addIfNotNull(simpleName),
                Collections.singletonList(typeParameters),
                addIfNotNull(extendsKeyword),
                addIfNotNull(superClass),
                addIfNotNull(implementsKeyword),
                Collections.singletonList(superInterfaces),
                addIfNotNull(openBraceToken),
/*
                members,
*/
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
