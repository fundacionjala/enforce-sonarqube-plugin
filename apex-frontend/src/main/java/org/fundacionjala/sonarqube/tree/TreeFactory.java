package org.fundacionjala.sonarqube.tree;

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.typed.Optional;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.KindMaps;
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

    public Tree newEndOfFileTree() {
        return new EndOfFileTreeTreeImpl();
    }
}
