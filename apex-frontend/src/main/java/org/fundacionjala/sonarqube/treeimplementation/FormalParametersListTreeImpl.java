package org.fundacionjala.sonarqube.treeimplementation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.fundacionjala.sonarqube.InternalSyntaxToken;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.sonar.sslr.grammar.GrammarRuleKey;

import java.util.List;

public class FormalParametersListTreeImpl extends ListTreeImpl<VariableTreeImpl>{

    private InternalSyntaxToken openParenToken;
    private InternalSyntaxToken closeParenToken;

    public FormalParametersListTreeImpl(InternalSyntaxToken openParenToken, InternalSyntaxToken closeParenToken) {
        super(ApexLexer.FORMAL_PARAMETERS, ImmutableList.<VariableTreeImpl>of());

        this.openParenToken = openParenToken;
        this.closeParenToken = closeParenToken;
    }

    public FormalParametersListTreeImpl(VariableTreeImpl variable) {
        super(ApexLexer.FORMAL_PARAMETERS, Lists.newArrayList(variable));
    }

    public FormalParametersListTreeImpl complete(InternalSyntaxToken openParenToken, InternalSyntaxToken closeParenToken) {
        this.openParenToken = openParenToken;
        this.closeParenToken = closeParenToken;
        return this;
    }

    public InternalSyntaxToken openParenToken() {
        return openParenToken;
    }

    public InternalSyntaxToken closeParenToken() {
        return closeParenToken;
    }

}
