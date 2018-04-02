public class DraftArticle {
    public integer MyMethod () { 
    		Integer i = 0;
    		for (Integer count = 0; count < 10; count++){
    			i++;
    		}
    		return i;
    }
    
    public integer MyOtherMethod () { 
    		Integer i = 0;
    		while (true){
	    		break;
    		}
    		return i;
    }
    
    public integer DoMyOtherMethod () { 
    		Integer i = 0;
    		{
	    		i++;
    		} do (true);
    		return i;
    }
    
    public integer NestMyOtherMethod () { 
    		Integer i = 0;
    		while (true){
	    		for (Integer count = 0; count < 10; count++){
    				i++;
    			};
    		}
    		return i;
    }
}


    