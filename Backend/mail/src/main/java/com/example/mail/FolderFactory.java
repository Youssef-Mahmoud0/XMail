package com.example.mail;

public class FolderFactory {
    public MailFolders createFolder(String type){
        if(type.equals("inbox")){
            InboxFolder inboxFolder = new InboxFolder();
            return inboxFolder;
        }
        if(type.equals("sent")){
            SentFolder sentFolder = new SentFolder();
            return sentFolder;
        }
        if(type.equals("draft")){
            DraftFolder draftFolder = new DraftFolder();
            return draftFolder;
        }
        else{
            return null;
        }
    }
}
