package com.example.mail;

import java.util.ArrayList;

public class CustomFolder implements MailFolders{
    private String folderName;
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

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
