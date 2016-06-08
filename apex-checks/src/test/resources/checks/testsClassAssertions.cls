@isTest
public class sampleTestMethod {
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

    public static testMethod void testMethodWithNoBooleanValueOnAssert() {
        integer variable = 0;
        test.startTest();
        variable = 1;
        test.stopTest();
        integer variable = new Integer();
        string message = 'error';
        System.assert(variable, message);
    }

    public static testMethod void testMethodWithExpressionValueOnAssert() {
        integer variable = 0;
        test.startTest();
        variable = 1;
        test.stopTest();
        System.assert(4-2/5);
    }
 
   public static testMethod void testMethodWithBooleanValueOnAssert() {
        integer variable = 0;
        test.startTest();
        variable = 1;
        test.stopTest();
        System.assert(variable == 1);
    }
}
