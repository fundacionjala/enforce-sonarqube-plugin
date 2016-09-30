package org.fundacionjala.sonarqube.tree;

import com.sonar.sslr.api.typed.Optional;
import org.fundacionjala.sonarqube.api.ApexKeyword;
import org.sonar.sslr.grammar.GrammarRuleKey;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class ApexTree implements Tree{

    private Tree parent;

    protected GrammarRuleKey grammarRuleKey;

    private List<Tree> children;

    public ApexTree(GrammarRuleKey grammarRuleKey) {
        this.grammarRuleKey = grammarRuleKey;
    }

    @Override
    @Nullable
    public SyntaxToken firstToken() {
        for (Tree child : getChildren()) {
            SyntaxToken first = child.firstToken();
            if (first != null) {
                return first;
            }
        }
        return null;
    }

    @Override
    @Nullable
    public SyntaxToken lastToken() {
        List<Tree> trees = getChildren();
        for (int index = trees.size() - 1; index >= 0; index--) {
            SyntaxToken last = trees.get(index).lastToken();
            if (last != null) {
                return last;
            }
        }
        return null;
    }

    public int getLine() {
        SyntaxToken firstSyntaxToken = firstToken();
        if (firstSyntaxToken == null) {
            return -1;
        }
        return firstSyntaxToken.line();
    }

    @Override
    public final boolean is(Kind... kinds) {
        Kind treeKind = kind();
        for (Kind kindIter : kinds) {
            if (treeKind == kindIter) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Tree parent() {
        return parent;
    }

    public void setParent(Tree parent) {
        this.parent = parent;
    }

    protected abstract Iterable<Tree> children();

    public List<Tree> getChildren() {
        if(children == null) {
            children = new ArrayList<>();
            children().forEach(child -> {
                // null children are ignored
                if (child != null) {
                    children.add(child);
                }
            });
        }
        return children;
    }


    public boolean isLeaf() {
        return false;
    }

    public GrammarRuleKey getGrammarRuleKey() {
        return grammarRuleKey;
    }
}
