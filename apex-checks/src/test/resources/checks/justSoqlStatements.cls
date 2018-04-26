public class EmptyCatch{
   
    private void method1(){
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
								
		list<User__c> lstUser = [select Region__c 
								from User
								where Region__c != ''
								Group By Region__c
								HAVING SUM(Amount__c) >=: lastThrityDayPoint
								
								Order By Region__c];	
													
		list<table1> lstTable1 = [SELECT field1,field2 FROM table1 AS T1 
                        WHERE id includes (a4, b5) LIMIT 100];
                        
         List<Bot_Command__c> commands = [SELECT apex_class__c, pattern__c FROM Bot_Command__c WHERE Active__c = True ORDER BY Name];
    }
}