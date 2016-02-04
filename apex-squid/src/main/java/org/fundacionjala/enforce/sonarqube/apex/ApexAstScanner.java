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

import com.sonar.sslr.api.Grammar;
import java.io.File;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.api.SourceFile;

/**
 * Utility class for scans a file and generate {@link SourceFile}
 */
public class ApexAstScanner {

    /**
     * Default constructor.
     */
    private ApexAstScanner() {
    }

    /**
     * Returns a scanner from configuration and visitors.
     *
     * @param conf apex configuration.
     * @param visitors list of visitors.
     * @return a scanner.
     */
    public static AstScanner<Grammar> create(ApexConfiguration conf, SquidAstVisitor<Grammar>... visitors) {
        return null;
    }

    /**
     * Returns a source file from file and visitors.
     * @param file
     * @param visitors
     * @return a source.
     */
    public static SourceFile scanFile(File file, SquidAstVisitor<Grammar>... visitors) {
        return null;
    }
}
