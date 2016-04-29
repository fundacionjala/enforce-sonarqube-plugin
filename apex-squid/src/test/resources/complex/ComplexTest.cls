@isTest  
private class ComplexTest
{    
    private class WebServiceMockImpl implements WebServiceMock 
	{
		public void doInvoke(
			Object stub, Object request, Map<String, Object> response,
			String endpoint, String soapAction, String requestName,
			String responseNS, String responseName, String responseType) 
		{
			if(request instanceof MetadataService.retrieve_element)
				response.put('response_x', new MetadataService.retrieveResponse_element());
			else if(request instanceof MetadataService.checkDeployStatus_element)
				response.put('response_x', new MetadataService.checkDeployStatusResponse_element());
			else if(request instanceof MetadataService.listMetadata_element)
				response.put('response_x', new MetadataService.listMetadataResponse_element());
			else if(request instanceof MetadataService.checkRetrieveStatus_element)
				response.put('response_x', new MetadataService.checkRetrieveStatusResponse_element());
			else if(request instanceof MetadataService.describeMetadata_element)
				response.put('response_x', new MetadataService.describeMetadataResponse_element());
			else if(request instanceof MetadataService.checkRetrieveStatus_element)
                                response.put('response_x', new MetadataService.checkRetrieveStatusResponse_element());
			return;
		}
	}    
		
    @IsTest
    private static void coverGeneratedCodeCRUDOperations()
    {	
    	System.Test.setMock(WebServiceMock.class, new WebServiceMockImpl());
        MetadataService metaDataService = new MetadataService();
        MetadataService.MetadataPort metaDataPort = new MetadataService.MetadataPort();
    }
	
    @IsTest
    private static void coverGeneratedCodeFileBasedOperations1()
    {    	
    	System.Test.setMock(WebServiceMock.class, new WebServiceMockImpl());
        MetadataService metaDataService = new MetadataService();
        MetadataService.MetadataPort metaDataPort = new MetadataService.MetadataPort();
        metaDataPort.retrieve(null);
        metaDataPort.checkDeployStatus(null, false);
        metaDataPort.listMetadata(null, null);
        metaDataPort.describeMetadata(null);
        metaDataPort.deploy(null, null);
        metaDataPort.checkDeployStatus(null, false);
        metaDataPort.updateMetadata(null);
        metaDataPort.renameMetadata(null, null, null);
        metaDataPort.cancelDeploy(null);
    }

    @IsTest
    private static void coverGeneratedCodeFileBasedOperations2()
    {       
        System.Test.setMock(WebServiceMock.class, new WebServiceMockImpl());
        MetadataService metaDataService = new MetadataService();
        MetadataService.MetadataPort metaDataPort = new MetadataService.MetadataPort();
        metaDataPort.deleteMetadata(null, null);
        metaDataPort.upsertMetadata(null);
        metaDataPort.createMetadata(null);
        metaDataPort.deployRecentValidation(null);
        metaDataPort.describeValueType(null);
        metaDataPort.checkRetrieveStatus(null, null);
    }
        
	@IsTest
    private static void coverGeneratedCodeTypes()
    {    	       
        new MetadataService();
        new MetadataService.listMetadataResponse_element();
        new MetadataService.WorkflowRule();
        new MetadataService.RecordTypeTranslation();
        new MetadataService.checkDeployStatus_element();
        new MetadataService.CodeCoverageWarning();
        new MetadataService.ModerationRule();
        new MetadataService.WaveApplication();
        new MetadataService.WaveDataset();
    }

    @IsTest
    private static void elfMissingGetRecordsTest() { 
        new MetadataService.ReadFlowResult().getRecords();
        new MetadataService.ReadWaveApplicationResult().getRecords();
        new MetadataService.ReadWaveDatasetResult().getRecords();
    }

    @IsTest
    private static void elfMissingGetResultTest() {
        new MetadataService.readWorkflowRuleResponse_element().getResult();
        new MetadataService.readSamlSsoConfigResponse_element().getResult();
        new MetadataService.readModerationRuleResponse_element().getResult();
        new MetadataService.readWaveApplicationResponse_element().getResult();
        new MetadataService.readWaveDatasetResponse_element().getResult();
    }    
}