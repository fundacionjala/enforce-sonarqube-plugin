public class SomeClass {
	
	public static void aMethod() {
        List<table1> incorrect = [SELECT dato FROM table1];
        List<table1> incorrect = Database.query('SELECT dato FROM table1');
    }

    public static void bMethod() {
        List<table1> correct = [SELECT dato FROM table1 LIMIT 50];
    }

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
            for(Account acc : [select Name, Id, AccountNumber
                                    from Account
                                    where Id = :accId]) {
                selectedAccount = acc;
            }
        }
        
    }

    public void populateAccount(){

        list<AssignmentRule> lstAR = [select id from AssignmentRule where SobjectType = 'Case' and Active = true limit 1];

    }
}

    