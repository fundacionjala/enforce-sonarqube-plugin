package org.fundacionjala.sonarqube;

import org.fundacionjala.sonarqube.tree.ApexTree;
import org.fundacionjala.sonarqube.tree.SyntaxToken;
import org.fundacionjala.sonarqube.tree.SyntaxTrivia;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;

import java.util.Collections;
import java.util.List;

public class InternalSyntaxTrivia extends ApexTree implements SyntaxTrivia {

    private final String comment;
    private final int startLine;
    private final int column;
    private int endLine;
    private int endColumn;

    public InternalSyntaxTrivia(String comment, int startLine, int column) {
        super(null);
        this.comment = comment;

        this.startLine = startLine;
        this.column = column;
        calculateEndOffsets();
    }
    private void calculateEndOffsets() {
        String[] lines = comment.split("\r\n|\n|\r", -1);
        endColumn = column + comment.length();
        endLine = startLine + lines.length - 1;

        if (endLine != startLine) {
            endColumn = lines[lines.length - 1].length();
        }
    }

    @Override
    public int endLine() {
        return endLine;
    }

    @Override
    public int endColumn() {
        return endColumn;
    }

    @Override
    public String text() {
        return comment;
    }

    @Override
    public List<SyntaxTrivia> trivias() {
        return Collections.emptyList();
    }

    @Override
    public int line() {
        return startLine;
    }

    @Override
    public int column() {
        return column;
    }

    public Kind getKind() {
        return Kind.TRIVIA;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    public static SyntaxTrivia create(String comment, int startLine, int column) {
        return new InternalSyntaxTrivia(comment, startLine, column);
    }

    @Override
    public int getLine() {
        return startLine;
    }

    @Override
    protected Iterable<Tree> children() {
        return null;
    }

    @Override
    public void accept(BaseTreeVisitor visitor) {
        visitor.visitComment(this);
    }

    @Override
    public Kind kind() {
        return null;
    }
}
