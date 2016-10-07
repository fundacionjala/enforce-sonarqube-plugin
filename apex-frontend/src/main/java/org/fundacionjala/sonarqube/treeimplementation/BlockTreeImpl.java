package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.*;
import org.sonar.sslr.grammar.GrammarRuleKey;

import java.util.Collections;
import java.util.List;

public class BlockTreeImpl extends ApexTree implements BlockTree{
    private final Kind kind;
    private final InternalSyntaxToken openBraceToken;
    private final List<StatementTree> body;
    private final InternalSyntaxToken closeBraceToken;

    public BlockTreeImpl(InternalSyntaxToken openBraceToken,
                         List<StatementTree> body,
                         InternalSyntaxToken closeBraceToken) {
        this(Kind.BLOCK, openBraceToken,
                body,
                closeBraceToken);
    }

    public BlockTreeImpl(Kind kind, InternalSyntaxToken openBraceToken,
                         List<StatementTree> body,
                         InternalSyntaxToken closeBraceToken) {
        super(kind);

        this.kind = kind;
        this.openBraceToken = openBraceToken;
        this.body = Preconditions.checkNotNull(body);
        this.closeBraceToken = closeBraceToken;
    }

    @Override
    public Kind kind() {
        return kind;
    }

    @Override
    public SyntaxToken openBraceToken() {
        return openBraceToken;
    }

    @Override
    public List<StatementTree> body() {
        return body;
    }

    @Override
    public SyntaxToken closeBraceToken() {
        return closeBraceToken;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitBlock(this);
    }

    @Override
    public Iterable<Tree> children() {
        return Iterables.concat(
                Collections.singletonList(openBraceToken),
                /*body,*/
                Collections.singletonList(closeBraceToken));
    }
}
