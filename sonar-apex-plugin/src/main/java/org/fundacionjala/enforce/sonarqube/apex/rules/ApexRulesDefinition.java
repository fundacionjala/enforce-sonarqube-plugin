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

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.fundacionjala.enforce.sonarqube.apex.Apex;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;

/**
 *
 */
public class ApexRulesDefinition implements RulesDefinition {

    protected static final String KEY = "apexlint";
    protected static final String NAME = "apexLint";

    protected String rulesDefinitionFilePath() {
        return "/rules.xml";
    }

    private void defineRulesForLanguage(Context context, String repositoryKey, String repositoryName, String languageKey) {
        NewRepository repository = context.createRepository(repositoryKey, languageKey).setName(repositoryName);

        InputStream rulesXml = this.getClass().getResourceAsStream(rulesDefinitionFilePath());
        if (rulesXml != null) {
            RulesDefinitionXmlLoader rulesLoader = new RulesDefinitionXmlLoader();
            rulesLoader.load(repository, rulesXml, StandardCharsets.UTF_8.name());
        }

        repository.done();
    }

    @Override
    public void define(Context context) {
        String repositoryKey = getRepositoryKeyForLanguage(Apex.KEY);
        String repositoryName = getRepositoryNameForLanguage(Apex.KEY);
        defineRulesForLanguage(context, repositoryKey, repositoryName, Apex.KEY);
    }

    public static String getRepositoryKeyForLanguage(String languageKey) {
        return languageKey.toLowerCase() + "-" + KEY;
    }

    public static String getRepositoryNameForLanguage(String languageKey) {
        return languageKey.toUpperCase() + " " + NAME;
    }

}
