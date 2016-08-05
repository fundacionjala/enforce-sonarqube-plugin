public class SomeClass {
	
	public static void aMethod() {
            List<table1> incorrect = [SELECT dato FROM table1];
            List<table1> incorrect = Database.query('SELECT dato FROM table1');
        }

        public static void aMethod() {
            List<table1> correct = [SELECT dato FROM table1 LIMIT 50];
        }
}

    