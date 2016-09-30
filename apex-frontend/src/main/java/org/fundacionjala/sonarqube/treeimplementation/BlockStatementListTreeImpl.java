package org.fundacionjala.sonarqube.treeimplementation;

import org.fundacionjala.sonarqube.parser.ApexLexer;
import org.fundacionjala.sonarqube.tree.StatementTree;

import java.util.List;

public class BlockStatementListTreeImpl extends ListTreeImpl<StatementTree>{

    public BlockStatementListTreeImpl(List<? extends StatementTree> statements) {
        super(ApexLexer.BLOCK_STATEMENTS, (List<StatementTree>) statements);
    }
}
