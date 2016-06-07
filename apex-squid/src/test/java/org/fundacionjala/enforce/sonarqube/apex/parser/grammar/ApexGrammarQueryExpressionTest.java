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

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.QUERY_EXPRESSION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarQueryExpressionTest extends ApexRuleTest {

    @Before
    public void init() {
        setRootRule(QUERY_EXPRESSION);
    }
   
    /**
     * SELECT SENTENCE TESTS
     *
     */
    @Test
    public void positiveRules_SubQueryExpression() {
        assertThat(parser)
                .matches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM tab2 LIMIT 200) "
                        + "FROM table1 LIMIT 2000")
                .matches("SELECT field1,field2, (SELECT sub1 FROM tab2 LIMIT 2) "
                        + "FROM table1 LIMIT 2000");
    }

    @Test
    public void negativeRules_SubQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM LIMIT 200) "
                        + "FROM table1 LIMIT 2000")
                .notMatches("SELECT field1,field2, (SELECT FROM tab2 LIMIT 2) "
                        + "FROM table1 LIMIT 2000");
    }

    @Test
    public void positiveRules_CountQueryExpression() {
        assertThat(parser)
                .matches("SELECT COUNT(Id), COUNT(CampaignId) "
                        + "FROM table1 LIMIT 2000")
                .matches("SELECT COUNT() "
                        + "FROM table1 LIMIT 2000");
    }

    @Test
    public void negativeRules_CountQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT COUNT( "
                        + "FROM table1 LIMIT 2000")
                .notMatches("SELECT COUNT "
                        + "FROM table1 LIMIT 2000");
    }

    /**
     * FROM SENTENCE TESTS
     *
     */
    @Test
    public void positiveRules_AliasAssignementQueryExpression() {
        assertThat(parser)
                .matches("SELECT field1,field2 "
                        + "FROM table1 AS T1 LIMIT 100")
                .matches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM tab2 AS T2 LIMIT 2) "
                        + "FROM table1 AS T1 LIMIT 3000");
    }

    @Test
    public void negativeRules_AliasAssignementExpression() {
        assertThat(parser)
                .notMatches("SELECT sub1 FROM AS d LIMIT 200)")
                .notMatches("SELECT field1,field2, (SELECT FROM tab2 LIMIT 2) "
                        + "FROM table1AS LIMIT 2000")
                .notMatches("SELECT field1,field2 "
                        + "FROM table1 AS LIMIT 2000");
    }

    /**
     * WITH SENTENCE TESTS
     *
     */
    @Test
    public void positiveRules_SimpleWithQueryExpression() {
        assertThat(parser)
                .matches("SELECT field1,field2 "
                        + "FROM table1 AS T1 WITH A!=5 LIMIT 100");
    }

    @Test
    public void negativeRules_SimpleWithExpression() {
        assertThat(parser)
                .notMatches("SELECT sub1 FROM t1 WITH LIMIT 200)")
                .notMatches("SELECT field1,field2, (SELECT s FROM tab2 WHERE LIMIT 2) "
                        + "FROM table1 WITHcustomer=1 LIMIT 2000")
                .notMatches("SELECT field1,field2 "
                        + "FROM table1 WITH customer_id = 'test'AST1 LIMIT 2000");
    }

    /**
     * WHERE SENTENCE TESTS
     *
     */
    @Test
    public void positiveRules_SimpleWhereQueryExpression() {
        assertThat(parser)
                .matches("SELECT field1,field2 "
                        + "FROM table1 AS T1 WHERE customer_id = 1 LIMIT 100")
                .matches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM tab2 AS T2 WHERE id >= 1 LIMIT 2) "
                        + "FROM table1 AS T1 WHERE customer_id != 'test' LIMIT 3000");
    }

    @Test
    public void negativeRules_SimpleWhereExpression() {
        assertThat(parser)
                .notMatches("SELECT sub1 FROM AS LIMIT 200)")
                .notMatches("SELECT field1,field2, (SELECT s FROM tab2 WHERE LIMIT 2) "
                        + "FROM table1 WHEREcustomer=1 LIMIT 2000")
                .notMatches("SELECT field1,field2 "
                        + "FROM table1 WHERE customer_id = 'test'AST1 LIMIT 2000");
    }

    @Test
    public void positiveRules_ConditionalOperatorsWhereQueryExpression() {
        assertThat(parser)
                .matches("SELECT field1,field2 "
                        + "FROM table1 AS T1 WHERE customer_id = 1 LIMIT 100")
                .matches("SELECT field1,field2 "
                        + "FROM table1 AS T1 WHERE Name LIKE 'A%' LIMIT 100")
                .matches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM tab2 AS T2 WHERE id >= 1 LIMIT 2) "
                        + "FROM table1 AS T1 WHERE customer_id = 'test' LIMIT 3000");
    }

    @Test
    public void negativeRules_ConditionalOperatorWhereQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT sub1 FROM A WHEREcustomer=LIKE LIMIT 200)")
                .notMatches("SELECT field1,field2, (SELECT s FROM tab2 WHERE LIMIT 2) "
                        + "FROM table1 WHEREcustomer=1 LIMIT 2000")
                .notMatches("SELECT field1,field2 "
                        + "FROM table1 WHERE customer_id 'test' LIMIT 2000");
    }

    @Test
    public void positiveRules_ComplexWhereAndQueryExpression() {
        assertThat(parser)
                .matches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE customer_id = 1 AND id = 1 LIMIT 100")
                .matches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE Name LIKE 'A%' AND C = 5 LIMIT 100")
                .matches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM tab2 AS T2 WHERE id >= 1 LIMIT 2) "
                        + "FROM table1 AS t1 WHERE Name LIKE 'A%' AND C = 5 "
                        + "LIMIT 3000");
    }

    @Test
    public void negativeRules_ComplexWhereAndQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE customer_id = 1 ANDid = 1 LIMIT 100")
                .notMatches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE Name LIKE 'A%' ANDid = 'and' LIMIT 100")
                .notMatches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM tab2 AS T2 WHERE id >= 1 LIMIT 2) "
                        + "FROM table1 AS T1 "
                        + "WHERE customer_id = 'test' Name LIKE 'A%' "
                        + "AND ANDfield1 = 'Test' LIMIT 3000");
    }

    @Test
    public void positiveRules_ComplexWhereOrQueryExpression() {
        assertThat(parser)
                .matches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE customer_id = 1 OR id = 1 LIMIT 100")
                .matches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE Name LIKE 'A%' OR C = 5 LIMIT 100")
                .matches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM tab2 AS T2 WHERE id >= 1 LIMIT 2) "
                        + "FROM table1 AS t1 WHERE Name LIKE 'A%' OR C = 5 "
                        + "LIMIT 3000");
    }

    @Test
    public void negativeRules_ComplexWhereOrQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE customer_id = 1 ANDid = 1 LIMIT 100")
                .notMatches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE Name LIKE 'A%' ORid > 1 LIMIT 100")
                .notMatches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM tab2 AS T2 WHERE id >= 1 LIMIT 2) "
                        + "FROM table1 AS T1 "
                        + "WHERE customer_id = 'test' Name LIKE 'A%' "
                        + "OR id > 1 ANDfield1 = 'Test' LIMIT 3000");
    }

    @Test
    public void positiveRules_ComplexWhereInQueryExpression() {
        assertThat(parser)
                .matches("SELECT Id,Name "
                        + "FROM Account "
                        + "WHERE Name IN (SELECT ParentId "
                        + "FROM Account WHERE Name LIKE 'A%')");
    }

    @Test
    public void negativeRules_ComplexWhereInQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE customer_id = 1 NOTid LIKE 1 LIMIT 100")
                .notMatches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE Name LIKE 'A%' NOTid > 1 LIMIT 100")
                .notMatches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM tab2 AS T2 WHERE id >= 1 LIMIT 2) "
                        + "FROM table1 AS T1 "
                        + "WHERE customer_id = 'test' Name LIKE 'A%' "
                        + "OR id > 1 NOT = 'Test' LIMIT 3000");
    }

    @Test
    public void positiveRules_ComplexCombinedWhereQueryExpression() {
        assertThat(parser)
                .matches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE customer_id = 1 OR id = 1 AND Cid ='test' "
                        + "LIMIT 100")
                .matches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE Name LIKE 'A%' AND Cid ='test' OR C = 5 "
                        + "LIMIT 100")
                .matches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM tab2 AS T2 "
                        + "WHERE customer_id = 1 OR id = 1 AND Cid ='test' LIMIT 2) "
                        + "FROM table1 AS t1 "
                        + "WHERE Name LIKE 'A%' AND Cid ='test' OR C = 5 "
                        + "LIMIT 3000");
    }

    @Test
    public void negativeRules_ComplexCombinedWhereQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE customer_id = 1 ANDid = 1 LIMIT 100")
                .notMatches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE Name LIKE 'A%' ORid > 1 LIMIT 100")
                .notMatches("SELECT field1,field2, "
                        + "(SELECT sub1 FROM tab2 AS T2 WHERE id >= 1 LIMIT 2) "
                        + "FROM table1 AS T1 "
                        + "WHERE customer_id = 'test' Name LIKE 'A%' "
                        + "OR id > 1 ANDfield1 = 'Test' LIMIT 3000");
    }

    @Test
    public void positiveRules_FilterWhereQueryExpression() {
        assertThat(parser)
                .matches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE id includes (a4, b5) LIMIT 100")
                .matches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE id IN (Account) LIMIT 100")
                .matches("SELECT field1,field2,"
                        + "(SELECT sub1 FROM tab2 AS T2 WHERE id >= 1 LIMIT 2) "
                        + "FROM table1 AS t1 WHERE Name LIKE 'A%' OR Cid = 5 "
                        + "AND id includes (a4, b5) OR id excludes (c3)"
                        + "LIMIT 3000");
    }

    @Test
    public void negativeRules_FilterWhereQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHEREid includes (4,5) LIMIT 100")
                .notMatches("SELECT field1,field2 FROM table1 AS T1 "
                        + "WHERE id IN 'Account') LIMIT 100")
                .notMatches("SELECT field1,field2,"
                        + "(SELECT sub1 FROM tab2 AS T2 WHERE id >= 1 LIMIT 2) "
                        + "FROM table1 AS t1 WHERE NOT (Name LIKE 'A%' OR Cid = 5) "
                        + "AND id includes (4,5 OR id excludes (3)"
                        + "LIMIT 3000");
    }

    /**
     * ORDER BY SENTENCE TESTS
     *
     */
    @Test
    public void positiveRules_OrderByAscQueryExpression() {
        assertThat(parser)
                .matches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BY BillingPostalCode ASC LIMIT 1250");
    }

    @Test
    public void negativeRules_OrderByAscQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BYBillingPostalCodeASC LIMIT 1250");
    }

    @Test
    public void positiveRules_OrderByDescQueryExpression() {
        assertThat(parser)
                .matches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BY BillingPostalCode DESC LIMIT 1250");
    }

    @Test
    public void negativeRules_OrderByDescQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BYBillingPostalCodeDESC LIMIT 1250");
    }

    @Test
    public void positiveRules_OrderByNullsQueryExpression() {
        assertThat(parser)
                .matches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BY BillingPostalCode DESC NULLS LAST LIMIT 1250")
                .matches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BY BillingPostalCode ASC NULLS FIRST LIMIT 1250");
    }

    @Test
    public void negativeRules_OrderByNullsQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BY BillingPostalCode DSC NULLS LIMIT 1250")
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BY BillingPostalCode DES NULLS FIRST LIMIT 1250");
    }

    /**
     * LIMIT BY SENTENCE TESTS
     *
     */
    @Test
    public void positiveRules_OffSetQueryExpression() {
        assertThat(parser)
                .matches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BY BillingPostalCode ASC "
                        + "LIMIT 1250 OFFSET 20");
    }

    @Test
    public void negativeRules_OffSetQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BY BillingPostalCode ASC "
                        + "LIMIT OFFSET 20")
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BY BillingPostalCode ASC "
                        + "LIMIT 5 OFFSET20")
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "ORDER BY BillingPostalCode ASC "
                        + "LIMIT 15 OFFSET");
    }

    /**
     * GROUP BY SENTENCE TESTS
     *
     */
    @Test
    public void positiveRules_GroupByQueryExpression() {
        assertThat(parser)
                .matches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "GROUP BY BillingPostalCode LIMIT 1250");
    }

    @Test
    public void negativeRules_GroupByQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "GROUP BYBillingPostalCodeLIMIT 1250");
    }

    @Test
    public void positiveRules_GroupByRollUpQueryExpression() {
        assertThat(parser)
                .matches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "GROUP BY ROLLUP (BillingPostalCode) LIMIT 1250")
                .matches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "GROUP BY ROLLUP (BillingPostalCode,Id) LIMIT 1250");
    }

    @Test
    public void negativeRules_GroupByRollUpQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "GROUP BY ROLLUP (BillingPostalCode LIMIT 1250")
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "GROUP BY ROLLUPBillingPostalCode) LIMIT 1250");
    }

    @Test
    public void positiveRules_GroupByCubeQueryExpression() {
        assertThat(parser)
                .matches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "GROUP BY CUBE (BillingPostalCode) LIMIT 1250")
                .matches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "GROUP BY CUBE (BillingPostalCode,Id) LIMIT 1250");
    }

    @Test
    public void negativeRules_GroupByCubeQueryExpression() {
        assertThat(parser)
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "GROUP BY CUBE (BillingPostalCode LIMIT 1250")
                .notMatches("SELECT Name "
                        + "FROM Account WHERE industry = 'media' "
                        + "GROUP BY CUBEBillingPostalCode) LIMIT 1250");
    }
}
