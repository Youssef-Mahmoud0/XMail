package com.example.mail;

import java.util.ArrayList;

public class SentFolder implements MailFolders{
    private ArrayList<Mail> sentMails;

    public SentFolder(){
        this.sentMails = new ArrayList<>();
    }
    @Override
    public void addMail(Mail mail) {
        if(this.sentMails == null)this.sentMails = new ArrayList<Mail>();
        this.sentMails.add(mail);
    }

    @Override
    public ArrayList<Mail> getMail() {
        return this.sentMails;
    }

    public void setSentMails(ArrayList<Mail> sentMails) {
        this.sentMails = sentMails;
    }

    @Override
    public ArrayList<Mail> deleteMail(Mail mail) {
        return this.sentMails;
    }
}
