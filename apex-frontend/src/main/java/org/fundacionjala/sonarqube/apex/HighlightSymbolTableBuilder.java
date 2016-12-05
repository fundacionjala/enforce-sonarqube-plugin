package org.fundacionjala.sonarqube.apex;

import org.fundacionjala.sonarqube.semantic.Symbol;
import org.fundacionjala.sonarqube.semantic.Usage;
import org.fundacionjala.sonarqube.tree.IdentifierTree;
import org.fundacionjala.sonarqube.tree.SyntaxToken;
import org.fundacionjala.sonarqube.visitors.TreeVisitorContext;
import org.sonar.api.batch.sensor.symbol.NewSymbol;
import org.sonar.api.batch.sensor.symbol.NewSymbolTable;

import java.util.LinkedList;

public class HighlightSymbolTableBuilder {

    private HighlightSymbolTableBuilder() {
    }

    public static void build(NewSymbolTable newSymbolTable, TreeVisitorContext context) {

        for (Symbol symbol : context.getSymbolModel().getSymbols()) {
            highlightSymbol(newSymbolTable, symbol);
        }

        newSymbolTable.save();
    }

    private static void highlightSymbol(NewSymbolTable newSymbolTable, Symbol symbol) {
        if (!symbol.usages().isEmpty()) {
            LinkedList<Usage> usagesList = new LinkedList<>(symbol.usages());
            SyntaxToken token = (usagesList.getFirst().identifierTree()).identifierToken();
            NewSymbol newSymbol = getHighlightedSymbol(newSymbolTable, token);
            for (int i = 1; i < usagesList.size(); i++) {
                Usage validUsage = usagesList.get(i);
                if (!validUsage.isExternalInvocation()) {
                    SyntaxToken referenceToken = getToken(validUsage.identifierTree());
                    addReference(newSymbol, referenceToken);
                }
            }
        }
    }

    private static void addReference(NewSymbol symbol, SyntaxToken referenceToken) {
        symbol.newReference(referenceToken.line(), referenceToken.column(), referenceToken.line(), referenceToken.column() + referenceToken.text().length());
    }

    private static NewSymbol getHighlightedSymbol(NewSymbolTable newSymbolTable, SyntaxToken token) {
        return newSymbolTable.newSymbol(token.line(), token.column(), token.line(), token.column() + token.text().length());
    }

    private static SyntaxToken getToken(IdentifierTree identifierTree) {
        return (identifierTree).identifierToken();
    }


}
