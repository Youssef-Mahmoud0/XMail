package com.example.mail;

import java.util.ArrayList;

public class MailFactory {
    public MailParent createMail(String type, MailParent mail){
        if(type.equals("inbox")){
            inboxMail Mail = new inboxMail();
            Mail.AddMail(mail);
            return Mail;
        }
        if(type.equals("sent")){
            sentMail Mail = new sentMail();
            Mail.AddMail(mail);
            return Mail;
        }
        if(type.equals("draft")){
            draftMail Mail = new draftMail();
            Mail.AddMail(mail);
            return mail;
        }
        else{
            return null;
        }
    }
}
