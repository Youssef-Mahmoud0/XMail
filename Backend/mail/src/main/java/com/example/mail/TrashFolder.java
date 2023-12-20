package com.example.mail;

import java.util.ArrayList;

public class TrashFolder implements MailFolders{
    private ArrayList<Mail> trashMails;
    @Override
    public void addMail(Mail mail) {
        if (this.trashMails == null)
            this.trashMails = new ArrayList<>();
        else
            this.trashMails.add(mail);
    }

    @Override
    public ArrayList<Mail> getMail() {
        return this.trashMails;
    }

    @Override
    public ArrayList<Mail> deleteMail(Mail mail) {
        return null;
    }
}
