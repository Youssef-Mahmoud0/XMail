package com.example.mail;

import java.io.Serializable;
import java.util.ArrayList;

public class TrashFolder implements MailFolders, Serializable {
    private ArrayList<Mail> trashMails;

    public TrashFolder() {
        this.trashMails = new ArrayList<>();
    }

    @Override
    public void addMail(Mail mail) {
        if (this.trashMails == null)
            this.trashMails = new ArrayList<>();
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
