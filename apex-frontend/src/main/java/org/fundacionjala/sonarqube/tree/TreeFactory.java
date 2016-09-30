package org.fundacionjala.sonarqube.tree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.sonar.sslr.api.typed.Optional;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.KindMaps;
import org.fundacionjala.sonarqube.Tuple;
import org.fundacionjala.sonarqube.api.ApexKeyword;
import org.fundacionjala.sonarqube.treeimplementation.*;

import java.util.List;

public class TreeFactory {

    private KindMaps kindMaps = new KindMaps();

    public CompilationUnitTreeImpl newCompilationUnit(
            List<Tree> typeDeclarations,
            InternalSyntaxToken eof) {

        ImmutableList.Builder<Tree> types = ImmutableList.builder();
        for (Tree child : typeDeclarations) {
            types.add(child);
        }
        return new CompilationUnitTreeImpl(types.build(), eof);
    }

    public ModifiersTreeImpl modifiers(List<ModifierTree> modifierNodes) {
        if (modifierNodes.isEmpty()) {
            return ModifiersTreeImpl.emptyModifiers();
        }
        return new ModifiersTreeImpl(modifierNodes);
    }

    public ModifierKeywordTreeImpl modifierKeyword(InternalSyntaxToken token) {
        ApexKeyword keyword = (ApexKeyword) token.getGrammarRuleKey();
        return new ModifierKeywordTreeImpl(kindMaps.getModifier(keyword), token);
    }

    public ClassTreeImpl newTypeDeclaration(ModifiersTreeImpl modifiers, ClassTreeImpl classDeclaration) {
        return classDeclaration.completeModifiers(modifiers);
    }

    public ClassTreeImpl newClassDeclaration(Optional<List<InternalSyntaxToken>> sharingRules,
                                             InternalSyntaxToken token,
                                             NamingKeywordsTreeImpl namingKeywordsTree,
                                             Optional<List<InternalSyntaxToken>> extendsDeclaration,
                                             /*Optional<List<InternalSyntaxToken>> implementsDeclaration,*/
                                             ClassTreeImpl partial) {
        if(sharingRules.isPresent()) {
            partial.completeSharingRules(sharingRules.get());
        }
        partial.completeDeclarationKeyword(token);
        partial.completeIdentifier(namingKeywordsTree);
        if (extendsDeclaration.isPresent()) {
            partial.completeSuperclass(extendsDeclaration.get());
        }
      /*  if(implementsDeclaration.isPresent()) {

        }*/
        return partial;
    }

    public ClassTreeImpl newClassBody(InternalSyntaxToken openBraceToken,
                                      Optional<List<ApexTree>> members,
                                      InternalSyntaxToken closeBraceToken){
        return newClassBody(Tree.Kind.CLASS_OR_INTERFACE,openBraceToken, members, closeBraceToken);
    }

    public NamingKeywordsTreeImpl namingKeywords(InternalSyntaxToken namingToken) {
        return new NamingKeywordsTreeImpl(Tree.Kind.NAMING_KEYWORDS, namingToken);
    }

    public List<InternalSyntaxToken> getSharingRules(InternalSyntaxToken withOrWithout, InternalSyntaxToken sharing) {
        return Lists.newArrayList(withOrWithout, sharing);
    }

    public List<InternalSyntaxToken> defineExtendsDeclaration(InternalSyntaxToken extendsToken, InternalSyntaxToken extension) {
        return Lists.newArrayList(extendsToken, extension);
    }

    public List<InternalSyntaxToken> defineImplementsDeclaration(InternalSyntaxToken implementsKeyword, List<InternalSyntaxToken> arguments) {
        List<InternalSyntaxToken> partial = Lists.newArrayList();
        partial.add(implementsKeyword);
        partial.add(arguments.iterator().next());
        while(arguments.iterator().hasNext()) {
            partial.add(arguments.iterator().next());
        }
        return partial;
    }

    public List<InternalSyntaxToken> defineImplementsArguments(InternalSyntaxToken firstImplementation, Optional<List
            <Tuple<InternalSyntaxToken,InternalSyntaxToken>>> otherImplementations) {
        List<InternalSyntaxToken> result = Lists.newArrayList(firstImplementation);
        if(otherImplementations.isPresent()) {

        }
        return result;
    }

    public Tuple<InternalSyntaxToken, InternalSyntaxToken> defineImplementations(InternalSyntaxToken comma, InternalSyntaxToken otherImplementation) {
        return new Tuple<>(comma, otherImplementation);
    }

    private static ClassTreeImpl newClassBody(Tree.Kind kind, InternalSyntaxToken openBrace,
                                              Optional<List<ApexTree>> members, InternalSyntaxToken closeBrace) {
        ImmutableList.Builder<Tree> builder = ImmutableList.builder();
        if (members.isPresent()) {
            for (ApexTree member : members.get()) {
                builder.add(member);
            }
        }
        return new ClassTreeImpl(kind, openBrace, builder.build(), closeBrace);
    }

