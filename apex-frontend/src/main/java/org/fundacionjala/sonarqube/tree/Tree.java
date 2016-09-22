package org.fundacionjala.sonarqube.tree;

import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.sonar.sslr.grammar.GrammarRuleKey;

import javax.annotation.Nullable;

public interface Tree {

    boolean is(Kind... kinds);

    void accept(TreeVisitor visitor);

    @Nullable
    Tree parent();

    @Nullable
    SyntaxToken firstToken();

    @Nullable
    SyntaxToken lastToken();

    enum Kind implements GrammarRuleKey{

        COMPILATION_UNIT(CompilationUnitTree.class),

        TYPE_DECLARATION(TypeDeclarationTree.class),

        MODIFIERS(ModifiersTree.class),

        METHOD(MethodTree.class),

        LIST(ListTree.class),

        TOKEN(SyntaxToken.class),

        TRIVIA(SyntaxTrivia.class);

        final Class<? extends Tree> associatedInterface;

        Kind(Class<? extends Tree> associatedInterface) { this.associatedInterface = associatedInterface; }

        public Class<?extends Tree> getAssociatedInterface() { return associatedInterface; }

    }

    Kind kind();
}
