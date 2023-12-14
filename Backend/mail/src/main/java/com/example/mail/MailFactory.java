package com.example.mail;

import java.util.ArrayList;

public class MailFactory {
    public MailParent createMail(String type, MailParent mail){
        if(type.equals("recieved")){
            return new recievedMail();
        }
        if(type.equals("sent")){
            return new sentMail();
        }
        if(type.equals("draft")){
            return new draftMail();
        }
        else{
            return null;
        }
    }
}
