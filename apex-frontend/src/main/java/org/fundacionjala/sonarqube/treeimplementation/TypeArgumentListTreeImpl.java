package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.fundacionjala.sonarqube.tree.SyntaxToken;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.tree.TypeArguments;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;

import java.util.Collections;
import java.util.List;

public class TypeArgumentListTreeImpl extends ListTreeImpl<Tree> implements TypeArguments {

    private final InternalSyntaxToken openBracketToken;
    private final InternalSyntaxToken closeBracketToken;

    public TypeArgumentListTreeImpl(InternalSyntaxToken openBracketToken, List<Tree> expressions, List<SyntaxToken> separators, InternalSyntaxToken closeBracketToken) {
        super(ApexLexer.TYPE_ARGUMENTS, expressions, separators);

        this.openBracketToken = openBracketToken;
        this.closeBracketToken = closeBracketToken;
    }

    public SyntaxToken openBracketToken() {
        return openBracketToken;
    }

    public SyntaxToken closeBracketToken() {
        return closeBracketToken;
    }

    @Override
    public void accept(BaseTreeVisitor visitor) {
        visitor.visitTypeArguments(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                Collections.singletonList(openBracketToken),
                super.children(),
                Collections.singletonList(closeBracketToken));
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Kind kind() {
        return Kind.TYPE_ARGUMENTS;
    }
}
