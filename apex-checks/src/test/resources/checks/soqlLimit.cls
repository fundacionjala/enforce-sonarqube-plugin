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
        
        list<points> lstPoints123 = [SELECT SUM(Amount__c) points
                                                FROM   Points__c
                                                WHERE  User__r.Id = :user
                                                AND    Amount__c > 0
                                                AND    RecordType.Name = 'Earned Points'
                                                AND    Date__c = THIS_QUARTER];
        
        list<lead> lstLead = [SELECT LeadSource, COUNT(Name)
								FROM Lead
								where title__c LIKE :key AND (city__c LIKE :key OR tags__c LIKE :key)
								GROUP BY LeadSource];
								
		list<User__c> lstUser = [select Region__c 
								from User
								where Region__c != ''
								Group By Region__c
								Order By Region__c];	
													
		list<table1> lstTable1 = [SELECT field1,field2 FROM table1 AS T1 
                        WHERE id includes (a4, b5) LIMIT 100];
                        
         List<Bot_Command__c> commands = [SELECT apex_class__c, pattern__c FROM Bot_Command__c WHERE Active__c = True ORDER BY Name];
        
    }

    public void populateAccount(){

        list<AssignmentRule> lstAR = [SELECT id, count(id) TOTAL, SUM(ID) TOTA1, MAX(ID) MAX1, MIN(ID) min1, AVG(ID) avg1, COUNT_DISTINCT(ID) count1  
        								FROM AssignmentRule 
        								WHERE SobjectType = 'Case' AND Active = 'true'
        								AND MSP1__c includes ('AAA;BBB','CCC')
        								GROUP BY Challenge__r.Id, Active, Id
        								order by Challenge__r.Id ASC, Active DSC, Id  
        								LIMIT 1];

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
                                AND id in (SELECT ParentId from Account where id like 'A%')
                                AND ID = :test
                                Limit :getSOQlRows()];

    }
}

    