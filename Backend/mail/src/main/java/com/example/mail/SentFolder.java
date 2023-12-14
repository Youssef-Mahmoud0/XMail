package com.example.mail;

import java.util.ArrayList;

public class SentFolder implements MailFolders{
    private ArrayList<MailParent> sentMails;

    @Override
    public void addMail(MailParent mail) {

    }

    @Override
    public ArrayList<MailParent> getMail() {
        return null;
    }

    @Override
    public ArrayList<MailParent> deleteMail(MailParent mail) {
        return null;
    }
}
