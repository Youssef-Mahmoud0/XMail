package com.example.mail;

import java.util.ArrayList;

public interface MailFolders {
    void addMail(Mail mail);
    ArrayList<Mail> getMail();
    ArrayList<Mail> deleteMail(Mail mail);
}
