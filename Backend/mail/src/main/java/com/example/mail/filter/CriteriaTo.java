package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public class CriteriaTo implements Criteria{
    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter) {
        ArrayList<Mail> filteredMails = new ArrayList<>();
        for (Mail mail:mails){
            for (String receiver : mail.getTo()){
                if (receiver.equalsIgnoreCase(filter)){
                    filteredMails.add(mail);
                    break;
                }
            }
        }
        return filteredMails;
    }
}
