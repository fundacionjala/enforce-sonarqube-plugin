public class SomeClass {
	
	public static void cMethod(){
        string accId = '1234';
        Document doc = new Document();
        list<Document>() attachedDocList = new list<Document>();
        DateTime CURRENT_DATE_TIME = system.now().format('ddMMyyyy hh:mm:ss a');
        
        populateAccount();

        list<SelectOption> requestTypeOptions = new list<SelectOption>();
        list<String> pageSectionName = new list<String>();
        Boolean formSubmitted = false;
        if(accId <> null) {
            for(Account acc : [SELECT Name, Id, AccountNumber
                                    FROM Account
                                    WHERE Id = :accId 
                                    and id = '1234'
                                    limit :rowCount
                                    ]) {
                selectedAccount = acc;
            }
            
            for(Account acc : [SELECT Name, AccountNumber
                                    FROM Account
                                    WHERE id = '1234'
                                    limit 1
                                    ]) {
               selectedAccount = acc;
      		}
      		
      		incomingObjs = [SELECT ID, Country_Name__c, rC_Bios__Original_Country__c
                      FROM rC_Bios__Account_Address__c
                      WHERE ID IN :idsAfterSplit Limit 2 FOR UPDATE];
        }
        
        for(Opportunity opp : [SELECT Id, Campaign.rC_Giving__Source_Code__c, CreatedDate,AccountId
                               FROM Opportunity
                               WHERE (RecordType.Name = 'Pledge' OR RecordType.Name = 'Donation')  
                               AND AccountId IN : accountsIds 
                               AND CampaignId != NULL
                               AND AccountId != NULL
                               ORDER BY CreatedDate ASC
                               LIMIT 2]) {
	        if(!mapOfAccountIdVsOppId.containsKey(opp.AccountId)) {
	          mapOfAccountIdVsOppId.put(opp.AccountId,opp);
	          break;
	        }
        }
        
        incomingObjs = [SELECT ID, Country_Name__c, rC_Bios__Original_Country__c
                      FROM rC_Bios__Account_Address__c
                      WHERE Account__c in: accIdList 
                      AND Team_Role__c IN ('Primary Relationship Manager', 'Major Gifts Affiliate', 'Major Gifts National' ) 
                      AND  Status__c = 'Current' 
                      Limit 2 FOR UPDATE];
        
        incomingObjs = [SELECT ID, Country_Name__c, rC_Bios__Original_Country__c
                      FROM rC_Bios__Account_Address__c
                      WHERE ID IN :idsAfterSplit 
                      AND accountId IN :accWrappersById.keySet()                                       
                	AND ( RecordTypeId = : BatchLogDetailPageController.opptyRTMap.get('Grant').getRecordTypeId()
                			OR 
                			RecordTypeId = : BatchLogDetailPageController.opptyRTMap.get('Proposal').getRecordTypeId()
                			OR (RecordTypeId = : BatchLogDetailPageController.opptyRTMap.get('Pledge Payment').getRecordTypeId()
                  				AND stageName = 'Open' AND IsCreatedFromBatchLog__c = false
                  				)
          				)
          				limit 2];
          				
         Group grp = [SELECT Id, Name FROM Group
         			WHERE Name =: mapActionPlanIdToAffiliate.Values() 
         			OR Name =: ALLACLU 
         			OR Name =: NATIONAL 
         			OR createdDate >= :system.now().addDays(1)
         			LIMIT 1];
         			
        String accToqueryID = [SELECT id, contact.accountID from CampaignMember WHERE id =: campMemberList.get(0).id limit 1].contact.accountID;
        
        list<rC_BUP__Event__c> eventsToDelete = [SELECT Id FROM rC_BUP__Event__c
                                               WHERE (rC_BUP__Batch_Upload__c IN :batchUploadsDeleteSuspendedEvent
                                                   AND rC_BUP__Event_State__c IN ('Suspend', 'Error')) limit 2];
        
        String accToqueryID = [SELECT id, contact.accountID from CampaignMember WHERE id in (select id from Account limit 1) limit 1].contact.accountID; 
        
        list<Account> lst = [Select Id, Name from Account where Name in (Select name from Account where name like 'A%' limit 1) limit 1];   
    }
}