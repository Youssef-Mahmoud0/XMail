package com.example.mail;

import java.util.ArrayList;

public class CustomFolder implements MailFolders{
    private String folderName;
    private ArrayList<Mail> mails;

    public void setMails(ArrayList<Mail> mails) {
        this.mails = mails;
    }
    public CustomFolder(String folderName){
        this.folderName = folderName;
        this.mails = new ArrayList<>();
    }
    public CustomFolder(){
        this.mails = new ArrayList<>();
    }
    @Override
    public void addMail(Mail mail) {
        if(this.mails == null)this.mails = new ArrayList<>();
        this.mails.add(mail);
    }
    @Override
    public ArrayList<Mail> getMail() {
        return this.mails;
    }

    @Override
    public ArrayList<Mail> deleteMail(Mail mail) {
        return this.mails;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
