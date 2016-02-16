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

import org.sonar.api.rule.RuleStatus;
import org.sonar.api.rule.Severity;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;

/**
 *
 */
public class ApexlintRulesDefinition implements RulesDefinition {
//
//    public static final String REPOSITORY_NAME = "Apex";
//    public static final String REPOSITORY_KEY = REPOSITORY_NAME;
//
//    private static final String RULES_FILE = "/rules.xml";
//    private static final String SQALE_FILE = "/rules.xml";
//    private final RulesDefinitionXmlLoader xmlLoader;
//
//    public ApexlintRulesDefinition(RulesDefinitionXmlLoader xmlLoader) {
//        this.xmlLoader = xmlLoader;
//    }

    @Override
    public void define(RulesDefinition.Context context) {
        NewRepository repository = context.createRepository("apex-example", "apex").setName("Custom Apex Analyzer");

        // define a rule programmatically.
        NewRule x1Rule = repository.createRule("ExampleRule1")
                .setName("Stupid rule")
                .setHtmlDescription("Generate an issue on every line 1")
                // optional tags
                .setTags("style", "stupid")
                // optional status. Default value is READY.
                .setStatus(RuleStatus.BETA)
                // default severity when the rule is activated on a Quality profile. Default value is MAJOR.
                .setSeverity(Severity.MINOR);

        x1Rule
                .setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
                .setDebtRemediationFunction(x1Rule.debtRemediationFunctions().linearWithOffset("1h", "30min"));

        // don't forget to call done() to finalize the definition
        repository.done();
    }

//    protected static final String KEY = "apexlint";
//    protected static final String NAME = "ApexLint";
//
//    protected String rulesDefinitionFilePath() {
//        return "/rules.xml";
//    }
//
//    private void defineRulesForLanguage(Context context, String repositoryKey, String repositoryName, String languageKey) {
//        NewRepository repository = context.createRepository(repositoryKey, languageKey).setName(repositoryName);
//
//        InputStream rulesXml = this.getClass().getResourceAsStream(rulesDefinitionFilePath());
//        if (rulesXml != null) {
//            RulesDefinitionXmlLoader rulesLoader = new RulesDefinitionXmlLoader();
//            rulesLoader.load(repository, rulesXml, StandardCharsets.UTF_8.name());
//        }
//
//        System.out.println("=======> repository: " + repository.toString());
//        repository.done();
//    }
//
//    @Override
//    public void define(Context context) {
//        System.out.println("==========> Context: " + context.toString());
//        String repositoryKey = getRepositoryKeyForLanguage(Apex.KEY);
//        String repositoryName = getRepositoryNameForLanguage(Apex.KEY);
//        defineRulesForLanguage(context, repositoryKey, repositoryName, Apex.KEY);
//    }
//
//    public static String getRepositoryKeyForLanguage(String languageKey) {
//        return languageKey.toLowerCase() + "-" + KEY;
//    }
//
//    public static String getRepositoryNameForLanguage(String languageKey) {
//        return languageKey.toUpperCase() + " " + NAME;
//    }
}
