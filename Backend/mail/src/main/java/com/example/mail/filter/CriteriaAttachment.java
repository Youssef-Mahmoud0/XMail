package com.example.mail.filter;

import com.example.mail.Attachment;
import com.example.mail.Mail;

import java.util.ArrayList;

public class CriteriaAttachment implements Criteria{
    public CriteriaAttachment() {
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter) {
        ArrayList<Mail> filteredMails = new ArrayList<>();
        for (Mail mail:mails){
            for (Attachment attachment : mail.getAttachments()){
                if (attachment.getName().toLowerCase().contains(filter.toLowerCase()))
                    filteredMails.add(mail);
            }
        }
        return filteredMails;
    }

    @Override
    public Boolean matches(Mail mail, String filter) {
        for (Attachment attachment : mail.getAttachments()){
            if (attachment.getName().toLowerCase().contains(filter.toLowerCase()))
                return true;
        }
        return false;
    }
}
