package org.fundacionjala.sonarqube.semantic;

import org.fundacionjala.sonarqube.tree.IdentifierTree;
import org.sonar.api.batch.fs.InputFile;

import javax.annotation.Nullable;

public class Usage {
    public enum Kind {
        DECLARATION,
        DECLARATION_WRITE,
        // parameters in function signature
        LEXICAL_DECLARATION,
        WRITE,
        READ,
        READ_WRITE,
        METHOD_INVOCATION,
        EXTERNAL_INVOCATION;
    }

    private Kind kind;
    private IdentifierTree identifierTree;
    private @Nullable InputFile referencedFile;

    /**
     * @param identifierTree - this tree contains only symbol name identifier (we need it for symbol highlighting)
     * @param kind           - kind of usage
     */
    private Usage(IdentifierTree identifierTree, Kind kind, @Nullable InputFile otherFile) {
        this.kind = kind;
        this.identifierTree = identifierTree;
        this.referencedFile = otherFile;
    }

    public Symbol symbol() {
        return identifierTree.symbol();
    }

    public Kind kind() {
        return kind;
    }

    public IdentifierTree identifierTree() {
        return identifierTree;
    }

    public static Usage create(IdentifierTree symbolTree, Kind kind) {
        return new Usage(symbolTree, kind, null);
    }

    public static Usage create(IdentifierTree symbolTree, Kind kind, InputFile inputFile) {
        return new Usage(symbolTree, kind, inputFile);
    }

    public boolean isExternalInvocation() {
        return kind == Kind.EXTERNAL_INVOCATION;
    }
    public boolean isDeclaration() {
        return kind == Kind.DECLARATION_WRITE || kind == Kind.DECLARATION;
    }

    public boolean isWrite() {
        return kind == Kind.DECLARATION_WRITE || kind == Kind.WRITE || kind == Kind.READ_WRITE;
    }
}
