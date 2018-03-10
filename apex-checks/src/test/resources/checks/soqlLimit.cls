public without sharing class WebToCaseController {

    //Constructor
    public WebToCaseController() {
        string accId = Apexpages.currentPage().getParameters().get('accId');
        //String policyNumber = Apexpages.currentPage().getParameters().get('POLICY');
        Document doc = new Document();
        list<Document>(); attachedDocList = new list<Document>();
        DateTime CURRENT_DATE_TIME = system.now().format('ddMMyyyy hh:mm:ss a');
        //wrapperMap = new map<String, list<FormWrapper>>();

        //intialiseWrapperMap();

        list<SelectOption> requestTypeOptions = new list<SelectOption>();
        list<String> pageSectionName = new list<String>();
        Boolean formSubmitted = false;
        /*if(accId <> null) {
            for(Account acc : [select Name, Id, AccountNumber
                                    from Account
                                    where Id = :accId]) {
                selectedAccount = acc;
            }
        }*/
        
    }

    // If Policy is not created, Create it using page action
    /*public void populateAccount(){

        list<AssignmentRule> lstAR = [select id from AssignmentRule where SobjectType = 'Case' and Active = true limit 1];

    }*/


 }