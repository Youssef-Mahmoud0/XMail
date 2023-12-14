package com.example.mail;

import java.util.ArrayList;

public class inboxMail extends MailParent{
    private ArrayList<MailParent> inboxMails;

    public void AddMail(MailParent mail){
        this.inboxMails.add(mail);
    }
    public ArrayList<MailParent> getRecievedMails() {
        return inboxMails;
    }

}
