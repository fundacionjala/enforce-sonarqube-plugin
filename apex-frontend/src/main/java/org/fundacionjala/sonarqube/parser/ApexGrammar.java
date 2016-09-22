package org.fundacionjala.sonarqube.parser;

import com.sonar.sslr.api.typed.GrammarBuilder;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.api.ApexKeyword;
import org.fundacionjala.sonarqube.tree.ModifierTree;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.tree.TreeFactory;
import org.fundacionjala.sonarqube.treeimplementation.CompilationUnitTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.ModifierKeywordTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.ModifiersTreeImpl;

public class ApexGrammar {

    private final GrammarBuilder<InternalSyntaxToken> b;
    private final TreeFactory f;

    public ApexGrammar(GrammarBuilder<InternalSyntaxToken> b, TreeFactory f) {
        this.b = b;
        this.f = f;
    }

    public CompilationUnitTreeImpl COMPILATION_UNIT() {
        return b.<CompilationUnitTreeImpl>nonterminal(ApexLexer.COMPILATION_UNIT)
                .is(
                   f.newCompilationUnit(
                     b.zeroOrMore(TYPE_DECLARATION()),
                     b.token(ApexLexer.EOF)));
    }

    public Tree TYPE_DECLARATION() {
        return b.<Tree>nonterminal(ApexLexer.TYPE_DECLARATION).is(
                MODIFIERS()
        );
    }

    public ModifiersTreeImpl MODIFIERS() {
        return b.<ModifiersTreeImpl>nonterminal(ApexLexer.MODIFIERS)
                .is(
                  f.modifiers(
                    b.<ModifierTree>zeroOrMore(MODIFIER_KEYWORD())));
    }

    public ModifierKeywordTreeImpl MODIFIER_KEYWORD() {
        return b.<ModifierKeywordTreeImpl>nonterminal().is(
          f.modifierKeyword(
            b.firstOf(
              b.token(ApexKeyword.PUBLIC),
              b.token(ApexKeyword.PROTECTED),
              b.token(ApexKeyword.STATIC),
              b.token(ApexKeyword.PRIVATE),
              b.token(ApexKeyword.GLOBAL),
              b.token(ApexKeyword.FINAL),
              b.token(ApexKeyword.ABSTRACT),
              b.token(ApexKeyword.TRANSIENT))));
    }
}