    public ApexTree completeMember(ModifiersTreeImpl modifiers, ApexTree partial) {
        if(partial instanceof MethodTreeImpl) {
            ((MethodTreeImpl)partial).completeWithModifiers(modifiers);
        }
        return partial;
    }

    public EmptyStatementTreeImpl newEmptyMember(InternalSyntaxToken semicolonToken) {
        return new EmptyStatementTreeImpl(semicolonToken);
    }

    public MethodTreeImpl newMethod(SyntaxToken type, InternalSyntaxToken identifier,
                                    FormalParametersListTreeImpl parameters,
                                    ApexTree blockOrSemicolon) {
        return newMethodOrConstructor(Optional.of(type), identifier, parameters, blockOrSemicolon);
    }

    public ParenthesizedTreeImpl parenthesizedExpression(InternalSyntaxToken leftParen, ExpressionTree expression, InternalSyntaxToken rightParen) {
        return new ParenthesizedTreeImpl(leftParen, expression, rightParen);
    }

    public Tuple<Optional<InternalSyntaxToken>, ExpressionTree> completeMemberSelectorOMethodSelector(InternalSyntaxToken dotToken,ExpressionTree partial){
        return new Tuple(Optional.of(dotToken), partial);
    }

    public ArgumentListTreeImpl newArguments1(ExpressionTree expression, Optional<List<Tuple>> otherExpressions) {
        ImmutableList.Builder<ExpressionTree> expressions = ImmutableList.builder();
        expressions.add(expression);
        ImmutableList.Builder<SyntaxToken> separators = ImmutableList.builder();
        if (otherExpressions.isPresent()) {
            for (Tuple<InternalSyntaxToken, ExpressionTree> rest : otherExpressions.get()) {
                separators.add(rest.getFirstElement());
                expressions.add(rest.getSecondElement());
            }
        }

        return new ArgumentListTreeImpl(expressions.build(), separators.build());
    }

    private static MethodTreeImpl newMethodOrConstructor(
            Optional<SyntaxToken> type, InternalSyntaxToken identifierToken, FormalParametersListTreeImpl parameters,
            ApexTree blockOrSemicolon) {

        /*IdentifierTreeImpl identifier = new IdentifierTreeImpl(identifierToken);*/
        SyntaxToken actualType;
        if(type.isPresent()) {
            actualType = type.get();
        } else {
            actualType = null;
        }
        BlockTreeImpl block = null;
        InternalSyntaxToken semicolonToken = null;
        if (blockOrSemicolon.is(Tree.Kind.BLOCK)) {
            block = (BlockTreeImpl) blockOrSemicolon;
        } else {
            semicolonToken = (InternalSyntaxToken) blockOrSemicolon;
        }

        return new MethodTreeImpl(
                actualType,
                identifierToken,
                parameters,
                block,
                semicolonToken);
    }

    public ArgumentListTreeImpl completeArguments(InternalSyntaxToken openParenthesisToken,
                                                  /*Optional<ArgumentListTreeImpl> expressions,*/
                                                  InternalSyntaxToken closeParenthesisToken) {
         /*expressions.isPresent() ?
                expressions.get().complete(openParenthesisToken, closeParenthesisToken) :*/
         return       new ArgumentListTreeImpl(openParenthesisToken, closeParenthesisToken);
    }

    public ExpressionTree newIdentifierOrMethodInvocation(InternalSyntaxToken identifier
            , Optional<ArgumentListTreeImpl> arguments
    ) {
            ExpressionTree result = new IdentifierTreeImpl(identifier) {
            };
            if (arguments.isPresent()) {
                result = new MethodInvocationTreeImpl(result, arguments.get());
            }
            return result;
    }

    public Tuple<Optional<InternalSyntaxToken>, ExpressionTree> completeMemberSelectOrMethodSelector(InternalSyntaxToken dotToken, ExpressionTree partial) {
        return new Tuple(Optional.of(dotToken), partial);
    }

    public ExpressionTree applySelectors1(ExpressionTree primary, Optional<List<Tuple<Optional<InternalSyntaxToken>, ExpressionTree>>> selectors) {
        return applySelectors(primary, selectors);
    }

    private static ExpressionTree applySelectors(ExpressionTree primary, Optional<List<Tuple<Optional<InternalSyntaxToken>, ExpressionTree>>> selectors) {
        ExpressionTree result = primary;

        if (selectors.isPresent()) {
            for (Tuple<Optional<InternalSyntaxToken>, ExpressionTree> tuple : selectors.get()) {
                Optional<InternalSyntaxToken> dotTokenOptional = tuple.getFirstElement();
                ExpressionTree selector = tuple.getSecondElement();

                if (dotTokenOptional.isPresent()) {
                    InternalSyntaxToken dotToken = dotTokenOptional.get();
                    if (selector.is(Tree.Kind.IDENTIFIER)) {
                        IdentifierTreeImpl identifier = (IdentifierTreeImpl) selector;
                        result = new MemberSelectExpressionTreeImpl(result, dotToken, identifier);
                    } else {
                        MethodInvocationTreeImpl methodInvocation = (MethodInvocationTreeImpl) selector;
                        IdentifierTreeImpl identifier = (IdentifierTreeImpl) methodInvocation.methodSelect();
                        MemberSelectExpressionTreeImpl memberSelect = new MemberSelectExpressionTreeImpl(result, dotToken, identifier);

                        result = new MethodInvocationTreeImpl(memberSelect, (ArgumentListTreeImpl) methodInvocation.arguments());
                    }
                } else if (selector.is(Tree.Kind.MEMBER_SELECT)) {
                    MemberSelectExpressionTreeImpl memberSelect = (MemberSelectExpressionTreeImpl) selector;
                    result = memberSelect.completeWithExpression(result);
                } else {
                    throw new IllegalStateException();
                }
            }
        }

        return result;
    }

