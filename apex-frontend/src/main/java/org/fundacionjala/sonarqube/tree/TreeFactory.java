package org.fundacionjala.sonarqube.tree;

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.typed.Optional;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.KindMaps;
import org.fundacionjala.sonarqube.api.ApexKeyword;
import org.fundacionjala.sonarqube.treeimplementation.CompilationUnitTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.ModifierKeywordTreeImpl;
import org.fundacionjala.sonarqube.treeimplementation.ModifiersTreeImpl;

import java.util.List;

public class TreeFactory {

    private KindMaps kindMaps;

    public CompilationUnitTreeImpl newCompilationUnit(
            Optional<List<Tree>> typeDeclarations,
            InternalSyntaxToken eof) {

        ImmutableList.Builder<Tree> types = ImmutableList.builder();
        if (typeDeclarations.isPresent()) {
            for (Tree child : typeDeclarations.get()) {
                types.add(child);
            }
        }
        return new CompilationUnitTreeImpl(types.build(), eof);
    }

    public ModifiersTreeImpl modifiers(Optional<List<ModifierTree>> modifierNodes) {
        if (!modifierNodes.isPresent()) {
            return ModifiersTreeImpl.emptyModifiers();
        }
        return new ModifiersTreeImpl(modifierNodes.get());
    }

    public ModifierKeywordTreeImpl modifierKeyword(InternalSyntaxToken token) {
        ApexKeyword keyword = (ApexKeyword) token.getGrammarRuleKey();
        return new ModifierKeywordTreeImpl(kindMaps.getModifier(keyword), token);
    }
}
