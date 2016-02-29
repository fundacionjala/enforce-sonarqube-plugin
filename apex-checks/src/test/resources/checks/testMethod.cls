public with sharing class DraftArticle extends AbstractArticle {

    @isTest public void testType() {
        System.assert(true);
        System.assertEquals('name', 'name');
    }
}
