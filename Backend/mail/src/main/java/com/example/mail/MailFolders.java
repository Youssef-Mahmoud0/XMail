package com.example.mail;

import java.util.ArrayList;

public interface MailFolders {
    void addMail(MailParent mail);
    ArrayList<MailParent> getMail();
    ArrayList<MailParent> deleteMail(MailParent mail);
}
