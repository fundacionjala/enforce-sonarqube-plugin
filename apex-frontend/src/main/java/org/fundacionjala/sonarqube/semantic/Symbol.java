package org.fundacionjala.sonarqube.semantic;

import org.fundacionjala.sonarqube.treeimplementation.IdentifierTreeImpl;
import org.fundacionjala.sonarqube.visitors.Scope;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Symbol {

    public enum Kind {
        METHOD("method"),
        PARAMETER("parameter"),
        CLASS("class"),
        METHOD_INVOCATION("method_invocation"),
        UNKNOWN("unknown");

        private final String value;

        Kind(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    private final String name;

    private Kind kind;
    private Scope scope;
    private List<Usage> usages = new LinkedList<>();

    public Symbol(String name, Kind kind, @Nullable Scope scope) {
        this.name = name;
        this.kind = kind;
        this.scope = scope;
    }

    public void addUsage(Usage usage) {
        usages.add(usage);
        ((IdentifierTreeImpl) usage.identifierTree()).setSymbol(this);

    }

    public void addUsageAtFirst(Usage usage) {
        ((LinkedList)usages).addFirst(usage);
        ((IdentifierTreeImpl) usage.identifierTree()).setSymbol(this);
    }

    public Collection<Usage> usages() {
        return Collections.unmodifiableList(usages);
    }

    public Scope scope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String name() {
        return name;
    }

    public boolean is(Symbol.Kind kind) {
        return kind.equals(this.kind);
    }

    public Kind kind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else {
            if (o instanceof String) {
                return o.equals(this.name);
            }
        }
        return false;
    }

}
