package org.fundacionjala.sonarqube.tree;

import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.treeimplementation.SimpleTypeTreeImpl;
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

        TRIVIA(SyntaxTrivia.class),

        CLASS_OR_INTERFACE(ClassTree.class),

        NAMING_KEYWORDS(NamingKeywordsTree.class),

        CLASS_OR_INTERFACE_TYPE(ClassOrInterfaceTypeTree.class),

        INTERFACE(ClassTree.class),

        VARIABLE(VariableTree.class),

        CONSTRUCTOR(MethodTree.class),

        TYPE_PARAMETERS(TypeParameters.class),

        EMPTY_STATEMENT(EmptyStatementTree.class),

        BLOCK(BlockTree.class),

        PARENTHESIZED_EXPRESSION(ParenthesizedTree.class),

        TYPE_ARGUMENTS(TypeArguments.class),

        ARGUMENTS(Arguments.class),

        IDENTIFIER(IdentifierTree.class),

        METHOD_INVOCATION(MethodInvocationTree.class),

        MEMBER_SELECT(MemberSelectExpressionTree.class),

        EXPRESSION_STATEMENT(ExpressionStatementTree.class),

        TYPE(SimpleTypeTreeImpl.class),

        IF_STATEMENT(IfStatementTree.class),

        CONDITIONAL_EXPRESSION(ConditionalExpressionTree.class),

        MULTIPLY(BinaryExpressionTree.class),

        DIVIDE(BinaryExpressionTree.class),

        EQUAL_TO(BinaryExpressionTree.class),

        NOT_EQUAL_TO(BinaryExpressionTree.class),

        AND(BinaryExpressionTree.class),

        CONDITIONAL_AND(BinaryExpressionTree.class),

        CONDITIONAL_OR(BinaryExpressionTree.class);

        final Class<? extends Tree> associatedInterface;

        Kind(Class<? extends Tree> associatedInterface) { this.associatedInterface = associatedInterface; }

        public Class<?extends Tree> getAssociatedInterface() { return associatedInterface; }

    }

    Kind kind();
}
