public class Foo {
	public List<Account> test1() {
		for(;;) {
			return [SELECT Id FROM Account];
		}
	}
	
	public void test2() {
		for(Account a : [SELECT Id FROM Account]) {
			
		}
	}
}