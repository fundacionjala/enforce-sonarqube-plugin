/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.APEX_GRAMMAR;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(APEX_GRAMMAR);
    }

    @Test
    public void correctRuleSimpleClass() {
        assertThat(parser)
                .matches("public class ClassName {"
                        + "}");
    }

    @Test
    public void correctRuleBasic() {
        assertThat(parser)
                .matches("public with sharing class MyClass {"
                        + "public bool MyMethod(){"
                        + "integer name=0;while(trueExpression){"
                        + "insert accout;"
                        + "}"
                        + "}"
                        + "}");
    }

    @Test
    public void correctRuleMoreImplements() {
        assertThat(parser)
                .matches("public with sharing class Class1 implements YourClass {"
                        + "public void MyMethod(integer myParameter){"
                        + "integer someNumber = myParameter;"
                        + "}"
                        + "}");
    }

    @Test
    public void correctRuleMoreExtends() {
        assertThat(parser)
                .matches("public with sharing class Class1 extends YourClass {"
                        + "public integer MyMethod(){"
                        + "integer someNumber = 0;"
                        + "}"
                        + "}");
    }

    @Test
    public void correctRuleBasicVariable() {
        assertThat(parser)
                .matches("public without sharing class MyClass {"
                        + "integer myVariable;"
                        + "}");
    }

    @Test
    public void correctRuleMoreImplementsVariableAndMethod() {
        assertThat(parser)
                .matches("public with sharing class Class1 implements YourClass {"
                        + "public boolean my_Variable = trueExpression;"
                        + "public boolean MyMethod(){}"
                        + "}");
    }

    @Test
    public void correctRuleMoreExtendsVariableAndMethod() {
        assertThat(parser)
                .matches("public with sharing class Class1 extends YourClass{"
                        + "private integer my_Variable = 10;"
                        + "public bool MyMethod(){}"
                        + "}");
    }

    @Test
    public void enumTest() {
        assertThat(parser)
                .matches("public enum SomeEnum {\n"
                        + "ENUM_VALUE, ANOTHER_ENUM_VALUE\n"
                        + "}"
                );
    }

    @Test
    public void complexClassTest() {
        assertThat(parser)
                .matches("public with sharing class SomeClass extends YourClass\n"
                        + "        implements SomeInterface, SomeOtherInterface {\n"
                        + "    {\n"
                        + "        string x = y = 3;\n"
                        + "        integer c = 0;\n"
                        + "        object thisThing = this;\n"
                        + "        {\n"
                        + "            integer z = x <= 5 ? 5 : 4 - v / 5 + x;\n"
                        + "        }\n"
                        + "    }\n"
                        + "\n"
                        + "    static {\n"
                        + "        Boolean CONSTANT = b = t & r ^ a || j != null;\n"
                        + "    }\n"
                        + "\n"
                        + "    protected abstract without sharing class subClass extends aClass {\n"
                        + "\n"
                        + "        protected list<integer> abstractMethod(list<integer> par1, integer par2) {\n"
                        + "            if (x == 0) {\n"
                        + "                par1.add(par2);\n"
                        + "            }\n"
                        + "            return par1;\n"
                        + "        }\n"
                        + "    }\n"
                        + "\n"
                        + "    public with sharing interface anInterface implements otherInterface {\n"
                        + "\n"
                        + "Integer count = 0;\n"
                        + "WebService String StagingFacilityType;\n"
                        + "public static String EscapeSingleQuotes(Iterator<String> iteratorToEscape){}\n"
                        + "String whereFiltersStmt = SecurityUtil.EscapeSingleQuotes(params.get('whereStmtFilters'));\n"
                        + "static testMethod void DateFilterValidationTemplate(){}"
                        + "        protected void aMethod(AType var1, OtherType var2, integer var3);\n"
                        + "    }\n"
                        + "\n"
                        + "    public enum someEnum {\n"
                        + "        AN_ENUM_VALUE,\n"
                        + "        ANOTHER_ONE,\n"
                        + "        AND_ANOTHER_ONE\n"
                        + "    }\n"
                        + "\n"
                        + "    private static final string FIELD = x < 5 ? 'first option' : secondOption;\n"
                        + "\n"
                        + "    public OfSomeType AProperty {protected get {\n"
                        + "        if(a instanceof b) {\n"
                        + "                return -++--+(a-b)++;\n"
                        + "        } else {\n"
                        + "                return u && (v ^ w & n || !(a != null));\n"
                        + "        }\n"
                        + "        }\n"
                        + "        private set;\n"
                        + "    }\n"
                        + "\n"
                        + "    public SomeClass () {\n"
                        + "        this.aVariable = super.thatVariable;\n"
                        + "        super.doSomething((aType)varToCast);\n"
                        + "        SomeStaticClass.doOtherThing(null, 0, anotherParam, AProperty);\n"
                        + "    }\n"
                        + "\n"
                        + "    public SomeClass (list<map<AKeyType, AValueType>> mapsList, integer aParam) {\n"
                        + "        while (!mapsList.isEmpty()) {\n"
                        + "                mapsList.remove(mapsList.get(aParam));\n"
                        + "        }\n"
                        + "    }\n"
                        + "\n"
                        + "    @someAnnotation\n"
                        + "    public global static void aMethodWithLoops(list<integer> collection) {\n"
                        + "        for(integer i = ((x%2 == 0)? 0 : 1); (i/5) <= 10; i++) {\n"
                        + "                doSomething(i);\n"
                        + "        }\n"
                        + "        for(integer item : collection) {\n"
                        + "                doSomeOtherThing(item);\n"
                        + "        }\n"
                        + "        map<Key, Valu> aMap = new map<Key, Valu>{0 => a, 1 =>b, 2=>(c<d ? c:d)};\n"
                        + "    }\n"
                        + "}"
                );
    }

    @Test
    public void complexExternalClassTest() throws IOException {
        String fileToString = readFileToString("src/test/resources/complex/ComplexClass.cls",
                StandardCharsets.UTF_8);
        assertThat(parser).matches(fileToString);
    }

    @Test
    public void complexExternalTestClassTest() throws IOException {
        String fileToString = readFileToString("src/test/resources/complex/ComplexTest.cls",
                StandardCharsets.UTF_8);
        assertThat(parser).matches(fileToString);
    }

    @Test
    public void complexExternalViatraClassTest() throws IOException {
        String fileToString = readFileToString("src/test/resources/complex/ComplexClassViatra.cls",
                StandardCharsets.UTF_8);
        assertThat(parser).matches(fileToString);
    }

    @Test
    public void recoveryDeclarationTest() {
        assertThat(parser)
                .matches("public with sharing class RecoveryClass  {\n"
                        + "    public class class InnerClass(){\n"
                        + "        doSomething();\n"
                        + "    }\n"
                        + "    private void someMthd(integer p1, p2)"
                        + "}");
    }

    @Test
    public void recoveryStatementTest() {
        assertThat(parser)
                .matches("public with sharing class RecoveryClass  {\n"
                        + "    public void someMethod(){\n"
                        + "        doSomething();\n"
                        + "        wrongMethod);\n"
                        + "        doOtherThing(x);\n"
                        + "    }\n"
                        + "}");
    }

    @Test
    public void recoveryTestFile() throws IOException {
        String fileToString = readFileToString("src/test/resources/complex/ComplexClassWithErrors.cls",
                StandardCharsets.UTF_8);
        assertThat(parser).matches(fileToString);
    }

    private String readFileToString(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
