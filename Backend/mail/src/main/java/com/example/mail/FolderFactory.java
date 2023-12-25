package com.example.mail;

public class FolderFactory {
    public MailFolders createFolder(String type){
        if(type.equals("inbox")){
            InboxFolder inboxFolder = new InboxFolder();
            return inboxFolder;
        }
        else if(type.equals("sent")){
            SentFolder sentFolder = new SentFolder();
            return sentFolder;
        }
        else if(type.equals("draft")){
            DraftFolder draftFolder = new DraftFolder();
            return draftFolder;
        } else if(type.equals("trash")) {
            TrashFolder trashFolder = new TrashFolder();
            return trashFolder;
        }
        else{
            CustomFolder customFolder = new CustomFolder("defaultName");
            return customFolder;
        }
    }
}
