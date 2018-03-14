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
        }
        
    }

    public void populateAccount(){

        list<AssignmentRule> lstAR = [SELECT id, count(id) TOTAL, SUM(ID) TOTA1, MAX(ID) MAX1, MIN(ID) min1, AVG(ID) avg1, COUNT_DISTINCT(ID) count1  FROM AssignmentRule WHERE SobjectType = 'Case' AND Active = 'true' LIMIT 1];

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
                                WHERE Id = :challengePointsBuckets[0].pointTable__r.pointTable__r.id
                                AND Id = :challengePointsBuckets[indexNumber].pointTable__r.id
                                AND Id = :challengePointsBuckets[indexNumber].id
                                AND Id = :Userinfo.getUserId()
                                AND id in :lst
                                AND ID = :test
                                Limit :getSOQlRows()];

    }
}

    