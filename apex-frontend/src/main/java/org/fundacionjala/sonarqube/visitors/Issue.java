package org.fundacionjala.sonarqube.visitors;

import org.fundacionjala.sonarqube.tree.Tree;

public class Issue {
    private Tree tree;
    private String message;

    public Issue (Tree tree, String message) {
        this.tree = tree;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Tree getTree() {
        return tree;
    }
}
