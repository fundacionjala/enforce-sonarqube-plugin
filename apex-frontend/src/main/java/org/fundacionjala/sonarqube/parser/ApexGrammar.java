package org.fundacionjala.sonarqube.parser;

import com.sonar.sslr.api.typed.GrammarBuilder;
import com.sonar.sslr.api.typed.Optional;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.Tuple;
import org.fundacionjala.sonarqube.api.ApexKeyword;
import org.fundacionjala.sonarqube.api.ApexPunctuator;
import org.fundacionjala.sonarqube.tree.*;
import org.fundacionjala.sonarqube.treeimplementation.*;

import java.beans.Expression;
import java.util.List;

public class ApexGrammar {

    private final GrammarBuilder<InternalSyntaxToken> b;
    private final TreeFactory f;

    public ApexGrammar(GrammarBuilder<InternalSyntaxToken> b, TreeFactory f) {
        this.b = b;
        this.f = f;
    }

    public ModifiersTreeImpl MODIFIERS() {
        return b.<ModifiersTreeImpl>nonterminal(ApexLexer.MODIFIERS)
                .is(
                        f.modifiers(
                                b.<ModifierTree>oneOrMore(MODIFIER_KEYWORD())));
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

    public CompilationUnitTreeImpl COMPILATION_UNIT() {
        return b.<CompilationUnitTreeImpl>nonterminal(ApexLexer.COMPILATION_UNIT)
                .is(
                        f.newCompilationUnit(
                                b.oneOrMore(TYPE_DECLARATION()),
                                b.token(ApexLexer.EOF)));
    }

    public Tree TYPE_DECLARATION() {
        return b.<Tree>nonterminal(ApexLexer.TYPE_DECLARATION)
                .is(
                        f.newTypeDeclaration(
                                MODIFIERS(),
                                CLASS_DECLARATION()
                        ));
    }

    public ClassTreeImpl CLASS_DECLARATION() {
        return b.<ClassTreeImpl>nonterminal(ApexLexer.CLASS_DECLARATION)
                .is(
                        f.newClassDeclaration(
                                b.optional(
                                        f.getSharingRules(
                                                b.firstOf(
                                                        b.token(ApexKeyword.WITHOUT),
                                                        b.token(ApexKeyword.WITH)),
                                                b.token(ApexKeyword.SHARING))),
                                b.token(ApexKeyword.CLASS),
                                NAMING_KEYWORDS(),
                                b.optional(EXTENDS_LIST()),
                                CLASS_BODY()));
    }

    public List<InternalSyntaxToken> EXTENDS_LIST() {
        return b.<List<InternalSyntaxToken>>nonterminal(ApexLexer.EXTENDS_DECLARATION)
                .is(
                        f.defineExtendsDeclaration(
                                b.token(ApexKeyword.EXTENDS),
                                b.token(ApexLexer.IDENTIFIER)
                        )
                );
    }

    public NamingKeywordsTreeImpl NAMING_KEYWORDS() {
        return b.<NamingKeywordsTreeImpl>nonterminal().is(
                f.namingKeywords(
                        b.firstOf(
                                ALLOWED_KEYWORDS_AS_IDENTIFIER(),
                                SPECIAL_KEYWORDS_AS_IDENTIFIER()
                        )
                )
        );
    }

    public InternalSyntaxToken ALLOWED_KEYWORDS_AS_IDENTIFIER() {
        return b.<InternalSyntaxToken>nonterminal(ApexLexer.ALLOWED_KEYWORDS_AS_IDENTIFIER).is(
                b.firstOf(
                        b.token(ApexLexer.IDENTIFIER),
                        b.token(ApexKeyword.TRANSIENT),
                        b.token(ApexKeyword.RETURNING),
                        b.token(ApexKeyword.SEARCH),
                        b.token(ApexKeyword.STAT),
                        b.token(ApexKeyword.CONVERTCURRENCY),
                        b.token(ApexKeyword.SAVEPOINT),
                        b.token(ApexKeyword.TOLABEL),
                        b.token(ApexKeyword.SHARING),
                        b.token(ApexKeyword.GET),
                        b.token(ApexKeyword.AFTER),
                        b.token(ApexKeyword.BEFORE),
                        b.token(ApexKeyword.FIRST),
                        b.token(ApexKeyword.LAST),
                        b.token(ApexKeyword.CATEGORY),
                        b.token(ApexKeyword.NETWORK),
                        b.token(ApexKeyword.ITERATOR)
                ));
    }

    public InternalSyntaxToken SPECIAL_KEYWORDS_AS_IDENTIFIER() {
        return b.<InternalSyntaxToken>nonterminal(ApexLexer.SPECIAL_KEYWORDS_AS_IDENTIFIER).is(
                b.firstOf(
                        b.token(ApexKeyword.WITHOUT),
                        b.token(ApexKeyword.OFFSET),
                        b.token(ApexKeyword.DATA),
                        b.token(ApexKeyword.GROUP),
                        b.token(ApexKeyword.LIMIT)
                ));
    }

    public ClassTreeImpl CLASS_BODY() {
        return b.<ClassTreeImpl>nonterminal(ApexLexer.CLASS_BODY)
                .is(
                  f.newClassBody(
                    b.token(ApexPunctuator.LBRACE),
                    b.zeroOrMore(CLASS_MEMBER()),
                    b.token(ApexPunctuator.RBRACE)));
    }

    public ApexTree CLASS_MEMBER() {
        return b.<ApexTree>nonterminal(ApexLexer.MEMBER_DECLARATION)
                .is(
                    f.newEmptyMember(b.token(ApexPunctuator.SEMICOLON))
                );
    }
}
