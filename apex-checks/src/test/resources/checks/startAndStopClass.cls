@isTest
public class SomeTestClass {
	
	public static testMethod void anotherTestMethod() {
            test.startTest();
            test.startTest();
            boolean n = isSomething();
            System.assert(n);
	}

        public static testMethod void anootherTestMethod() {
            test.startTest();
            boolean n = isSomething();
            System.assert(n);
            test.stopTest();
            test.startTest();
            test.startTest();
            test.stopTest();
	}

        public static testMethod void anoootherTestMethod() {
            test.startTest();
            boolean n = isSomething();
            System.assert(n);
            test.stopTest();
            integer x = 0;
            doSomething(x);
            test.stopTest();
	}
        
        //correct use, there should be no error here
        public static testMethod String aTestMethod() {
            test.startTest();
            System.assert(x.equals(y));
            test.stopTest();
	}

        //also valid, using none
        public static testMethod String aTestMethod() {
            System.assert(x.equals(y));
	}
}

    