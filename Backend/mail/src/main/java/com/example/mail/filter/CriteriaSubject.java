package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public class CriteriaSubject implements Criteria{
    public CriteriaSubject() {
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
        return mail.getSubject().toLowerCase().contains(filter.toLowerCase());
    }
}
