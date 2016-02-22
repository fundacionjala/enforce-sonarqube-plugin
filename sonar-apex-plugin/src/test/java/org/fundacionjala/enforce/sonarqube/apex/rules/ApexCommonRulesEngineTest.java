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
package org.fundacionjala.enforce.sonarqube.apex.rules;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.sonar.squidbridge.commonrules.api.CommonRulesRepository;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApexCommonRulesEngineTest {

    private ApexCommonRulesEngine engine;

    @Before
    public void setup() {
        engine = new ApexCommonRulesEngine();
    }

    @Test
    public void testShouldProvideExpectedExtensions() {
        List<CommonRulesRepository> repositories = engine.provide();
        assertThat(repositories.size(), is(1));
    }

    @Test
    public void testEnableCommonRules() {
        CommonRulesRepository repository = engine.newRepository();
        Set<String> ruleKeys = repository.enabledRuleKeys();
        assertThat(ruleKeys.size(), is(4));
        assertThat(ruleKeys, hasItem("InsufficientCommentDensity"));
    }
}
