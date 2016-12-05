package org.fundacionjala.sonarqube.MockChecks;

import org.fundacionjala.sonarqube.semantic.SymbolModel;
import org.fundacionjala.sonarqube.visitors.BaseTreeVisitorCheck;

import java.util.Map;

//this interface was designed for those checks that had to do with relations between classes such as method invocations
// between classes.
public abstract class SpecialCheck extends BaseTreeVisitorCheck{

    public abstract void setUnbindedSymbols(Map<String, SymbolModel> symbols);

}
