package com.example.mail;

import java.util.ArrayList;

public class DraftFolder implements MailFolders{
    private ArrayList<Mail> draftMails;


    @Override
    public void addMail(Mail mail) {

    }

    @Override
    public ArrayList<Mail> getMail() {
        return null;
    }

    @Override
    public ArrayList<Mail> deleteMail(Mail mail) {
        return null;
    }
}
