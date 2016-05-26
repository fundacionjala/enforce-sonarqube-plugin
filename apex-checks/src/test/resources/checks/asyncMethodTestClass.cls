public with sharing class AsyncMethodClass {

    public void myMethod () { 
        while(y < x)  {
            if(y < 0) {
                execNonAsyncMethod();
            }
        }

        while(trueExpression){
            execAsyncMethod();
            a.doSomethingElse();
        }
        
        for(Accts newAcct : listAccts){
            newAcct.doSomething();
            execAsyncMethod(); 
        }
        
        AnotherClass instance = new AnotherClass();
        for(Accts newAcct : listAccts){
            instance.execAnotherAsyncMethod(0);
            instance = new AnotherClass(null);
            anObject.Instance = instance;
            anObject.Instance.execAnotherAsyncMethod(1).size();
        }
    }

    @future
    public void execAsyncMethod(){
    }

    public void execNonAsyncMethod(){
    }

    public class AnotherClass {
    
        @future
        public void execAnotherAsyncMethod(integer x) {
        }
    }
}