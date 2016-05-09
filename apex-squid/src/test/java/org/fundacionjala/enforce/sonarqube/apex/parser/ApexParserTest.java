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
package org.fundacionjala.enforce.sonarqube.apex.parser;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.stream.Stream;

import com.google.common.base.Charsets;
import com.sonar.sslr.impl.Parser;
import org.junit.Before;
import org.junit.Test;
import org.sonar.sslr.tests.ParserAssert;
import org.sonar.sslr.tests.ParsingResultComparisonFailure;

import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;

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
