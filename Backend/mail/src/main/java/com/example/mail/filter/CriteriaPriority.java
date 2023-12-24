package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public class CriteriaPriority implements Criteria{
    public CriteriaPriority() {
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter) {
        ArrayList<Mail> filteredMails = new ArrayList<>();
        if (this.isValidPriority(filter)){
            for (Mail mail:mails){
                if (mail.getPriority() == Integer.parseInt(filter))
                    filteredMails.add(mail);
            }
        }
        return filteredMails;
    }

    @Override
    public Boolean matches(Mail mail, String filter) {
        return isValidPriority(filter) && mail.getPriority() == Integer.parseInt(filter);
    }
    public Boolean isValidPriority(String filter){
        try {
            int year = Integer.parseInt(filter);
        } catch (NumberFormatException e) {
//            throw new RuntimeException(e);
            return false;
        }
        return true;
    }
}
