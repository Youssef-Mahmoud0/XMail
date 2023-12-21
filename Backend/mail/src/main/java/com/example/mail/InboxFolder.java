package com.example.mail;

import java.io.Serializable;
import java.util.ArrayList;

public class InboxFolder implements MailFolders, Serializable {
    private ArrayList<Mail> inboxMails;

    public InboxFolder() {
        this.inboxMails = new ArrayList<>();
    }

    @Override
    public void addMail(Mail mail) {
        if(this.inboxMails == null)this.inboxMails = new ArrayList<Mail>();
        this.inboxMails.add(mail);
    }

    @Override
    public ArrayList<Mail> getMail() {
        return this.inboxMails;
    }

    public void setInboxMails(ArrayList<Mail> inboxMails) {
        this.inboxMails = inboxMails;
    }
    @Override
    public ArrayList<Mail> deleteMail(Mail mail) {
        return this.inboxMails;
    }
}
