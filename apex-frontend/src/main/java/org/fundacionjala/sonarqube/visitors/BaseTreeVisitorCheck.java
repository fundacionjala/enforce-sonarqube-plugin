package org.fundacionjala.sonarqube.visitors;

import org.fundacionjala.sonarqube.tree.Tree;
import org.sonar.api.internal.google.common.collect.Lists;

import java.util.List;

public class BaseTreeVisitorCheck extends BaseTreeVisitor{

    private static List<Issue> issues = Lists.newArrayList();

    public void addIssue(Tree tree, String message) {
        issues.add(new Issue(tree, message));
    }
    public static List<Issue> getIssues() {
        return issues;
    }

}
