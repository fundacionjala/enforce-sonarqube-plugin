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
package org.fundacionjala.enforce.sonarqube.apex.checks;

import com.google.common.base.Charsets;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.impl.Parser;
import java.io.File;
import static org.fundacionjala.enforce.sonarqube.apex.ApexAstScanner.scanFile;
import org.fundacionjala.enforce.sonarqube.apex.ApexConfiguration;
import org.fundacionjala.enforce.sonarqube.apex.parser.ApexParser;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class DmlCheckInWhileTest {

    private DmlCheckInWhile dmlCheckInWhile;
    private SourceFile sourceFile;

    @Test
    public void testIncorrectDMLDeclaration() throws Exception {
        dmlCheckInWhile = new DmlCheckInWhile();
        Parser p = ApexParser.create(new ApexConfiguration(Charsets.UTF_8));
        AstNode a = p.parse(new File("src/test/resources/checks/clazzCorrect.cls"));
        ajaja(a);
        sourceFile = scanFile(new File("src/test/resources/checks/clazzCorrect.cls"), dmlCheckInWhile);
        CheckMessagesVerifier.verify(sourceFile.getCheckMessages()).
                next().atLine(4).withMessage(
                "The DML statement \"insert\",can not be inside a while loop");
    }

    private void ajaja(AstNode astNode) {
        System.out.println("Entro");
        System.out.println(astNode.getName());
        for (AstNode n : astNode.getChildren()) {
            ajaja(n);
        }
    }

}
