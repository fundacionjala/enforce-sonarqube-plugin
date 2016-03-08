/*
 * The MIT License
 *
 * Copyright 2016 Fundacion Jala.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.fundacionjala.enforce.sonarqube.apex;

import java.io.File;
import java.util.Collection;

import com.google.common.base.Charsets;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.CommentAnalyser;
import org.sonar.squidbridge.SourceCodeBuilderCallback;
import org.sonar.squidbridge.SourceCodeBuilderVisitor;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.SquidAstVisitorContextImpl;
import org.sonar.squidbridge.api.SourceClass;
import org.sonar.squidbridge.api.SourceCode;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.api.SourceFunction;
import org.sonar.squidbridge.api.SourceProject;
import org.sonar.squidbridge.indexer.QueryByType;
import org.sonar.squidbridge.metrics.CommentsVisitor;
import org.sonar.squidbridge.metrics.ComplexityVisitor;
import org.sonar.squidbridge.metrics.CounterVisitor;
import org.sonar.squidbridge.metrics.LinesOfCodeVisitor;
import org.sonar.squidbridge.metrics.LinesVisitor;

import org.fundacionjala.enforce.sonarqube.apex.api.ApexMetric;
import org.fundacionjala.enforce.sonarqube.apex.parser.ApexParser;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.CLASS_NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.DML_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.METHOD_NAME;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.FOR_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.METHOD_DECLARATION;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.RETURN_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.STATEMENT_IF;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.TERMINAL_STATEMENT;
import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.RuleKey.WHILE_STATEMENT;

/**
 * Utility class in order to scan a file and generate {@link SourceFile}
 */
public class ApexAstScanner {

    /**
     * Stores an error message when there is more than one sourceFile.
     */
    private static final String ONE_SOURCE_FILE
            = "Only one SourceFile was expected whereas %d has been returned.";

    /**
     * Stores an error message when not found a file.
     */
    private static final String FILE_NOT_FOUND = "File '%s' not found.";

    /**
     * Stores a project name.
     */
    private static final String PROJECT_NAME = "Apex Project";

    /**
     * Stores a key pattern.
     */
    private static final String KEY_PATTERN = "%s:%d";

    /**
     * Stores a value to identifier a class.
     */
    private static final boolean IS_CLASS = true;

    /**
     * Default constructor.
     */
    private ApexAstScanner() {
    }

    /**
     * Returns a scanner from configuration and visitors.
     *
     * @param config apex configuration.
     * @param visitors list of visitors.
     * @return a scanner.
     */
    public static AstScanner<Grammar> create(ApexConfiguration config, SquidAstVisitor<Grammar>... visitors) {
        final SourceProject sourceProject = new SourceProject(PROJECT_NAME);
        final SquidAstVisitorContextImpl<Grammar> context = new SquidAstVisitorContextImpl<>(sourceProject);
        final Parser<Grammar> parser = ApexParser.create(config);

        AstScanner.Builder<Grammar> builder = AstScanner.<Grammar>builder(context).setBaseParser(parser);
        builder.withMetrics(ApexMetric.values());
        builder.setFilesMetric(ApexMetric.FILES);

        setCommentAnalyser(builder);
        setClassesAnalyser(builder);
        setMethodAnalyser(builder);
        setMetrics(config, builder);

        for (SquidAstVisitor<Grammar> visitor : visitors) {
            builder.withSquidAstVisitor(visitor);
        }
        return builder.build();
    }

    /**
     * Returns a source file from file and visitors.
     *
     * @param file source file.
     * @param visitors list of visitors.
     * @return a source.
     * @exception IllegalArgumentException when file is not found.
     * @exception IllegalStateException when there is more than one sourceFile.
     */
    public static SourceFile scanFile(File file, SquidAstVisitor<Grammar>... visitors) {
        if (!file.isFile()) {
            throw new IllegalArgumentException(String.format(FILE_NOT_FOUND, file));
        }
        AstScanner<Grammar> scanner = create(new ApexConfiguration(Charsets.UTF_8), visitors);
        scanner.scanFile(file);
        Collection<SourceCode> sources = scanner.getIndex().search(new QueryByType(SourceFile.class));
        if (sources.size() != 1) {
            throw new IllegalStateException(String.format(ONE_SOURCE_FILE, sources.size()));
        }
        return (SourceFile) sources.iterator().next();
    }

