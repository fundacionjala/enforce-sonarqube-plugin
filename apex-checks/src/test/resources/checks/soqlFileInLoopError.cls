public class Foo {
	public void test1() {
		for(Integer i : new List<Integer>{1,2}) {
			List<Account> accounts = [SELECT Id FROM Account];
		}
	}
	
	public void test2() {
		for(;;) {
			List<Account> accounts = [SELECT Id FROM Account];
		}
	}
	
	public void test3() {
		while(true) {
			List<Account> accounts = [SELECT Id FROM Account];
		}
	}
	
	public void test4() {
		do{
			List<Account> accounts = [SELECT Id FROM Account];
		}while(true) ;
	}
	
	public void test5() {
		do{
			List<Account> accounts = [SELECT Id FROM Account];
			List<Contact> accounts = [SELECT Id FROM Contact];
		}while(true) ;
	}
}