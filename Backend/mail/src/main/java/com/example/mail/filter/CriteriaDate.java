package com.example.mail.filter;

import com.example.mail.Mail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CriteriaDate implements Criteria{
    public CriteriaDate() {
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        ArrayList<Mail> filteredMails = new ArrayList<>();
        for (Mail mail:mails){
            LocalDate localDate = mail.getLocalDate();
            String result = localDate.format(formatter);
            if (result.equals(filter))
                filteredMails.add(mail);
        }
        return filteredMails;
    }

    @Override
    public Boolean matches(Mail mail, String filter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = mail.getLocalDate();
        String result = localDate.format(formatter);
        return result.equals(filter);
    }
}
