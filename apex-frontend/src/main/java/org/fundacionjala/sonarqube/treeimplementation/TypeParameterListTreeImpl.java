package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.SyntaxToken;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.tree.TypeParameterTree;
import org.fundacionjala.sonarqube.tree.TypeParameters;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class TypeParameterListTreeImpl extends ListTreeImpl<TypeParameterTree> implements TypeParameters {
    @Nullable
    private final InternalSyntaxToken openBracketToken;
    @Nullable
    private final InternalSyntaxToken closeBracketToken;

    public TypeParameterListTreeImpl(InternalSyntaxToken openBracketToken, List<TypeParameterTree> typeParameters,
                                     List<SyntaxToken> separators, InternalSyntaxToken closeBracketToken) {
        super(ApexLexer.TYPE_PARAMETERS, typeParameters, separators);

        this.openBracketToken = openBracketToken;
        this.closeBracketToken = closeBracketToken;
    }

    public TypeParameterListTreeImpl() {
        super(ApexLexer.TYPE_PARAMETERS, ImmutableList.<TypeParameterTree>of(), ImmutableList.<SyntaxToken>of());
        this.openBracketToken = null;
        this.closeBracketToken = null;
    }

    @Nullable
    @Override
    public SyntaxToken openBracketToken() {
        return openBracketToken;
    }

    @Nullable
    @Override
    public SyntaxToken closeBracketToken() {
        return closeBracketToken;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitTypeParameters(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                Collections.singletonList(openBracketToken),
                super.children(),
                Collections.singletonList(closeBracketToken));
    }

    @Override
    public Kind kind() {
        return Kind.TYPE_PARAMETERS;
    }


}
