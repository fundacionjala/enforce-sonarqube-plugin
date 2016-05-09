public with sharing class ADDR_Validator_Batch implements Database.Batchable<sObject>, Database.AllowsCallouts {

    private String query;
    
    public ADDR_Validator_Batch(Boolean skipPreviouslyVerified) {
    	Addr_Verification_Settings__c avs = UTIL_CustomSettingsFacade.getAddressVerificationSettings();
        if(avs.Class__c == 'ADDR_SmartyStreets_Validator') {
    	   avs.Using_SmartyStreets__c = true;
            update avs;
        }
        
        query = 'select id, name, MailingStreet__c, MailingStreet2__c, Formula_MailingStreetAddress__c, MailingCity__c, MailingState__c, MailingPostalCode__c, MailingCountry__c, API_Response__c ' + 
            ' from Address__c where ' +
            '(MailingCountry__c = null or MailingCountry__c in (\'US\', \'USA\', \'United States\', \'United States of America\')) ' +
            'and (MailingStreet__c != null or MailingPostalCode__c != null or (MailingCity__c != null and MailingState__c != null))';
         
        if (skipPreviouslyVerified)
            query += ' and Verified__c != true';
    }
    
    public Database.QueryLocator start(Database.BatchableContext bc) {
        return Database.getQueryLocator(query);
    }

    public void execute(Database.BatchableContext bc, list<Address__c> scope) {
        list<Address__c> addressesToVerify = new list<Address__c>();
        for (Address__c a : scope)
            if (isUS(a)) addressesToVerify.add(a); 
        
        if (!addressesToVerify.isEmpty())
            ADDR_Validator.verifyAddressRecords(addressesToVerify);
    }
    
    public void finish(Database.BatchableContext bc) { }
    
    public static Boolean isUS(Address__c c) {
        return ((c.MailingStreet__c != null || c.MailingPostalCode__c != null || (c.MailingCity__c != null && c.MailingState__c != null))
            && (c.MailingCountry__c == null || c.MailingCountry__c == 'US' || c.MailingCountry__c == 'USA' || 
                        c.MailingCountry__c == 'United States' || c.MailingCountry__c == 'United States of America'));
    }
}