package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public class CriteriaTo implements Criteria{
    public CriteriaTo() {
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter) {
        ArrayList<Mail> filteredMails = new ArrayList<>();
        for (Mail mail:mails){
            for (String receiver : mail.getTo()){
                if (receiver.toLowerCase().contains(filter.toLowerCase())){
                    filteredMails.add(mail);
                    break;
                }
            }
        }
        return filteredMails;
    }

    @Override
    public Boolean matches(Mail mail, String filter) {
        for (String receiver : mail.getTo()){
            if (receiver.equalsIgnoreCase(filter)){
                return true;
            }
        }
        return false;
    }
}
