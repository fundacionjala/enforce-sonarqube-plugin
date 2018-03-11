public without sharing class WebToCaseFormController {

    public String selectedRequestType{get;set;}
    public Account selectedAccount{get;set;}
    //public String insuredName{get;set;}
    public boolean formSubmitted{get;set;}
    public boolean displayField {get;set;}
    public boolean displaySubmitButton {get;set;}
    public list<SelectOption> requestTypeOptions{get;set;}

    public map<String, list<FormWrapper>> wrapperMap {get;set;}
    public list<String> pageSectionName{get;set;}

    public Document doc{get;set;}
    public String documentId{get;set;}
    public list<Document> attachedDocList{get;set;}
    public String ThankYouNote{get;set;}
    public String CURRENT_DATE_TIME{get;set;}
    //public string wyoValue {get;set;}

    private final String ALL_OTHER_REQUEST_TYPE_API_NAME = 'All_Other_Request_Types__c';
    private final String DO_NOT_SHOW = 'DO NOT SHOW';
    private final String REQUIRED = 'Required';
    private final String PAGE_SECTION_ATTACHMENT = 'Attachments';
    private final String PAGE_SECTION_POLICY_INFO = 'Policy Info';
    private final String PAGE_SECTION_REQUEST_INFO = 'Request Info';
    //private final String INVALID_ID_IN_URL = 'Policy Id/Number is not specified in URL or Policy Id/Number provided is invalid.';
    private final String CASE_ORIGIN = 'Agent Portal';
    private final String FIELD_LABEL_REQUEST_TYPE = 'Request Type';
    private final String ATTACHMENT_MISSING_ERR = 'Please add attachement for this Request Type.';
    private final String PARAMETER_NOT_FOUND = 'URL Parameters Not Found';
    //public final static String DEFAULT_CASE_RECORD_TYPE = Schema.SObjectType.Case.getRecordTypeInfosByName().get('BinTech').getRecordTypeId();

    public map<String, String> noticeMap = new map<String, String>();
    public String newCaseId{get;set;}

    //Constructor
    public WebToCaseFormController() {
        string accId = Apexpages.currentPage().getParameters().get('accId');
        //String policyNumber = Apexpages.currentPage().getParameters().get('POLICY');
        doc = new Document();
        attachedDocList = new list<Document>();
        CURRENT_DATE_TIME = system.now().format('ddMMyyyy hh:mm:ss a');
        wrapperMap = new map<String, list<FormWrapper>>();

        intialiseWrapperMap();

        requestTypeOptions = new list<SelectOption>();
        pageSectionName = new list<String>();
        formSubmitted = false;
        if(accId <> null) {
            for(Account acc : [select Name, Id, AccountNumber
                                    from Account
                                    where Id = :accId]) {
                selectedAccount = acc;
            }
        }
    }

    //Constructor
    public WebToCaseFormController(string abc) {
        string accId = Apexpages.currentPage().getParameters().get('accId');
        //String policyNumber = Apexpages.currentPage().getParameters().get('POLICY');
        doc = new Document();
        attachedDocList = new list<Document>();
        CURRENT_DATE_TIME = system.now().format('ddMMyyyy hh:mm:ss a');
        wrapperMap = new map<String, list<FormWrapper>>();

        intialiseWrapperMap();

        requestTypeOptions = new list<SelectOption>();
        pageSectionName = new list<String>();
        formSubmitted = false;
        if(accId <> null) {
            for(Account acc : [select Name, Id, AccountNumber
                                    from Account
                                    where Id = :accId]) {
                selectedAccount = acc;
            }
        }

    }

    //Constructor
    public WebToCaseFormController(string abc, string pqr) {
        string accId = Apexpages.currentPage().getParameters().get('accId');
        //String policyNumber = Apexpages.currentPage().getParameters().get('POLICY');
        doc = new Document();
        attachedDocList = new list<Document>();
        CURRENT_DATE_TIME = system.now().format('ddMMyyyy hh:mm:ss a');
        wrapperMap = new map<String, list<FormWrapper>>();

        intialiseWrapperMap();

        requestTypeOptions = new list<SelectOption>();
        pageSectionName = new list<String>();
        formSubmitted = false;
        if(accId <> null) {
            for(Account acc : [select Name, Id, AccountNumber
                                    from Account
                                    where Id = :accId]) {
                selectedAccount = acc;
            }
        }

    }

    //Constructor
    public WebToCaseFormController(int abc) {
        string accId = Apexpages.currentPage().getParameters().get('accId');
        //String policyNumber = Apexpages.currentPage().getParameters().get('POLICY');
        doc = new Document();
        attachedDocList = new list<Document>();
        CURRENT_DATE_TIME = system.now().format('ddMMyyyy hh:mm:ss a');
        wrapperMap = new map<String, list<FormWrapper>>();

        intialiseWrapperMap();

        requestTypeOptions = new list<SelectOption>();
        pageSectionName = new list<String>();
        formSubmitted = false;
        if(accId <> null) {
            for(Account acc : [select Name, Id, AccountNumber
                                    from Account
                                    where Id = :accId]) {
                selectedAccount = acc;
            }
        }

    }

    //Constructor
    public WebToCaseFormController(string abc, int pre) {
        string accId = Apexpages.currentPage().getParameters().get('accId');
        //String policyNumber = Apexpages.currentPage().getParameters().get('POLICY');
        // test comment
        doc = new Document();
        attachedDocList = new list<Document>();
        CURRENT_DATE_TIME = system.now().format('ddMMyyyy hh:mm:ss a');
        wrapperMap = new map<String, list<FormWrapper>>();

        intialiseWrapperMap();

        requestTypeOptions = new list<SelectOption>();
        pageSectionName = new list<String>();
        formSubmitted = false;
        if(accId <> null) {
            for(Account acc : [select Name, Id, AccountNumber
                                    from Account
                                    where Id = :accId]) {
                selectedAccount = acc;
            }
        }

    }

    // If Policy is not created, Create it using page action
    public void populateAccount(){

        //businessName = selectedAccount.Business_Name__c;
        //insuredName = selectedAccount.Insured_Full_Name__c;

        requestTypeOptions.add(new selectOption('','---None---'));
        requestTypeOptions.addAll(getRequestTypeOptions());
        requestTypeOptions = sortSelectOptions(requestTypeOptions);
        displayField = false;
        displaySubmitButton = false;
    }


    //Put default field in map which will be shown on page load.
    public void intialiseWrapperMap() {
        FormWrapper wrap = new FormWrapper(PAGE_SECTION_REQUEST_INFO, true, FIELD_LABEL_REQUEST_TYPE, 3, 'Picklist');
        wrap.value =  selectedRequestType;
        wrapperMap.put(PAGE_SECTION_REQUEST_INFO, new list<FormWrapper>{wrap});
    }

    private void populateFieldMapping(){
        //replaceAll('![a-zA-Z0-9]', '');
        wrapperMap =  new map<String, list<FormWrapper>>();
        intialiseWrapperMap();
        // getting all fields api names and labels from custom setting FieldRequestTypeMapping__c
        Map<String, Schema.SObjectField> customFieldNameMap = Schema.SObjectType.FieldRequestTypeMapping__c.fields.getMap();

        String validApiName;

        for(String key : customFieldNameMap.KeySet()) {
            system.debug('Field Label: '+ customFieldNameMap.get(key).getDescribe().getLabel());
            if(selectedRequestType == customFieldNameMap.get(key).getDescribe().getLabel()) {
                // if label of custom field is same as selected Request type
                validApiName = key;
                break;
            }
        }
        System.debug('==============Request Type API Name===' + validApiName);
        list<FormWrapper> tempWrapperList = new list<FormWrapper>();
        // fetching all values from FieldRequestTypeMapping custom setting. Where Fields are mapped according to request type.
        for(FieldRequestTypeMapping__c setting : FieldRequestTypeMapping__c.getAll().values()) {

            if(validApiName == null) {
                validApiName = ALL_OTHER_REQUEST_TYPE_API_NAME;
            }
            // checking if field is required to be filled in form
            boolean isRequired = String.valueOf(setting.get(validApiName)) == REQUIRED;
            // checking if field need to show on form with selected request type.
            boolean showField = String.valueOf(setting.get(validApiName)) != DO_NOT_SHOW;
            // for attachment section checking if attachment is required to submit case.
            if(setting.Page_Section_Name__c == PAGE_SECTION_ATTACHMENT && setting.get(validApiName) <> 'Yes') {
                showField = false;
            }
            // adding the list of fields to be shown on form.
            // Excluding fields Request Type (Picklist) and Notice (Label). These will be separtly handled on page.
            // Excluding Policy Info Page Section that is already present on form on page load.
            if(showField && setting.Page_Section_Name__c <> PAGE_SECTION_POLICY_INFO && setting.Name <> FIELD_LABEL_REQUEST_TYPE) {
                FormWrapper wrapperObj = new FormWrapper(setting.Page_Section_Name__c, isRequired, setting.Name, (Integer)setting.Tab_Order__c, setting.Field_Type__c);
                if(setting.Field_Type__c == 'ReadOnly')  {
                    wrapperObj.value = (String)setting.get(validApiName);
                }
                if(setting.Name == 'Notice') {
                    wrapperObj.value = noticeMap.get(selectedRequestType);
                }
                System.debug('=======noticeMap=========' + noticeMap);
                System.debug('=======wrapperObj.value=========' + wrapperObj.value);

                if(setting.Field_Type__c == 'ReadOnly' && (wrapperObj.value == null || wrapperObj.value == '')) continue;
                tempWrapperList.add(wrapperObj);
            }
        }
        //sort list of fields based on tab order.
        tempWrapperList = SortWrapperList(tempWrapperList);
        // sorted page section names.
        pageSectionName = new list<String>();
        pageSectionName.add(PAGE_SECTION_REQUEST_INFO);

        for(FormWrapper wrap : tempWrapperList) {
            if(!wrapperMap.containsKey(wrap.pageSectionName)) {
                wrapperMap.put(wrap.pageSectionName, new list<FormWrapper>());
                pageSectionName.add(wrap.PageSectionName);
            }
            wrapperMap.get(wrap.pageSectionName).add(wrap);
            System.debug('======Tab Order== '+ wrap.tabOrder);
        }
        System.debug('====Sorted Page Section==' + pageSectionName);
    }

    public list<SelectOption> getYesNoPicklistValues() {
        list<SelectOption> yesNoList = new list<SelectOption>();
        yesNoList.add(new selectOption('No','No'));
        yesNoList.add(new selectOption('Yes','Yes'));
        return yesNoList;
    }

    // getting Request Type values based on policy type from custom setting RequestTypeAndPolicyTypeMapping
    private list<SelectOption> getRequestTypeOptions() {
        list<SelectOption> requestTypeOptions = new list<SelectOption>();
        String policyType = 'Policy Type';//selectedAccount.Policy_Type_Formula__c;

        Map<String, Schema.SObjectField> customFieldNameMap = Schema.SObjectType.RequestTypeAndPolicyTypeMapping__c.fields.getMap();

         String validApiName;

        for(String key : customFieldNameMap.KeySet()) {
            if(policyType == customFieldNameMap.get(key).getDescribe().getLabel()) {
                // if label of custom field is same as selected Request type
                validApiName = key;
                break;
            }
        }
        if(validApiName == null) validApiName = 'Other__c';
        // T-286380: For BLDR Product, "Add Location" Case Type should not display
        for(RequestTypeAndPolicyTypeMapping__c rtSetting : RequestTypeAndPolicyTypeMapping__c.getAll().values()) {
            if(rtSetting.get(validApiName) == true) {
                //system.debug(rtSetting.Name +'; '+ selectedAccount.Product__c);
                if(rtSetting.Name == 'Add Location' ){//&& selectedAccount.Product__c == 'BLDR'
                    continue;
                }
                requestTypeOptions.add(new SelectOption(rtSetting.Name, rtSetting.Name));
                noticeMap.put(rtSetting.Name, rtSetting.Notice__c);
                System.debug(rtSetting.Name + '=====putting in map ===' + rtSetting.Notice__c);
            }
        }
        return requestTypeOptions;
    }

    // Creating case once submit button clicked
    public Pagereference submitCase() {

        String description = '';
        boolean isFormValid = true;
        String errMsg = '';
        Date inputDate;
        Date validDate = Date.today().addDays(-7);
        String webName;
        string caseSuppliedEmail = '';
        Set<String> filterOutFields = new Set<String>{PAGE_SECTION_ATTACHMENT, 'Notice', FIELD_LABEL_REQUEST_TYPE};
        System.debug('*** test 1');
        for(String key : pageSectionName) {
            for(FormWrapper wrap : wrapperMap.get(key)) {
                System.debug('*** test 2');
                if(wrap.label == 'Requestor Email Address' && wrap.value <> null & wrap.value <> '' && wrap.value <> '') {
                    if(!Pattern.matches('[a-zA-Z0-9._-]+@[a-zA-Z]+.[a-zA-Z]{2,4}', wrap.value)){
                        isFormValid = false;
                        ApexPages.addmessage(new ApexPages.message(ApexPages.severity.Error, 'Please enter valid requestor email address.'));
                    }else{
                        caseSuppliedEmail = wrap.value;
                    }

                }
                if(wrap.isRequired && wrap.label == 'Request Effective Date' && wrap.value <> null) {
                    try {
                        //Changes Starts
                        List <String> valueList = (wrap.value).split('/');
                        String newDate = '';
                        for (String val : valueList) {
                            if (val.length() == 1) {
                                val = '0' + val;
                                newDate += val + '/';
                            } else {
                                newDate += val + '/';
                            }
                        }
                        wrap.value = newDate.subString(0, newDate.length() - 1);
                        //Changes Ends
                        inputDate = date.parse(wrap.value);
                    }catch(Exception ex) {
                        isFormValid = false;
                        ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, 'Please enter a valid date.'));
                    }
                }

                if(wrap.label == 'Request Instructions') {
                   system.debug('Request Instructions value: '+ wrap.value);
                }

                if(wrap.label == 'Requested By Name') {
                    webName = wrap.value;
                }
                // required fields should be populated
                if(wrap.isRequired && (wrap.value == '' || wrap.value == null )) {
                    isFormValid = false;
                    errMsg = wrap.label + ' cannot be blank.';
                    ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, errMsg));
                }
                System.debug('*** test 3');
                // if attachment is required then at least one attachment should be selected.
                if(wrap.isRequired && wrap.pageSectionName == PAGE_SECTION_ATTACHMENT && attachedDocList.size() == 0) {
                    isFormValid = false;
                    ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, ATTACHMENT_MISSING_ERR));
                }
                // filter out unnecessary field to be mapped in case desc.
                if(!filterOutFields.contains(wrap.label)) {
                    description += wrap.label + ': ' + (wrap.value == null ? '' : wrap.value)    + '\n';
                }
                System.debug('*** test 4');
            }
        }
        System.debug('*** test 5');
        // Request Effective Date should not be less than a week
        if(inputDate <> null && inputDate < validDate) {
            isFormValid = false;
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, 'Effective Date cannot less than one week from current day'));
        }

        if(!isFormValid) {
            return null;
        }

        //Fetching the assignment rules on case
        list<AssignmentRule> lstAR = [select id from AssignmentRule where SobjectType = 'Case' and Active = true limit 1];

        Database.DMLOptions dmlOpts = new Database.DMLOptions();
        if(!lstAR.isEmpty()){
            //Creating the DMLOptions for "Assign using active assignment rules" checkbox
            dmlOpts.assignmentRuleHeader.assignmentRuleId= lstAR[0].id;
        }

        Case newPolicyCase = new Case();
        newPolicyCase.AccountId = selectedAccount.Id;
        newPolicyCase.Type = selectedRequestType;
        newPolicyCase.Description = description;
        //newPolicyCase.CaseNumber = String.valueOf((Math.floor(Math.random() * (99999 - 999)) + 999));
        newPolicyCase.Origin = CASE_ORIGIN;
        newPolicyCase.SuppliedName = webName;
        if(caseSuppliedEmail != null && caseSuppliedEmail != ''){
            newPolicyCase.SuppliedEmail = caseSuppliedEmail;
        }

        if(!lstAR.isEmpty()){
            newPolicyCase.setOptions(dmlOpts);
        }
        system.debug('newPolicycase: '+ newPolicyCase);
        insert newPolicyCase;

        Utility.generateCasePDFAttachment(newPolicyCase.Id, UserInfo.getSessionId(),'');
        // fetching caseNumber to show on confirmation screen.
        String caseNumber;
        for(Case cs : [select Id, CaseNumber from Case where Id = :newPolicyCase.Id]) {
            caseNumber = cs.CaseNumber;
            newCaseId = cs.Id;
            system.debug('CaseNumber: '+ caseNumber);
        }
        //inserting attachments
        list<Attachment> newAttachments = new list<Attachment>();
        for(Document attachedDoc : [select Name, body from Document where ID IN : attachedDocList]) {
            Attachment a = new Attachment();
            a.body = attachedDoc.body;
            a.parentId = newPolicyCase.Id;
            a.Name = attachedDoc.Name;
            newAttachments.add(a);
        }
        String policyDescLikeStr = selectedAccount.AccountNumber + ' - %';
        for(Document relatedDoc : [select id from Document
                                     where ID NOT IN : attachedDocList AND
                                     FolderId = :Userinfo.getUserId()
                                     AND Description like :policyDescLikeStr]) {
                attachedDocList.add(relatedDoc);
        }
        if(!newAttachments.isEmpty()) insert newAttachments;
        // deleting all temp documents which were created to store attachments files.
        if(!attachedDocList.isEmpty()) delete attachedDocList;

		system.debug(Label.WebToCase_Thank_You_Note);
		system.debug('CaseNumber: '+ caseNumber);
        ThankYouNote = Label.WebToCase_Thank_You_Note.replace('<CASE_NUMBER>', caseNumber);
        ThankYouNote = ThankYouNote.replace('<POLICY_NUMBER>', selectedAccount.AccountNumber);
        ThankYouNote = ThankYouNote.replace('<CASE_TYPE>', selectedRequestType);

        string businessInsuredLabel = 'Account Number';
        string businessInsuredName = selectedAccount.Name;

        ThankYouNote = ThankYouNote.replace('<INSURED_BUSINESS_LABEL>', businessInsuredLabel);
        ThankYouNote = ThankYouNote.replace('<INSURED_BUSINESS_NAME>', businessInsuredName);

        formSubmitted = true;
        return null;
    }

    // Delete attachment from list
    public void deleteAttachment() {
        Document attachmentToBeDel;
        for(Document a : [select id from Document where Id =:documentId]) {
            attachmentToBeDel = a;
        }
        if(attachmentToBeDel <> null) delete attachmentToBeDel;
        showAttachment();
    }

    // Prepare list of fields to display on page based on selected Request Type
    public void getFieldList(){
        if(selectedRequestType != null && selectedRequestType != '' && selectedRequestType != '---None---'){
            populateFieldMapping();
            displayField = true;
            displaySubmitButton = true;
        } else {
            pageSectionName = new list<String>();
            pageSectionName.add(PAGE_SECTION_REQUEST_INFO);
            displayField = false;
            displaySubmitButton = false;
            intialiseWrapperMap();
        }
    }

    // sort fields based on tab order
    public list<FormWrapper> SortWrapperList(list<FormWrapper> wrapList) {
        list<Integer> valuesToSort = new list<Integer>();
        list<FormWrapper> returnList = new list<FormWrapper>();
        map<Integer, FormWrapper> wrapMap = new map<Integer, FormWrapper>();
        for(FormWrapper wrap : wrapList) {
            wrapMap.put(wrap.tabOrder, wrap);
        }
        valuesToSort.addAll(wrapMap.keySet());
        valuesToSort.sort();
        for(Integer key : valuesToSort) {
            returnList.add(wrapMap.get(key));
        }
        return returnList;
    }

    public list<SelectOption> sortSelectOptions(list<SelectOption> soList) {
        list<String> tempList = new list<String>();
        for(SelectOption so : soList) {
            tempList.add(so.getLabel());
        }
        soList = new list<SelectOption>();
        tempList.sort();
        for(String str : tempList) {
            soList.add(new selectOption(str, str));
        }
        return soList;
    }

    // display list of attachment
    public void showAttachment() {
        System.debug('==documentId-> ' + documentId);
        String docPostFixStr = selectedAccount.AccountNumber + ' - ' + CURRENT_DATE_TIME;
        System.debug('====docPostFixStr===' + docPostFixStr);
        attachedDocList = [select Id, name from Document where folderId = :userinfo.getUserId() AND Description = :docPostFixStr];
    }

    // display list of attachment
    public void showAttachment(int a, string b, int c, string d, int f, string e) {
        System.debug('==documentId-> ' + documentId);
        String docPostFixStr = selectedAccount.AccountNumber + ' - ' + CURRENT_DATE_TIME;
        System.debug('====docPostFixStr===' + docPostFixStr);
        attachedDocList = [select Id, name from Document where folderId = :userinfo.getUserId() AND Description = :docPostFixStr];
    }

    // Insertion of documents when add attachment get called
    public void insertDoc() {
        if(doc.body == null) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, 'Please choose file before save.'));
            return;
        }
        String fileExtension = doc.name.indexOf('.') <> -1 ? doc.name.substring(doc.name.lastIndexOf('.') + 1) : null;
        Set<String> validFileTypes = new Set<String>{'BMP', 'GIF', 'JPEG', 'JPG', 'PDF', 'PNG', 'TIF', 'TIFF'};
        if(fileExtension == null || !validFileTypes.contains(fileExtension.toUpperCase())) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, Label.WebToCaseAttachmentFileTypeError));
            return;
        }
        doc.FolderId = Userinfo.getUserId();

        String dateTimeParam = Apexpages.currentPage().getParameters().get('dateTime');
        doc.Description = selectedAccount.AccountNumber + ' - ' + dateTimeParam;
        insert doc;
        documentId = doc.Id;
        doc.Body = null;
    }

    // Wrapper class to display fields dynamically
    public class FormWrapper {
        public Boolean isRequired{get;set;}
        public String value{get;set;}
        public String label{get;set;}
        public Integer tabOrder{get;set;}
        public boolean isTextArea{get;set;}
        public boolean isDateField{get;set;}
        public boolean isPicklistField{get;set;}
        public boolean isTextField{get;set;}
        public boolean isReadOnly{get;set;}
        public String pageSectionName{get;set;}

        public FormWrapper(String pageSectionName, boolean isRequired, String label, Integer tabOrder, String fieldType) {
            this.isDateField = false;
            this.isPicklistField = false;
            this.isTextField = false;
            this.isReadOnly = false;
            this.isRequired = isRequired;
            this.label = label;
            this.tabOrder = tabOrder;
            //this.isTextArea = isTextArea;
            this.pageSectionName = pageSectionName;
            if(fieldType == 'TextArea') {
                this.isTextArea = true;
            } else if(fieldType == 'Picklist') {
                this.isPicklistField = true;
            } else if(fieldType == 'Date') {
                this.isDateField = true;
            } else if(fieldType == 'Text'){
                this.isTextField = true;
            } else if(fieldType == 'ReadOnly') {
                this.isReadOnly = true;
            }
        }

    }

    // Wrapper class to display fields dynamically
    public class FormWrapper1 {
        public Boolean isRequired{get;set;}
        public String value{get;set;}
        public String label{get;set;}
        public Integer tabOrder{get;set;}
        public boolean isTextArea{get;set;}
        public boolean isDateField{get;set;}
        public boolean isPicklistField{get;set;}
        public boolean isTextField{get;set;}
        public boolean isReadOnly{get;set;}
        public String pageSectionName{get;set;}

        public FormWrapper1(String pageSectionName, boolean isRequired, String label, Integer tabOrder, String fieldType) {
            this.isDateField = true;
            this.isPicklistField = true;
            this.isTextField = true;
            this.isReadOnly = true;
            //Useful code
            this.isDateField = false;
            this.isPicklistField = false;
            this.isTextField = false;
            this.isReadOnly = false;
            this.isRequired = isRequired;
            this.label = label;
            this.tabOrder = tabOrder;
            //this.isTextArea = isTextArea;
            this.pageSectionName = pageSectionName;
            if(fieldType == 'TextArea') {
                this.isTextArea = true;
            } else if(fieldType == 'Picklist') {
                this.isPicklistField = true;
            } else if(fieldType == 'Date') {
                this.isDateField = true;
            } else if(fieldType == 'Text'){
                this.isTextField = true;
            } else if(fieldType == 'ReadOnly') {
                this.isReadOnly = true;
            }
        }

    }
}