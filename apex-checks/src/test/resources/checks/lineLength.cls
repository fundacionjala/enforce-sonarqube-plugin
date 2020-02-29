public       with       sharing         class               DraftArticle    extends AbstractArticletest {


                    public           integer          MyMethod                       () { 
                        integer     someNumber = 1902;
                        Account accountObject = [select id, name, createdDate, CreatedBy, OwnerID, Owner.Name from Account];
                            return someNumber;
                    }
}


    
