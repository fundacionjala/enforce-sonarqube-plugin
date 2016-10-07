package org.fundacionjala.sonarqube.tree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.sonar.sslr.api.typed.Optional;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.KindMaps;
import org.fundacionjala.sonarqube.api.ApexKeyword;
import org.fundacionjala.sonarqube.api.ApexPunctuator;
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
        return partial;
    }

    public ClassTreeImpl newClassBody(InternalSyntaxToken openBraceToken,
                                      List<ApexTree> members,
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
                                              List<ApexTree> members, InternalSyntaxToken closeBrace) {
        ImmutableList.Builder<Tree> builder = ImmutableList.builder();
        /*if (members.isPresent()) {*/
            for (ApexTree member : members) {
                builder.add(member);
            }
        /*}*/
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

    public MethodTreeImpl newMethod(TypeTree type, InternalSyntaxToken identifier,
                                    FormalParametersListTreeImpl parameters,
                                    ApexTree block) {
        return newMethodOrConstructor(Optional.of(type), identifier, parameters, block);
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
            Optional<TypeTree> type, InternalSyntaxToken methodName,
            FormalParametersListTreeImpl parameters,
            ApexTree block) {

        TypeTree actualType;
        if(type.isPresent()) {
            actualType = type.get();
        } else {
            actualType = null;
        }
        return new MethodTreeImpl(actualType, methodName, parameters, (BlockTreeImpl) block);
    }

    public ArgumentListTreeImpl completeArguments(InternalSyntaxToken openParenthesisToken,
                                                  Optional<ArgumentListTreeImpl> expressions,
                                                  InternalSyntaxToken closeParenthesisToken) {
         return expressions.isPresent() ?
                expressions.get().complete(openParenthesisToken, closeParenthesisToken) :
                new ArgumentListTreeImpl(openParenthesisToken, closeParenthesisToken);
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

    public FormalParametersListTreeImpl completeParenFormalParameters(InternalSyntaxToken openParenToken,
                                                                      Optional<FormalParametersListTreeImpl> partial,
                                                                      InternalSyntaxToken closeParenToken) {

        return partial.isPresent() ?
                partial.get().complete(openParenToken, closeParenToken) :
                 new FormalParametersListTreeImpl(openParenToken, closeParenToken);
    }

    public VariableTreeImpl newVariableDeclaratorId(InternalSyntaxToken identifier) {
            return new VariableTreeImpl(identifier);
    }

    public FormalParametersListTreeImpl prependNewFormalParameter(VariableTreeImpl variable, Optional<Tuple<InternalSyntaxToken, FormalParametersListTreeImpl>> otherParameters) {
        if (otherParameters.isPresent()) {
            InternalSyntaxToken comma = otherParameters.get().getFirstElement();
            FormalParametersListTreeImpl partial = otherParameters.get().getSecondElement();

            partial.add(0, variable);

            variable.setEndToken(comma);

            return partial;
        } else {
            return new FormalParametersListTreeImpl(variable);
        }
    }

    public FormalParametersListTreeImpl completeFormalParametersDecls(Optional<InternalSyntaxToken> finalToken, TypeTree type, FormalParametersListTreeImpl partial) {
        VariableTreeImpl variable = partial.get(0);

        if(finalToken.isPresent()) {
            variable.completeFinalKeywordAndType(finalToken.get(), type);
        }

        return partial;
    }

    public SimpleTypeTreeImpl newType(InternalSyntaxToken identifier,
                                      Optional<List<Tuple<InternalSyntaxToken, InternalSyntaxToken>>> types) {
        return new SimpleTypeTreeImpl(identifier);
    }

    public IfStatementTreeImpl newIfWithElse(InternalSyntaxToken elseToken, StatementTree elseStatement) {
        return new IfStatementTreeImpl(elseToken, elseStatement);
    }

    public IfStatementTreeImpl completeIf(InternalSyntaxToken ifToken, InternalSyntaxToken openParen,
                                          ExpressionTree condition,
                                          InternalSyntaxToken closeParen, StatementTree statement, Optional<IfStatementTreeImpl> elseClause) {
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

    public BlockTreeImpl block(InternalSyntaxToken openBrace,
                               BlockStatementListTreeImpl blockStatements,
                               InternalSyntaxToken closeBrace) {
        return new BlockTreeImpl(openBrace,
                blockStatements,
                closeBrace);
    }

    public ConditionalExpressionTreeImpl newTernaryExpression(InternalSyntaxToken queryToken, ExpressionTree trueExpression, InternalSyntaxToken colonToken, ExpressionTree falseExpression) {
        return new ConditionalExpressionTreeImpl(queryToken, trueExpression, colonToken, falseExpression);
    }

    public ArgumentListTreeImpl newArguments(ExpressionTree expression, Optional<List<Tuple<InternalSyntaxToken, ExpressionTree>>> moreArgs) {
        ImmutableList.Builder<ExpressionTree> expressions = ImmutableList.builder();
        expressions.add(expression);
        ImmutableList.Builder<SyntaxToken> separators = ImmutableList.builder();
        if (moreArgs.isPresent()) {
            for (Tuple<InternalSyntaxToken, ExpressionTree> rest : moreArgs.get()) {
                separators.add(rest.getFirstElement());
                expressions.add(rest.getSecondElement());
            }
        }

        return new ArgumentListTreeImpl(expressions.build(), separators.build());
    }

    public OperatorAndOperand newOperatorAndOperand5(InternalSyntaxToken operator, ExpressionTree operand) {
        return newOperatorAndOperand(operator, operand);
    }

    private OperatorAndOperand newOperatorAndOperand(InternalSyntaxToken operator, ExpressionTree operand) {
        return new OperatorAndOperand(operator, operand);
    }

    public ExpressionTree binaryExpression5(ExpressionTree expression, Optional<List<OperatorAndOperand>> operatorAndOperands) {
        return binaryExpression(expression, operatorAndOperands);
    }

    public OperatorAndOperand newOperatorAndOperand9(InternalSyntaxToken operator, ExpressionTree operand) {
        return newOperatorAndOperand(operator, operand);
    }

    public ExpressionTree binaryExpression9(ExpressionTree expression, Optional<List<OperatorAndOperand>> operatorAndOperands) {
        return binaryExpression(expression, operatorAndOperands);
    }

    private ExpressionTree binaryExpression(ExpressionTree expression, Optional<List<OperatorAndOperand>> operatorAndOperands) {
        if(!operatorAndOperands.isPresent()) {
            return expression;
        }
        ExpressionTree result = expression;
        for (OperatorAndOperand operatorAndOperand : operatorAndOperands.get()) {
            result = new BinaryExpressionTreeImpl(
                    kindMaps.getBinaryOperator((ApexPunctuator) operatorAndOperand.operator().getGrammarRuleKey()),
                    result,
                    operatorAndOperand.operator(),
                    operatorAndOperand.operand());
        }
        return result;
    }

    public OperatorAndOperand newOperatorAndOperand10(InternalSyntaxToken operator, ExpressionTree operand) {
        return newOperatorAndOperand(operator, operand);
    }

    public ExpressionTree binaryExpression10(ExpressionTree expression, Optional<List<OperatorAndOperand>> operatorAndOperands) {
        return binaryExpression(expression, operatorAndOperands);
    }

    public ExpressionTree completeTernaryExpression(ExpressionTree expression, Optional<ConditionalExpressionTreeImpl> partial) {
        return partial.isPresent() ?
                partial.get().complete(expression) :
                expression;
    }

    // Helpers

    public static class Tuple<T, U> {
        private final T firstElement;
        private final U secondElement;

        public Tuple(T firstElement, U secondElement) {
            this.firstElement = firstElement;
            this.secondElement = secondElement;
        }

        public T getFirstElement() {
            return firstElement;
        }

        public U getSecondElement() {
            return secondElement;
        }
    }

    private static <T, U> Tuple<T, U> newTuple(T first, U second) {
        return new Tuple<>(first, second);
    }

    public <T, U> Tuple<T, U> newTuple1(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple2(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple3(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple4(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple5(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple6(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple7(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple8(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple9(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple10(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple11(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple12(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple14(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple16(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple17(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple18(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple19(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple20(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple21(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple22(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple23(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple24(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple25(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple26(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple27(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple28(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple29(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newAnnotatedDimension(T first, U second) {
        return newTuple(first, second);
    }


}
