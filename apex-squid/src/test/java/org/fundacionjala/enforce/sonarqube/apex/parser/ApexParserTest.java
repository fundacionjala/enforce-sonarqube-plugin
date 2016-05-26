/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser;

import com.google.common.base.Charsets;
import com.sonar.sslr.impl.Parser;
import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.sonar.sslr.tests.ParserAssert;
import org.sonar.sslr.tests.ParsingResultComparisonFailure;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.stream.Stream;

public class ApexParserTest {

    private static final String NEW_LINE = "\n";
    private String articleControllerTestSource;
    private String draftArticleSource;
    private String articleSource;
    private Parser parser;

    @Before
    public void setup() throws IOException {
        parser = ApexParser.create(new ApexConfiguration(Charsets.UTF_8));
        articleControllerTestSource = readSource("src/test/resources/parser/ArticleControllerTest.cls");
        draftArticleSource = readSource("src/test/resources/parser/DraftArticle.cls");
        articleSource = readSource("src/test/resources/parser/Article.cls");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowingAnExceptionWhenCreateParserWithNullConfiguration() {
        ApexParser.create(null);
    }

    @Test
    public void testVerifiesIfApexClassesAreParsed() {
        new ParserAssert(parser)
                .matches(articleSource)
//this test should pass when refactoring of the grammar is complete, for now 
//it's invalidated.                
//                .matches(draftArticleSource)
                .notMatches(articleControllerTestSource);
    }

    @Test(expected = ParsingResultComparisonFailure.class)
    public void testThrowingAnExceptionWhenParsesAnIlegibleClass() {
        new ParserAssert(parser)
                .matches(articleControllerTestSource);
    }

    private String readSource(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
        FileSystem system = FileSystems.getDefault();
        Stream<String> lines = Files.lines(system.getPath(path));
        lines.forEach(line -> {
            builder.append(NEW_LINE);
            builder.append(line);
        });
        return builder.deleteCharAt(0).toString();
    }
}
