package com.example.mail;

import java.util.ArrayList;

public class sentMail extends MailParent{
    private ArrayList<MailParent> sentMails;

    public void AddMail(MailParent mail){
        this.sentMails.add(mail);
    }

    public ArrayList<MailParent> getSentMails() {
        return sentMails;
    }

}
