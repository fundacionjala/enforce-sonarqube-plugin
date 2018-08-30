/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.enforce.sonarqube.apex.parser.grammar;

import org.fundacionjala.enforce.sonarqube.apex.parser.ApexRuleTest;
import org.junit.Before;
import org.junit.Test;

import static org.fundacionjala.enforce.sonarqube.apex.api.grammar.ApexGrammarRuleKey.FIELD_DECLARATION;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class ApexGrammarFieldDeclarationTest extends ApexRuleTest {

    @Before
    public void setUp() {
        setRootRule(FIELD_DECLARATION);
    }

    @Test
    public void testValidFieldDeclaration() {
        assertThat(parser)
                .matches("integer myVariable;")
                .matches("integer addition,takeaway;")
                .matches("Boolean isActive = true;")
                .matches("integer addition = 0;")
                .matches("ClassType transient;")
                .matches("Integer count = 0;")
                .matches("Integer order = 0;")
                .matches("string name = syste.test();")
                .matches("Product2 product_01 = new Product2();")
                .matches("Iterator iterator = iteratorParameter;");
    }

    @Test
    public void testInvalidFieldDeclaration() {
        assertThat(parser)
                .notMatches("char varCharMissingSemicolon")
                .notMatches("ClassType missingValue =;");
    }
    
    @Test
    public void ResolvePositiveParsingRules(){
    	assertThat(parser)
    	.matches("Opportunity retVal = new Opportunity(Name = 'Some Oppty', AccountId = System.today(), createdDate = System.test());")
    			.matches("Opportunity retVal = new test(Name = Some.Today);")
    			.matches("Asset a = new Asset(Name = 'test', AccountId = acc.Id, Array_ID__c = 'test', Purity_Version__c = 'test'," +
    					" Status = 'Installed', Pure_Warranty_Description__c = 'test',"+
    					" Street_Address__c = 'test street', City__c = 'San Francisco', Latitude_Longitude__latitude__s = 1, Latitude_Longitude__longitude__s =1,"+
    					" state_province__c = 'California', Postal_Code__c = '90000', Platform_PN__c='test', SE_Opportunity_Owner__c = UserInfo.getUserId(), "+
    					" Country__c = 'United States', Original_Contract_End_Date__c=Date.today(),Support_Pricing__c=spc.id);");
    }
}
