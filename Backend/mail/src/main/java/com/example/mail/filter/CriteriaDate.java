package com.example.mail.filter;

import com.example.mail.Mail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CriteriaDate implements Criteria{
    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ArrayList<Mail> filteredMails = new ArrayList<>();
        LocalDate localDate = LocalDate.parse(filter,formatter);
        for (Mail mail:mails){
            if (mail.getLocalDate().equals(localDate))
                filteredMails.add(mail);
        }
        return filteredMails;
    }
}
