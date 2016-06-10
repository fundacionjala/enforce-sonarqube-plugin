@isTest
public class LiteralBooleanAssertTestClass {
	
	public static testMethod void someTestMethod() {
		doSomething();

		System.assert(TRUE);
	}

	public static testMethod void otherTestMethod() {
		doSomethingElse();

		System.assert(FALSE);
	}
}