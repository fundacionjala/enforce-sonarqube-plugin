/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.rules;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;
import org.fundacionjala.enforce.sonarqube.apex.Apex;
import org.fundacionjala.enforce.sonarqube.apex.checks.CheckList;
import org.sonar.api.rule.RuleStatus;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import org.sonar.api.utils.AnnotationUtils;
import org.sonar.squidbridge.rules.ExternalDescriptionLoader;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import com.google.common.io.Resources;
import com.google.gson.Gson;

/**
 * Defines some coding rules of the same repository.
 */
public class ApexRulesDefinition implements RulesDefinition {

    private static final String RESOURCE_BASE_PATH = "/org/sonar/l10n/apex/rules/apex";
    private final Gson gson = new Gson();

    /**
     * Loads apex custom rules in the repository.
     *
     * @param context builder.
     */
    @Override
    public void define(Context context) {
        NewRepository repository = context
                .createRepository(CheckList.REPOSITORY_KEY, Apex.KEY)
                .setName(CheckList.REPOSITORY_NAME);
        List<Class> checks = CheckList.getChecks();
        new RulesDefinitionAnnotationLoader().load(repository, Iterables.toArray(checks, Class.class));
        checks.stream().forEach((ruleClass) -> {
            newRule(ruleClass, repository);
        });
        repository.done();
    }

    /**
     * Loads HTML descriptions and meta data from JSON file to checks.
     *
     * @param ruleClass Class type of given rule.
     * @param repository current repository.
     */
    @VisibleForTesting
    protected void newRule(Class<?> ruleClass, NewRepository repository) {
        org.sonar.check.Rule ruleAnnotation = AnnotationUtils.getAnnotation(ruleClass, org.sonar.check.Rule.class);
        if (ruleAnnotation == null) {
            throw new IllegalArgumentException(
                    String.format("No Rule annotation was found on %s", ruleClass));
        }
        String ruleKey = ruleAnnotation.key();
        if (StringUtils.isEmpty(ruleKey)) {
            throw new IllegalArgumentException(
                    String.format("No key is defined in Rule annotation of %s", ruleClass));
        }
        NewRule rule = repository.rule(ruleKey);
        if (rule == null) {
            throw new IllegalStateException(
                    String.format("No rule was created for %s in %s", ruleClass, repository.key()));
        }
        addHtmlDescription(rule, rule.key());
        addMetadata(rule, rule.key());
    }

    /**
     * Adds checks necessary information from JSON file.
     *
     * @param rule the new rule to add the information.
     * @param metadataKey rule key.
     */
    private void addMetadata(NewRule rule, String metadataKey) {
        URL resource = ExternalDescriptionLoader.class.getResource(String.format("%s/%s.json", RESOURCE_BASE_PATH, metadataKey));
        if (resource != null) {
            RuleMetatada metadata = gson.fromJson(readResource(resource), RuleMetatada.class);
            rule.setName(metadata.title);
            rule.setSeverity(metadata.defaultSeverity.toUpperCase());
            rule.addTags(metadata.tags);
            rule.setStatus(RuleStatus.valueOf(metadata.status.toUpperCase()));
            rule.setDebtSubCharacteristic(metadata.sqaleSubCharacteristic);
            if (metadata.remediation != null) {
                rule.setDebtRemediationFunction(metadata.remediation.remediationFunction(rule.debtRemediationFunctions()));
                rule.setEffortToFixDescription(metadata.remediation.linearDesc);
            }
        }
    }

    /**
     * Adds the HTML description for the new rule given the rule and its key.
     *
     * @param rule the new rule to add HTML description.
     * @param metadataKey new rule key.
     */
    private static void addHtmlDescription(NewRule rule, String metadataKey) {
        URL resource = ApexRulesDefinition.class.getResource(String.format("%s/%s.html", RESOURCE_BASE_PATH, metadataKey));
        if (resource != null) {
            rule.setHtmlDescription(readResource(resource));
        }
    }

    /**
     * Reads the given resource if it can be, otherwise throws an exception
     *
     * @param resource to read from
     * @return a String value which results of reading resource.
     */
    private static String readResource(URL resource) {
        try {
            return Resources.toString(resource, Charsets.UTF_8);
        } catch (IOException ioException) {
            throw new IllegalStateException(String.format("Failed to read: %s", resource), ioException);
        }
    }

    /**
     * Checks Json file template.
     */
    private static class RuleMetatada {

        String title;
        String status;
        @Nullable
        Remediation remediation;

        String sqaleSubCharacteristic;
        String[] tags;
        String defaultSeverity;
    }
}
