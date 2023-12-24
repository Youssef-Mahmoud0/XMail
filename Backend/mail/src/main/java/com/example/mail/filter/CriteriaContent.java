package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public class CriteriaContent implements Criteria{
    public CriteriaContent() {
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter) {
        ArrayList<Mail> filteredMails = new ArrayList<>();
        for (Mail mail:mails){
            if (this.matches(mail,filter))
                filteredMails.add(mail);
        }
        return filteredMails;
    }

    @Override
    public Boolean matches(Mail mail, String filter) {
        return mail.getContent().toLowerCase().contains(filter.toLowerCase());
    }
}
