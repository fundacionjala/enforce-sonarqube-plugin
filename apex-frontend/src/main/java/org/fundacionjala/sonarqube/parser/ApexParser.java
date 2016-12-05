package org.fundacionjala.sonarqube.parser;

import com.sonar.sslr.api.typed.ActionParser;
import com.sonar.sslr.api.typed.NodeBuilder;
import org.fundacionjala.sonarqube.tree.ApexNodeBuilder;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.tree.TreeFactory;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import java.nio.charset.Charset;

public class ApexParser extends ActionParser<Tree> {

    private ApexParser(Charset charset, LexerlessGrammarBuilder b, Class grammarClass, Object treeFactory, NodeBuilder nodeBuilder, GrammarRuleKey rootRule) {
        super(charset, b, grammarClass, treeFactory, nodeBuilder, rootRule);
    }

    public static ActionParser<Tree> createParser(Charset charset) {
        return new ApexParser(
                charset,
                ApexLexer.createGrammarBuilder(),
                ApexGrammar.class,
                new TreeFactory(),
                new ApexNodeBuilder(),
                ApexLexer.COMPILATION_UNIT);
    }
}
