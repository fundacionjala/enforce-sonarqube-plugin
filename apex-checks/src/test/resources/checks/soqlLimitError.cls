public class SomeClass {
	string accId = '1234';
    Document doc = new Document();
    list<Document>() attachedDocList = new list<Document>();
    DateTime CURRENT_DATE_TIME = system.now().format('ddMMyyyy hh:mm:ss a');
        
    populateAccount();
	list<points> lstPoints123 = [SELECT SUM(Amount__c) points
                                                FROM   Points__c
                                                WHERE  User__r.Id = :user
                                                AND    Amount__c > 0
                                                AND    RecordType.Name = 'Earned Points'
                                                AND    Date__c = THIS_QUARTER];
    
    list<lead> lstLead = [SELECT LeadSource, COUNT(Name)
								FROM Lead
								where title__c LIKE :key AND (city__c LIKE :key OR tags__c LIKE :key)
								GROUP BY LeadSource
								HAVING SUM(Amount__c) >=: lastThrityDayPoint
								];
    
}