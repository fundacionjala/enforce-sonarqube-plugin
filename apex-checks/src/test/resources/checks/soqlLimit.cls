public class SomeClass {
	
	public static void aMethod() {
            List<table1> incorrect = [SELECT dato FROM table1];
            List<table1> incorrect = Database.query('SELECT dato FROM table1');
        }

    public static void bMethod() {
        list<AssignmentRule> lstAR = [select id 
            					from AssignmentRule 
            					where SobjectType = 'Case' 
            						and Active = 'true'
            						and Active = :test
            						limit 1];
        
        
    }
}

    