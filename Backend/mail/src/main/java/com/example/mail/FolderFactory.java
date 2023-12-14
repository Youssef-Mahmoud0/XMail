package com.example.mail;

public class FolderFactory {
    public MailFolders createFolder(String type, MailParent mail){
        if(type.equals("inbox")){
            InboxFolder inboxFolder = new InboxFolder();
            inboxFolder.addMail(mail);
            return inboxFolder;
        }
        if(type.equals("sent")){
            SentFolder sentFolder = new SentFolder();
            sentFolder.addMail(mail);
            return sentFolder;
        }
        if(type.equals("draft")){
            DraftFolder draftFolder = new DraftFolder();
            draftFolder.addMail(mail);
            return draftFolder;
        }
        else{
            return null;
        }
    }
}
