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

import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.MethodNameCheck;
import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.DeprecatedMethodCheck;
import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.TestMethodCheck;
import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.DmlInForCheck;
import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.AssertMethodCheck;
import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.DmlInConstructorCheck;
import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.DmlInWhileCheck;
import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.LineLengthCheck;
import org.fundacionjala.enforce.sonarqube.apex.checks.unofficial.ClassNameCheck;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.fundacionjala.enforce.sonarqube.apex.checks.testrelated.TestClassCheck;

/**
 * Builds a list of custom checks.
 */
public class CheckList {

    /**
     * Stores the sonarqube profile name.
     */
    public static final String SONAR_WAY_PROFILE = "Sonar way";

    /**
     * Stores the sonarqube repository name.
     */
    public static final String REPOSITORY_NAME = "SonarQube";

    /**
     * Stores the Apex's repository key.
     */
    public static final String REPOSITORY_KEY = "apex";

    /**
     * Default constructor.
     */
    private CheckList() {
    }

    /**
     * Builds and returns the custom checks to create Apex's rules.
     *
     * @return the list of the checks.
     */
    public static List<Class> getChecks() {
        return ImmutableList.<Class>of(
                AssertMethodCheck.class,
                ClassNameCheck.class,
                DeprecatedMethodCheck.class,
                DmlInConstructorCheck.class,
                DmlInForCheck.class,
                DmlInWhileCheck.class,
                LineLengthCheck.class,
                MethodNameCheck.class,
                TestMethodCheck.class,
                TestClassCheck.class);
    }
}
