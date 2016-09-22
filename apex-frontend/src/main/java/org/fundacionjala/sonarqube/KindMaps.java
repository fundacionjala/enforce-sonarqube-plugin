package org.fundacionjala.sonarqube;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.fundacionjala.sonarqube.api.ApexKeyword;
import org.fundacionjala.sonarqube.tree.Modifier;
import org.fundacionjala.sonarqube.tree.Tree;

import java.util.Map;

public class KindMaps {
    private final Map<ApexKeyword, Modifier> modifiers = Maps.newEnumMap(ApexKeyword.class);

    public KindMaps() {
        modifiers.put(ApexKeyword.PUBLIC, Modifier.PUBLIC);
        modifiers.put(ApexKeyword.PROTECTED, Modifier.PROTECTED);
        modifiers.put(ApexKeyword.PRIVATE, Modifier.PRIVATE);
        modifiers.put(ApexKeyword.ABSTRACT, Modifier.ABSTRACT);
        modifiers.put(ApexKeyword.STATIC, Modifier.STATIC);
        modifiers.put(ApexKeyword.FINAL, Modifier.FINAL);
        modifiers.put(ApexKeyword.TRANSIENT, Modifier.TRANSIENT);
    }

    public Modifier getModifier(ApexKeyword keyword) {
        return Preconditions.checkNotNull(modifiers.get(keyword), "Mapping not found for modifier %s", keyword);
    }

}
