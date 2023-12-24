package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public class CriteriaFrom implements Criteria{
    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter) {
        ArrayList<Mail> filteredMails = new ArrayList<>();
        for (Mail mail:mails){
            if (mail.getFrom().toLowerCase().contains(filter.toLowerCase()))
                filteredMails.add(mail);
        }
        return filteredMails;
    }
}
