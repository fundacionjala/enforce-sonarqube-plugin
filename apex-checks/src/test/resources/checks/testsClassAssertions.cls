@isTest
public class sampleTestMethod {

    private String someString;
    private boolean anotherBooleanValue;

    public static testMethod void testSampleMethod() {
        List<Account> accounts = new List<Account>{};
        for(Integer i = 0; i < 200; i++){
            Account a = new Account(Name = 'Test Account ' + i);
            accounts.add(a);
        }
        
        test.startTest();
         
        insert accounts;
         
        test.stopTest();

        for(Account a : insertedAccounts){
        }

    }

    public static testMethod void testSampleMethod1() {
        List<Account> accounts = new List<Account>{};
        for(Integer i = 0; i < 200; i++){
            Account a = new Account(Name = 'Test Account ' + i);
            accounts.add(a);
        }
        
        test.startTest();
         
        insert accounts;
         
        test.stopTest();

        for(Account a : insertedAccounts){
            System.assertEquals(
            'This Account is probably left over from testing. It should probably be deleted.',
            a.Description);
        }

    }

    public static void normalMethod() {
        List<Account> accounts = new List<Account>{};
        for(Integer i = 0; i < 200; i++){
            Account a = new Account(Name = 'Test Account ' + i);
            accounts.add(a);
        }
        
        for(Account a : insertedAccounts){
        }

    }

    public static void testNoKeywordWithAssertionLogic() {
        List<Account> accounts = new List<Account>{};
        for(Integer i = 0; i < 200; i++){
            Account a = new Account(Name = 'Test Account ' + i);
            accounts.add(a);
        }
        
        test.startTest();
         
        insert accounts;
         
        test.stopTest();

        for(Account a : insertedAccounts){
           System.assertEquals(
            'This Account is probably left over from testing. It should probably be deleted.',
            a.Description);
        }
    }

    public static testMethod void testMethodWithParameters(String someParameter) {
        integer variable = 0;
        test.startTest();
        variable = 1;
        test.stopTest();
        System.assertEquals(1 , variable);
    }

    public static testMethod void testMethodWithBooleanLocalVariableOnAssert() {
        integer variable = 0;
        test.startTest();
        variable = 1;
        test.stopTest();
        boolean isBoolean = true;
        string message = 'error';
        System.assert(isBoolean, message);
    }
    
    public static testMethod void testMethodWithBooleanFieldVariableOnAssert() {
        integer variable = 0;
        test.startTest();
        variable = 1;
        test.stopTest();
        string message = 'error';
        System.assert(anotherBooleanValue, message);
    }

    public static testMethod void testMethodWithNoBooleanVariableOnAssert() {
        integer variable = 0;
        test.startTest();
        variable = 1;
        test.stopTest();
        integer someInteger = 2;
        string message = 'error';
        System.assert(someInteger, message);
    }

    public static testMethod void testMethodWithoutExpressionValueOnAssert() {
        integer variable = 0;
        test.startTest();
        variable = 1;
        test.stopTest();
        System.assert(4-2/5, '');
    }
}
