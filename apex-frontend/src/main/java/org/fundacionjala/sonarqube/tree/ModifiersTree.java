package org.fundacionjala.sonarqube.tree;

import java.util.List;

public interface ModifiersTree extends ListTree<ModifierTree> {

    List<ModifierKeywordTree> modifiers();

}
