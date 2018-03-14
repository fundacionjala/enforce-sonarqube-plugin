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
                                    WHERE Id = :accId]) {
                selectedAccount = acc;
            }
        }
        
    }

    public void populateAccount(){

        list<AssignmentRule> lstAR = [SELECT id FROM AssignmentRule WHERE SobjectType = 'Case' AND Active = 'true' LIMIT 1];

        List<Points__c> pList = [SELECT Id,
                                    Name,
                                    Challenge__r.Id,
                                    Amount__c,
                                    Date__c,
                                    Source__c,
                                    RecordType.Name
                             FROM   Points__c
                             WHERE  User__r.Id = :selectedEmployee
                             AND    Challenge__r.id != null
                             ORDER BY Challenge__r.Title__c];
                             
       pointsBuckets = [SELECT Id, Current_Balance__c, Owner__c, Title__c, CreatedById 
                                FROM Points_Bucket__c 
                                WHERE Id = :challengePointsBuckets.Points_Bucket__r.Id
                                AND Id = :Userinfo.getUserId()
                                Limit :getSOQlRows()];

    }
}

    