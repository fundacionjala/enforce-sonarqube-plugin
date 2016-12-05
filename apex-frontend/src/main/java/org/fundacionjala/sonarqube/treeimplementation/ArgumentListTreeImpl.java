package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.fundacionjala.sonarqube.tree.Arguments;
import org.fundacionjala.sonarqube.tree.ExpressionTree;
import org.fundacionjala.sonarqube.tree.SyntaxToken;
import org.fundacionjala.sonarqube.tree.Tree;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ArgumentListTreeImpl extends ListTreeImpl<ExpressionTree> implements Arguments{
    private InternalSyntaxToken openParenToken;
    private InternalSyntaxToken closeParenToken;

    public ArgumentListTreeImpl(InternalSyntaxToken openParenToken, InternalSyntaxToken closeParenToken) {
        super(ApexLexer.ARGUMENTS, ImmutableList.<ExpressionTree>of(), ImmutableList.<SyntaxToken>of());
        this.openParenToken = openParenToken;
        this.closeParenToken = closeParenToken;
    }

    public ArgumentListTreeImpl(InternalSyntaxToken openParenToken, ExpressionTree expression, InternalSyntaxToken closeParenToken) {
        super(ApexLexer.ARGUMENTS, ImmutableList.of(expression), ImmutableList.<SyntaxToken>of());
        this.openParenToken = openParenToken;
        this.closeParenToken = closeParenToken;
    }

    public ArgumentListTreeImpl(List<ExpressionTree> expressions, List<SyntaxToken> separators) {
        super(ApexLexer.ARGUMENTS, expressions, separators);
    }

    public ArgumentListTreeImpl complete(InternalSyntaxToken openParenToken, InternalSyntaxToken closeParenToken) {
        this.openParenToken = openParenToken;
        this.closeParenToken = closeParenToken;
        return this;
    }

    @Nullable
    public SyntaxToken openParenToken() {
        return openParenToken;
    }

    @Nullable
    public SyntaxToken closeParenToken() {
        return closeParenToken;
    }

    @Override
    public Tree.Kind kind() {
        return Tree.Kind.ARGUMENTS;
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                openParenToken != null ? Collections.singletonList(openParenToken) : Collections.<Tree>emptyList(),
                super.children(),
                closeParenToken != null ? Collections.singletonList(closeParenToken) : Collections.<Tree>emptyList());
    }
}
