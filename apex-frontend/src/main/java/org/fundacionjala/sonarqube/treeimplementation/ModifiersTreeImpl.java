package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.collect.ImmutableList;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.fundacionjala.sonarqube.parser.TreeVisitor;
import org.fundacionjala.sonarqube.tree.ModifierKeywordTree;
import org.fundacionjala.sonarqube.tree.ModifierTree;
import org.fundacionjala.sonarqube.tree.ModifiersTree;

import java.util.List;

public class ModifiersTreeImpl extends ListTreeImpl<ModifierTree> implements ModifiersTree {

    private final List<ModifierKeywordTree> modifiers;

    public ModifiersTreeImpl(List<ModifierTree> apexTrees) {
        super(ApexLexer.MODIFIERS, apexTrees);
        ImmutableList.Builder<ModifierKeywordTree> modifierBuilder = ImmutableList.builder();
        for (ModifierTree modifierTree : this) {
                modifierBuilder.add((ModifierKeywordTree) modifierTree);
        }
        this.modifiers = modifierBuilder.build();
    }

    public static ModifiersTreeImpl emptyModifiers() {
        return new ModifiersTreeImpl(ImmutableList.<ModifierTree>of());
    }

    public Kind kind() {
        return Kind.MODIFIERS;
    }

    public List<ModifierKeywordTree> modifiers() {
        return modifiers;
    }

    public void accept(TreeVisitor visitor) {
        visitor.visitModifier(this);
    }

}
