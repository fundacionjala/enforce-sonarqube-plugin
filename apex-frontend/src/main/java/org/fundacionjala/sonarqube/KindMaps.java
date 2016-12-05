package org.fundacionjala.sonarqube;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.fundacionjala.sonarqube.api.ApexKeyword;
import org.fundacionjala.sonarqube.api.ApexPunctuator;
import org.fundacionjala.sonarqube.tree.Modifier;
import org.fundacionjala.sonarqube.tree.Tree;

import java.util.Map;

public class KindMaps {
    private final Map<ApexKeyword, Modifier> modifiers = Maps.newEnumMap(ApexKeyword.class);
    private final Map<ApexPunctuator, Tree.Kind> binaryOperators = Maps.newEnumMap(ApexPunctuator.class);

    public KindMaps() {
        modifiers.put(ApexKeyword.PUBLIC, Modifier.PUBLIC);
        modifiers.put(ApexKeyword.PROTECTED, Modifier.PROTECTED);
        modifiers.put(ApexKeyword.PRIVATE, Modifier.PRIVATE);
        modifiers.put(ApexKeyword.ABSTRACT, Modifier.ABSTRACT);
        modifiers.put(ApexKeyword.STATIC, Modifier.STATIC);
        modifiers.put(ApexKeyword.FINAL, Modifier.FINAL);
        modifiers.put(ApexKeyword.TRANSIENT, Modifier.TRANSIENT);


        binaryOperators.put(ApexPunctuator.STAR, Tree.Kind.MULTIPLY);
        binaryOperators.put(ApexPunctuator.DIV, Tree.Kind.DIVIDE);
        binaryOperators.put(ApexPunctuator.EQUAL, Tree.Kind.EQUAL_TO);
        binaryOperators.put(ApexPunctuator.NOTEQUAL, Tree.Kind.NOT_EQUAL_TO);
        binaryOperators.put(ApexPunctuator.AND, Tree.Kind.AND);
        binaryOperators.put(ApexPunctuator.ANDAND, Tree.Kind.CONDITIONAL_AND);
        binaryOperators.put(ApexPunctuator.OROR, Tree.Kind.CONDITIONAL_OR);
    }

    public Modifier getModifier(ApexKeyword keyword) {
        return Preconditions.checkNotNull(modifiers.get(keyword), "Mapping not found for modifier %s", keyword);
    }

    public Tree.Kind getBinaryOperator(ApexPunctuator punctuator) {
        return Preconditions.checkNotNull(binaryOperators.get(punctuator), "Mapping not found for binary operator %s", punctuator);
    }
}
