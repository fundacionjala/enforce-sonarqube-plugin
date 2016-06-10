@isTest
public class SomeTestClass {
	
	public static testMethod String aTestMethod() {
		System.assert(x.equals(y), 'Test failed because x was supposed to be equal to y');
	}

	public static testMethod void anotherTestMethod() {
		boolean n = isSomething();
		doSomethingElse();
		System.assert(n);
		System.assertEquals(n, m);
		System.assertNotEquals(n, m);
	}
}

    