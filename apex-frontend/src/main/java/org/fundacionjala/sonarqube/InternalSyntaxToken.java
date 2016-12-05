package org.fundacionjala.sonarqube;

import org.fundacionjala.sonarqube.tree.SyntaxToken;
import org.fundacionjala.sonarqube.tree.SyntaxTrivia;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitor;
import org.fundacionjala.sonarqube.visitors.TreeVisitor;
import org.fundacionjala.sonarqube.tree.ApexTree;
import org.fundacionjala.sonarqube.tree.Tree;
import org.sonar.sslr.grammar.GrammarRuleKey;

import java.util.List;

public class InternalSyntaxToken extends ApexTree implements SyntaxToken {

    private List<SyntaxTrivia> trivias;
    private int startIndex;
    private int endIndex;
    private final int line;
    private final int column;
    private int endLine;
    private int endColumn;
    private final String value;
    private final boolean isEOF;

    protected InternalSyntaxToken(InternalSyntaxToken internalSyntaxToken) {
        super(null);
        this.value = internalSyntaxToken.value;
        this.line = internalSyntaxToken.line;
        this.column = internalSyntaxToken.column;
        this.trivias = internalSyntaxToken.trivias;
        this.startIndex = internalSyntaxToken.startIndex;
        this.endIndex = internalSyntaxToken.endIndex;
        this.isEOF = internalSyntaxToken.isEOF;
        calculateEndOffsets();
    }

    public InternalSyntaxToken(int line, int column, String value, List<SyntaxTrivia> trivias, int startIndex, int endIndex, boolean isEOF) {
        super(null);
        this.value = value;
        this.line = line;
        this.column = column;
        this.trivias = trivias;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.isEOF = isEOF;
    }

    private void calculateEndOffsets() {
        String[] lines = value.split("\r\n|\n|\r", -1);
        endColumn = column + value.length();
        endLine = line + lines.length - 1;

        if (endLine != line) {
            endColumn = lines[lines.length - 1].length();
        }
    }

    @Override
    public String text() {
        return value;
    }

    @Override
    public List<SyntaxTrivia> trivias() {
        return trivias;
    }

    @Override
    public int line() {
        return line;
    }

    @Override
    public int column() {
        return column;
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
    public void accept(BaseTreeVisitor visitor) {
        visitor.visitToken(this);
    }

    public boolean isLeaf() {
        return true;
    }

    public boolean isEOF() {
        return isEOF;
    }

    @Override
    public SyntaxToken firstToken() {
        return this;
    }

    @Override
    public SyntaxToken lastToken() {
        return this;
    }

    @Override
    public Kind kind() {
        return Kind.TOKEN;
    }

    public GrammarRuleKey getGrammarRuleKey() {
        return grammarRuleKey;
    }

    @Override
    public Iterable<Tree> children() {
        throw new UnsupportedOperationException();
    }

    public void setGrammarRuleKey(GrammarRuleKey grammarRuleKey) {
        this.grammarRuleKey = grammarRuleKey;
    }
}
