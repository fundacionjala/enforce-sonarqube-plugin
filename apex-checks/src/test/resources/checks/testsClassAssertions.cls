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

}