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
package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.junit.Test;
import org.junit.Before;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;

import static org.sonar.sslr.tests.Assertions.assertThat;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.APEX_GRAMMAR;

public class ApexGrammarTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(APEX_GRAMMAR);
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
                        + "public booleanN my_Variable = trueExpression;"
                        + "public booleanN MyMethod(){}"
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
                        + "        boolean CONST = b = t & r ^ a || j != null;\n"
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
}
