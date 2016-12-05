package org.fundacionjala.sonarqube;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.Rule;
import com.sonar.sslr.api.typed.ActionParser;
import org.fest.assertions.GenericAssert;
import org.fundacionjala.sonarqube.parser.ApexGrammar;
import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.fundacionjala.sonarqube.tree.ApexNodeBuilder;
import org.fundacionjala.sonarqube.tree.ApexTree;
import org.fundacionjala.sonarqube.tree.Tree;
import org.fundacionjala.sonarqube.tree.TreeFactory;
import org.fundacionjala.sonarqube.treeimplementation.FormalParametersListTreeImpl;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.tests.ParsingResultComparisonFailure;
import org.sonar.sslr.tests.RuleAssert;

public class Assertions {

    public static RuleAssert assertThat(Rule actual) {
        return new RuleAssert(actual);
    }

    public static ParserAssert assertThat(GrammarRuleKey rule) {
        return assertThat(ApexLexer.createGrammarBuilder(), rule);
    }

    public static ParserAssert assertThat(LexerlessGrammarBuilder b, GrammarRuleKey rule) {
        return new ParserAssert(new ActionParser<Tree>(
                Charsets.UTF_8,
                b,
                ApexGrammar.class,
                new TreeFactory(),
                new ApexNodeBuilder(),
                rule));
    }

    public static class ParserAssert extends GenericAssert<ParserAssert, ActionParser<Tree>> {

        public ParserAssert(ActionParser<Tree> actual) {
            super(ParserAssert.class, actual);
        }

        private void parseTillEof(String input) {
            ApexTree tree = (ApexTree) actual.parse(input);
            InternalSyntaxToken syntaxToken = (InternalSyntaxToken) tree.lastToken();
            //FIXME ugly hack to get closing parenthesis of formal parameter list
            if(tree instanceof FormalParametersListTreeImpl && ((FormalParametersListTreeImpl) tree).closeParenToken() != null) {
                syntaxToken = ((FormalParametersListTreeImpl) tree).closeParenToken();
            }
            if (syntaxToken == null || (!syntaxToken.isEOF() && (syntaxToken.column()+syntaxToken.text().length() != input.length()))) {
                if (syntaxToken == null)  {
                    throw new RecognitionException(0, "Did not match till EOF : Last syntax token cannot be found");
                }
                throw new RecognitionException(
                        0, "Did not match till EOF, but till line " + syntaxToken.line() + ": token \"" + syntaxToken.text() + "\"");
            }
        }

        public ParserAssert matches(String input) {
            isNotNull();
            Preconditions.checkArgument(!hasTrailingWhitespaces(input), "Trailing whitespaces in input are not supported");
            String expected = "Rule '" + getRuleName() + "' should match:\n" + input;
            try {
                parseTillEof(input);
            } catch (RecognitionException e) {
                String actual = e.getMessage();
                throw new ParsingResultComparisonFailure(expected, actual);
            }
            return this;
        }

        private static boolean hasTrailingWhitespaces(String input) {
            return input.endsWith(" ") || input.endsWith("\n") || input.endsWith("\r") || input.endsWith("\t");
        }

        public ParserAssert notMatches(String input) {
            isNotNull();
            try {
                parseTillEof(input);
            } catch (RecognitionException e) {
                // expected
                return this;
            }
            throw new AssertionError("Rule '" + getRuleName() + "' should not match:\n" + input);
        }

        private String getRuleName() {
            return actual.rootRule().toString();
        }

    }
}
