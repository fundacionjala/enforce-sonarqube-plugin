/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex;

import com.google.common.base.Charsets;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import org.fundacionjala.enforce.sonarqube.apex.api.ApexMetric;
import org.fundacionjala.enforce.sonarqube.apex.parser.ApexParser;
import org.sonar.squidbridge.*;
import org.sonar.squidbridge.api.*;
import org.sonar.squidbridge.indexer.QueryByType;
import org.sonar.squidbridge.metrics.*;

import java.io.File;
import java.util.Collection;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.*;

/**
 * Utility class in order to scan a file and generate {@link SourceFile}
 */
public class ApexAstScanner {

    /**
     * Stores an error message when there is more than one sourceFile.
     */
    private static final String ONE_SOURCE_FILE
            = SquidBundle.getStringFromBundle("scanner.messages.onefile");

    /**
     * Stores an error message when not found a file.
     */
    private static final String FILE_NOT_FOUND = SquidBundle.getStringFromBundle("scanner.messages.notfound");

    /**
     * Stores a project name.
     */
    private static final String PROJECT_NAME = SquidBundle.getStringFromBundle("scanner.projectName");

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
            IF_STATEMENT,
            RETURN_STATEMENT
        };
        builder.withSquidAstVisitor(ComplexityVisitor.<Grammar>builder()
                .setMetricDef(ApexMetric.COMPLEXITY)
                .subscribeTo(complexityAstNodeType)
                .build());
        builder.withSquidAstVisitor(CommentsVisitor.<Grammar>builder()
                .withCommentMetric(ApexMetric.COMMENT_LINES)
                .withNoSonar(Boolean.TRUE)
                .withIgnoreHeaderComment(config.getIgnoreHeaderComments())
                .build());
        builder.withSquidAstVisitor(CounterVisitor.<Grammar>builder()
                .setMetricDef(ApexMetric.STATEMENTS)
                .subscribeTo(STATEMENT)
                .build());
    }

    /**
     * Sets the default visitors to mapping a method.
     *
     * @param builder scanner builder.
     */
    private static void setMethodAnalyser(AstScanner.Builder<Grammar> builder) {
        builder.withSquidAstVisitor(new SourceCodeBuilderVisitor<>(
                buildCallback(METHOD_IDENTIFIER, IS_CLASS),
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
                buildCallback(COMMON_IDENTIFIER, !IS_CLASS),
                CLASS_OR_INTERFACE_DECLARATION));

        builder.withSquidAstVisitor(CounterVisitor.<Grammar>builder()
                .setMetricDef(ApexMetric.CLASSES)
                .subscribeTo(CLASS_OR_INTERFACE_DECLARATION)
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
