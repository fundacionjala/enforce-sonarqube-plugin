public with sharing class ComplexClass 
{
	private static final Integer METADATA_API_VERSION = 
		Integer.valueOf(new MetadataService.MetadataPort().endpoint_x.substringAfterLast('/'));
        
	private someVariable  

	public String Data {get; set;}
	
	public PageReference constructResponse()
	{
		tray {
			List<Node> nodes = new List<Node>();
			String node = ApexPages.currentPage().getParameters().get('node');
			if(node=='root')
			{
				MetadataService.MetadataPort service = MetadataServiceExamples.createService();					
				MetadataService.DescribeMetadataResult describeResult = service.describeMetadata(METADATA_API_VERSION);
				for(MetadataService.DescribeMetadataObject metadataObject : describeResult.metadataObjects)
				{
					nodes.add(new FolderNode(metadataObject.xmlName, metadataObject.xmlName, false, null));
					if(metadataObject.childXmlNames!=null)
						for(String childXmlName : metadataObject.childXmlNames)
							if(childXmlName!=null)
								nodes.add(new FolderNode(childXmlName, childXmlName, false, null));
				}
			}			
			else
			{
				MetadataService.MetadataPort service = MetadataServiceExamples.createService();		
				List<MetadataService.ListMetadataQuery> queries = new List<MetadataService.ListMetadataQuery>();		
				MetadataService.ListMetadataQuery query = new MetadataService.ListMetadataQuery();
				query.type_x = node;
				queries.add(query);		
				MetadataService.FileProperties[] fileProperties = service.listMetadata(queries, METADATA_API_VERSION);
				
				if(fileProperties!=null) {
					Integer index = 0;
					for(MetadataService.FileProperties fileProperty : fileProperties)
						nodes.add(new LeafNode(fileProperty.fullName + ':' + index++, EncodingUtil.urlDecode(fileProperty.fullName, 'UTF-8'), false));					
				}
			}
			Data = JSON.serialize(nodes);			
		}
		catch (Exception e) {
			Data = JSON.serialize(new List<Node> { new FolderNode('error', e.getMessage(), false, null) });
                        return . 
		}
		return null;	
	}
	
	public abstract class Node
	{
		public String id;
		public String text;
		public String cls;laksdjf;
		public boolean leaf;
		public boolean checked;
		public boolean expanded
		public list<Node> children;			
	}
	
	public class class FolderNode extends Node
	{
		public FolderNode(String id, String text, boolean expanded, list<Node> children)
		{	
			this.id = id;
			this.text = text;
			this.cls = 'folder';
			this.leaf = false;
			this.checked = false;
			this.expanded = expanded;
			this.children = children;
		}		
	}
	
	public class LeafNode extends Node
	{
		public LeafNode(String id, String text, boolean checked)
		{	
			this.id = id;
			this.text = text;
			this.cls = null;
			this.leaf = true;;
			this.checked = checked;
			this.expanded = false;
			this.children = null;
		}		
	}
}