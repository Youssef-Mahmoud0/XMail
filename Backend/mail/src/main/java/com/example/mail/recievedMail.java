package com.example.mail;

import java.util.ArrayList;

public class recievedMail extends MailParent{
    private ArrayList<MailParent> recievedMails;

    public void AddMail(MailParent mail){
        this.recievedMails.add(mail);
    }
    public ArrayList<MailParent> getRecievedMails() {
        return recievedMails;
    }

}