    public EmptyStatementTreeImpl emptyStatement(InternalSyntaxToken semicolon) {
        return new EmptyStatementTreeImpl(semicolon);
    }

    public ExpressionStatementTreeImpl expressionStatement(ExpressionTree expression, InternalSyntaxToken semicolonToken) {
        return new ExpressionStatementTreeImpl(expression, semicolonToken);
    }

    public FormalParametersListTreeImpl completeParenFormalParameters(InternalSyntaxToken openParenToken, Optional<FormalParametersListTreeImpl> partial,
                                                                      InternalSyntaxToken closeParenToken) {

        return partial.isPresent() ?
                partial.get().complete(openParenToken, closeParenToken) :
                new FormalParametersListTreeImpl(openParenToken, closeParenToken);
    }

    public VariableTreeImpl newVariableDeclaratorId(InternalSyntaxToken identifier) {
            IdentifierTreeImpl identifierImpl = new IdentifierTreeImpl(identifier);
            return new VariableTreeImpl(identifierImpl);
    }

    public FormalParametersListTreeImpl prependNewFormalParameter(VariableTreeImpl variableTree, Optional<Tuple<InternalSyntaxToken, FormalParametersListTreeImpl>> others) {
        if (others.isPresent()) {
            InternalSyntaxToken comma = others.get().getFirstElement();
            FormalParametersListTreeImpl partial = others.get().getSecondElement();

            partial.add(0, variableTree);

            // store the comma as endToken for the variable
            variableTree.setEndToken(comma);

            return partial;
        } else {
            return new FormalParametersListTreeImpl(variableTree);
        }
    }

    public FormalParametersListTreeImpl completeTypeFormalParameters(ModifiersTreeImpl modifiers, TypeTree type, FormalParametersListTreeImpl partial) {
        VariableTreeImpl variable = partial.get(0);

        variable.completeModifiersAndType(modifiers, type);

        return partial;
    }

    public SimpleTypeTreeImpl newType(InternalSyntaxToken identifier) {
        return new SimpleTypeTreeImpl(identifier);
    }

    public IfStatementTreeImpl newIfWithElse(InternalSyntaxToken elseToken, StatementTree elseStatement) {
        return new IfStatementTreeImpl(elseToken, elseStatement);
    }

    public IfStatementTreeImpl completeIf(InternalSyntaxToken ifToken, InternalSyntaxToken openParen, ExpressionTree condition, InternalSyntaxToken closeParen, StatementTree statement, Optional<IfStatementTreeImpl> elseClause) {
        if (elseClause.isPresent()) {
           return elseClause.get().complete(ifToken, openParen, condition, closeParen, statement);
        } else {
           return new IfStatementTreeImpl(ifToken, openParen, condition, closeParen, statement);
        }
    }

    public BlockStatementListTreeImpl wrapInBlockStatements(StatementTree statement) {
        return new BlockStatementListTreeImpl(ImmutableList.of(statement));
    }

    public BlockStatementListTreeImpl blockStatements(Optional<List<BlockStatementListTreeImpl>> blockStatements) {
        ImmutableList.Builder<StatementTree> builder = ImmutableList.builder();

        if (blockStatements.isPresent()) {
            for (BlockStatementListTreeImpl blockStatement : blockStatements.get()) {
                builder.addAll(blockStatement);
            }
        }

        return new BlockStatementListTreeImpl(builder.build());
    }

    public BlockTreeImpl block(InternalSyntaxToken openBrace, BlockStatementListTreeImpl blockStatements, InternalSyntaxToken closeBrace) {
        return new BlockTreeImpl(openBrace, blockStatements, closeBrace);
    }

/*    public <T extends Tree> T defineClassOrInterfaceType(InternalSyntaxToken token, Optional<List<Tuple<InternalSyntaxToken, InternalSyntaxToken>>> listOptional) {
        ClassOrInterfaceTypeTreeImpl result;
        if(listOptional.isPresent()) {
            for(Tuple<InternalSyntaxToken, InternalSyntaxToken> rest : listOptional.get()) {
                InternalSyntaxToken dotToken = rest.getFirstElement();
                result = new ClassOrInterfaceTypeTreeImpl(token,)
            }
        }
        return (T) result;
    }*/
}
