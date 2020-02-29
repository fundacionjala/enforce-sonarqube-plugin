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
        		while(true){
	            for(int ii = 0; i < 10; ii++) {
	                list<points> lstPoints123 = [SELECT SUM(Amount__c) points
	                                                FROM   Points__c
	                                                WHERE  User__r.Id = :user
	                                                AND    Amount__c > 0
	                                                AND    RecordType.Name = 'Earned Points'
	                                                AND    Date__c = TODAY];
	            }
            }
        }
    }
}