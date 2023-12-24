package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public class CriteriaYear implements Criteria{
    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter) {
        ArrayList<Mail> filteredMails = new ArrayList<>();
        if (this.isValidYear(filter)){
            for (Mail mail:mails){
                if (mail.getLocalDate().getYear() == Integer.parseInt(filter))
                    filteredMails.add(mail);
            }
        }
        return filteredMails;
    }
    public Boolean isValidYear(String filter){
        try {
            int year = Integer.parseInt(filter);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
            return false
        }
        return true;
    }
}
