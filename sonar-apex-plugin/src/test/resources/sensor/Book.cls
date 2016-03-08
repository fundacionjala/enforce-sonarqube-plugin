public with sharing class Book implements IBook {

    @isTest public void testType() {
        System.assert(true);
        System.assertEquals('name', 'name');
    }
}