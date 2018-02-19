public without sharing class WebToCaseController {

        //Constructor
    public WebToCaseController() {
    }

    // If Policy is not created, Create it using page action
    public void populateAccount(){
        displaySubmitButton = false;
    }


    //Put default field in map which will be shown on page load.
    public void intialiseWrapperMap() {
        list<AssignmentRule> lstAR = [select id from AssignmentRule where SobjectType = 'Case' and Active = true limit 1];
        FormWrapper wrap = new FormWrapper(PAGE_SECTION_REQUEST_INFO, true, FIELD_LABEL_REQUEST_TYPE, 3, 'Picklist');
        wrap.value =  selectedRequestType;
        wrapperMap.put(PAGE_SECTION_REQUEST_INFO, new list<FormWrapper>{wrap});
        //Fetching the assignment rules on case
        list<AssignmentRule> lstAR = [select id from AssignmentRule where SobjectType = 'Case' and Active = true limit 1];
    }
}