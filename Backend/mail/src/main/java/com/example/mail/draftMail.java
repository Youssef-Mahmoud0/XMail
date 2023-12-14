package com.example.mail;

import java.util.ArrayList;

public class draftMail extends MailParent{
    private ArrayList<MailParent> draftMails;

    public void AddMail(MailParent mail){
        this.draftMails.add(mail);
    }

    public ArrayList<MailParent> getDraftMails() {
        return draftMails;
    }

    public void setDraftMails(ArrayList<MailParent> draftMails) {
        this.draftMails = draftMails;
    }
}