    /**
     * Sets the default apex metrics in {@link AstScanner}.
     *
     * @param config apex configuration.
     * @param builder scanner builder.
     */
    private static void setMetrics(ApexConfiguration config, AstScanner.Builder<Grammar> builder) {
        builder.withSquidAstVisitor(new LinesVisitor<>(ApexMetric.LINES));
        builder.withSquidAstVisitor(new LinesOfCodeVisitor<>(ApexMetric.LINES_OF_CODE));
        AstNodeType[] complexityAstNodeType = new AstNodeType[]{
            METHOD_DECLARATION,
            WHILE_STATEMENT,
            FOR_STATEMENT,
            STATEMENT_IF,
            RETURN_STATEMENT,
            DML_STATEMENT
        };
        builder.withSquidAstVisitor(ComplexityVisitor.<Grammar>builder()
                .setMetricDef(ApexMetric.COMPLEXITY)
                .subscribeTo(complexityAstNodeType)
                .build());
        builder.withSquidAstVisitor(CommentsVisitor.<Grammar>builder().withCommentMetric(ApexMetric.COMMENT_LINES)
                .withNoSonar(true)
                .withIgnoreHeaderComment(config.getIgnoreHeaderComments())
                .build());
        builder.withSquidAstVisitor(CounterVisitor.<Grammar>builder()
                .setMetricDef(ApexMetric.STATEMENTS)
                .subscribeTo(TERMINAL_STATEMENT)
                .build());
    }

    /**
     * Sets the default visitors to mapping a method.
     *
     * @param builder scanner builder.
     */
    private static void setMethodAnalyser(AstScanner.Builder<Grammar> builder) {
        builder.withSquidAstVisitor(new SourceCodeBuilderVisitor<>(
                buildCallback(METHOD_NAME, IS_CLASS),
                METHOD_DECLARATION));

        builder.withSquidAstVisitor(CounterVisitor.<Grammar>builder()
                .setMetricDef(ApexMetric.METHODS)
                .subscribeTo(METHOD_DECLARATION)
                .build());
    }

    /**
     * Sets the default visitors to mapping a class.
     *
     * @param builder scanner builder.
     */
    private static void setClassesAnalyser(AstScanner.Builder<Grammar> builder) {
        builder.withSquidAstVisitor(new SourceCodeBuilderVisitor<>(
                buildCallback(CLASS_NAME, !IS_CLASS),
                CLASS_DECLARATION));

        builder.withSquidAstVisitor(CounterVisitor.<Grammar>builder()
                .setMetricDef(ApexMetric.CLASSES)
                .subscribeTo(CLASS_DECLARATION)
                .build());
    }

    /**
     * Sets the default visitors to mapping comments.
     *
     * @param builder scanner builder.
     */
    private static void setCommentAnalyser(AstScanner.Builder<Grammar> builder) {
        builder.setCommentAnalyser(new CommentAnalyser() {
            @Override
            public boolean isBlank(String line) {
                for (int i = 0; i < line.length(); i++) {
                    if (Character.isLetterOrDigit(line.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String getContents(String comment) {
                return comment.substring(comment.indexOf('/'));
            }
        });
    }

    /**
     * Builds and returns a source code builder callback.
     *
     * @param nodeName node type to identify a name.
     * @param isClass define a SourceClass or SourceFunction.
     * @return the builder callback.
     */
    private static SourceCodeBuilderCallback buildCallback(AstNodeType nodeName, boolean isClass) {
        return (SourceCode sourceCode, AstNode astNode) -> {
            String key = generateKey(astNode, nodeName);
            sourceCode = isClass ? new SourceClass(key) : new SourceFunction(key);
            sourceCode.setStartAtLine(astNode.getTokenLine());
            return sourceCode;
        };
    }

    /**
     * Generates and returns the unique key for a node.
     *
     * @param node node type to identify a name
     * @param nodeName current node to be analyzed.
     * @return the key.
     */
    private static String generateKey(AstNode node, AstNodeType nodeName) {
        String name = node.getFirstDescendant(nodeName).getTokenValue();
        int line = node.getToken().getLine();
        return String.format(KEY_PATTERN, name, line);
    }
}
